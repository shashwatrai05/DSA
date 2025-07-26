package andino;
import java.util.*;

public class MarsRoverSystem {

    enum Direction {
        N(0, 1), E(1, 0), S(0, -1), W(-1, 0);
        
        final int dx, dy;
        
        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        
        Direction rotateLeft() {
            return values()[(ordinal() + 3) % 4];
        }
        
        Direction rotateRight() {
            return values()[(ordinal() + 1) % 4];
        }
    }
    
    static class RoverState {
        int x, y;
        Direction direction;
        int power;
        int scientificData;
        
        RoverState(int x, int y, Direction direction, int power) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.power = power;
            this.scientificData = 0;
        }
        
        RoverState copy() {
            RoverState copy = new RoverState(x, y, direction, power);
            copy.scientificData = scientificData;
            return copy;
        }
    }
    
    static class MissionResult {
        RoverState finalState;
        String status;
        
        MissionResult(RoverState finalState, String status) {
            this.finalState = finalState;
            this.status = status;
        }
        
        @Override
        public String toString() {
            return String.format(
                "{\n  \"final_state\": {\n    \"position\": [%d, %d],\n    \"direction\": \"%s\",\n    \"power\": %d,\n    \"scientific_data\": %d\n  },\n  \"status\": \"%s\"\n}",
                finalState.x, finalState.y, finalState.direction.name(),
                finalState.power, finalState.scientificData, status
            );
        }
    }
    
    public static MissionResult executeMission(
            String[][] plateauMap,
            int[] initialPosition,
            String initialDirection,
            int maxPower,
            int chargingRate,
            String commandSequence) {

        if (plateauMap == null || plateauMap.length == 0 || plateauMap[0].length == 0) {
            throw new IllegalArgumentException("Invalid plateau map");
        }
        
        if (initialPosition == null || initialPosition.length != 2) {
            throw new IllegalArgumentException("Invalid initial position");
        }
        
        if (commandSequence == null) {
            commandSequence = "";
        }
        
        int height = plateauMap.length;
        int width = plateauMap[0].length;

        int startX = initialPosition[0];
        int startY = initialPosition[1];
        if (startX < 0 || startX >= width || startY < 0 || startY >= height) {
            throw new IllegalArgumentException("Initial position out of bounds");
        }

        if ("X".equals(plateauMap[height - 1 - startY][startX])) {
            throw new IllegalArgumentException("Initial position is impassable");
        }
        
        Direction dir;
        try {
            dir = Direction.valueOf(initialDirection);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid initial direction: " + initialDirection);
        }
        
        RoverState rover = new RoverState(startX, startY, dir, maxPower);

        Set<String> collectedAnomalies = new HashSet<>();

        for (int i = 0; i < commandSequence.length(); i++) {
            char command = commandSequence.charAt(i);

            if (rover.power <= 0) {
                return new MissionResult(rover, "Mission Failed: Power Depleted");
            }
            
            switch (command) {
                case 'L':
                    if (rover.power < 1) {
                        return new MissionResult(rover, "Mission Failed: Insufficient Power for Rotation");
                    }
                    rover.direction = rover.direction.rotateLeft();
                    rover.power--;
                    break;
                    
                case 'R':
                    if (rover.power < 1) {
                        return new MissionResult(rover, "Mission Failed: Insufficient Power for Rotation");
                    }
                    rover.direction = rover.direction.rotateRight();
                    rover.power--;
                    break;
                    
                case 'M':
                    MissionResult moveResult = executeMove(rover, plateauMap, width, height);
                    if (moveResult != null) return moveResult;
                    break;
                    
                case 'P':
                    MissionResult processResult = processAnomaly(rover, plateauMap, height, collectedAnomalies);
                    if (processResult != null) return processResult;
                    break;
                    
                default:
                    break;
            }

            String currentCell = plateauMap[height - 1 - rover.y][rover.x];
            if ("C".equals(currentCell)) {
                rover.power += chargingRate;
            }
        }
        
        return new MissionResult(rover, "Mission Successful");
    }
    
    private static MissionResult executeMove(RoverState rover, String[][] plateauMap, 
                                           int width, int height) {
        int newX = rover.x + rover.direction.dx;
        int newY = rover.y + rover.direction.dy;
        
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return new MissionResult(rover, "Mission Failed: Rover Fell Off Mars");
        }

        String destinationCell = plateauMap[height - 1 - newY][newX];
        if ("X".equals(destinationCell)) {
            return null;
        }

        int powerCost = getMovementCost(destinationCell);
        
        if (rover.power < powerCost) {
            if ("S".equals(destinationCell)) {
                return new MissionResult(rover, "Mission Failed: Rover Stuck in Sand Dune");
            }
            return new MissionResult(rover, "Mission Failed: Insufficient Power for Movement");
        }
        rover.x = newX;
        rover.y = newY;
        rover.power -= powerCost;
        
        return null;
    }
    
    private static int getMovementCost(String cellType) {
        switch (cellType) {
            case "E": case "A": return 1; 
            case "R": return 2; 
            case "S": return 3; 
            case "C": return 1; 
            case "X": return Integer.MAX_VALUE; 
            default: return 1;
        }
    }
    
    private static MissionResult processAnomaly(RoverState rover, String[][] plateauMap, 
                                              int height, Set<String> collectedAnomalies) {
        String currentCell = plateauMap[height - 1 - rover.y][rover.x];
        
        if (rover.power < 1) {
            return new MissionResult(rover, "Mission Failed: Power Depleted During Anomaly Processing");
        }
        rover.power--;

        if ("A".equals(currentCell)) {
            // Check if this anomaly was already collected
            String anomalyKey = rover.x + "," + rover.y;
            if (!collectedAnomalies.contains(anomalyKey)) {
                rover.scientificData++;
                collectedAnomalies.add(anomalyKey);
            }
        }
        
        
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("=== Mars Rover Mission Control ===");
            System.out.println();
            
            System.out.print("Enter plateau width: ");
            int width = scanner.nextInt();
            System.out.print("Enter plateau height: ");
            int height = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (width <= 0 || height <= 0) {
                System.out.println("Error: Plateau dimensions must be positive integers.");
                return;
            }
            
            // Get plateau map
            System.out.println();
            System.out.println("Enter plateau map (row by row, from top to bottom):");
            System.out.println("Use: E (Empty), R (Rocky), X (Impassable), C (Charging), A (Anomaly), S (Sand dune)");
            System.out.println("Separate cells with spaces:");
            
            String[][] plateauMap = new String[height][width];
            for (int i = 0; i < height; i++) {
                System.out.printf("Row %d (top=%d): ", i + 1, height - i);
                String[] row = scanner.nextLine().trim().split("\\s+");
                
                if (row.length != width) {
                    System.out.printf("Error: Expected %d cells, got %d. Please try again.\n", width, row.length);
                    i--;
                    continue;
                }
                
                boolean validRow = true;
                for (String cell : row) {
                    if (!isValidCellType(cell)) {
                        System.out.printf("Error: Invalid cell type '%s'. Use E, R, X, C, A, or S.\n", cell);
                        validRow = false;
                        break;
                    }
                }
                
                if (!validRow) {
                    i--;
                    continue;
                }
                
                plateauMap[i] = row;
            }
            
            System.out.println();
            System.out.print("Enter initial X coordinate (0 to " + (width - 1) + "): ");
            int startX = scanner.nextInt();
            System.out.print("Enter initial Y coordinate (0 to " + (height - 1) + "): ");
            int startY = scanner.nextInt();
            scanner.nextLine(); 
            
            System.out.print("Enter initial direction (N/S/E/W): ");
            String direction = scanner.nextLine().trim().toUpperCase();
            
            // Get power settings
            System.out.print("Enter maximum power: ");
            int maxPower = scanner.nextInt();
            System.out.print("Enter charging rate: ");
            int chargingRate = scanner.nextInt();
            scanner.nextLine();
            
            if (maxPower <= 0) {
                System.out.println("Error: Maximum power must be positive.");
                return;
            }

            System.out.println();
            System.out.print("Enter command sequence (L/R/M/P): ");
            String commands = scanner.nextLine().trim().toUpperCase();

            System.out.println();
            System.out.println("=== Mission Execution ===");
            displayMissionInput(plateauMap, startX, startY, direction, maxPower, chargingRate, commands);
            
            MissionResult result = executeMission(
                plateauMap, 
                new int[]{startX, startY}, 
                direction, 
                maxPower, 
                chargingRate, 
                commands
            );
            
            System.out.println();
            System.out.println("=== Mission Result ===");
            System.out.println(result);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Invalid input format. " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
    
    private static boolean isValidCellType(String cellType) {
        return cellType.matches("[ERXCAS]");
    }
    
    private static void displayMissionInput(String[][] plateauMap, int startX, int startY, 
                                          String direction, int maxPower, int chargingRate, String commands) {
        System.out.println("Mission Parameters:");
        System.out.printf("- Plateau: %dx%d\n", plateauMap[0].length, plateauMap.length);
        System.out.printf("- Initial Position: (%d, %d)\n", startX, startY);
        System.out.printf("- Initial Direction: %s\n", direction);
        System.out.printf("- Max Power: %d\n", maxPower);
        System.out.printf("- Charging Rate: %d\n", chargingRate);
        System.out.printf("- Commands: %s\n", commands);
        
        System.out.println("\nPlateau Map (Y increases upward):");
        for (int i = 0; i < plateauMap.length; i++) {
            System.out.printf("Y=%d: ", plateauMap.length - 1 - i);
            for (int j = 0; j < plateauMap[i].length; j++) {
                System.out.print(plateauMap[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("     ");
        for (int j = 0; j < plateauMap[0].length; j++) {
            System.out.printf("X=%d ", j);
        }
        System.out.println();
    }
}