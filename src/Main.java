import java.io.*;
import java.util.*;

public class Main {
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        char[][] grid = new char[n][n];
        
        for (int i = 0; i < n; i++) {
            String[] elements = br.readLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                grid[i][j] = elements[j].charAt(0);
            }
        }
        
        System.out.print(findMinimumWaterPath(grid));
    }

    public static int findMinimumWaterPath(char[][] grid) {
        int n = grid.length;
        int[] start = findPosition(grid, 'S');
        int[] end = findPosition(grid, 'E');
        
        int[][] waterConsumption = new int[n][n];
        for (int[] row : waterConsumption) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        waterConsumption[start[0]][start[1]] = 0;
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start[0], start[1], 0));
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.water > waterConsumption[current.x][current.y]) continue;
            if (current.x == end[0] && current.y == end[1]) {
                return current.water;
            }

            for (int[] dir : DIRECTIONS) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                
                if (isValidMove(grid, newX, newY)) {
                    int newWater = current.water;
                    if (grid[current.x][current.y] != 'T' || grid[newX][newY] != 'T') {
                        newWater++;
                    }
                    
                    if (newWater < waterConsumption[newX][newY]) {
                        waterConsumption[newX][newY] = newWater;
                        pq.offer(new Node(newX, newY, newWater));
                    }
                }
            }
        }
        
        return -1;
    }

    static class Node implements Comparable<Node> {
        int x, y, water;

        Node(int x, int y, int water) {
            this.x = x;
            this.y = y;
            this.water = water;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.water, other.water);
        }
    }

    private static boolean isValidMove(char[][] grid, int x, int y) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid.length && grid[x][y] != 'M';
    }

    private static int[] findPosition(char[][] grid, char target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Position not found: " + target);
    }
}
