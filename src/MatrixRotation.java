import java.util.*;

public class MatrixRotation {
    public static void main(String[] args) {
        // Use try-with-resources to automatically close the scanner
        try (Scanner sc = new Scanner(System.in)) {
            // Input validation for matrix size
            int n = sc.nextInt();
            if (n <= 0 || n > 300) {
                System.out.println("Invalid matrix size. Must be between 1 and 300.");
                return;
            }
            sc.nextLine(); // Consume newline

            // Read matrix with input validation
            char[][] matrix = new char[n][n];
            for (int i = 0; i < n; i++) {
                String line = sc.nextLine().replace(" ", "");
                if (line.length() != n) {
                    System.out.println("Invalid row length. Each row must have exactly " + n + " characters.");
                    return;
                }
                matrix[i] = line.toUpperCase().toCharArray(); // Normalize to uppercase
            }

            // Input validation for number of queries
            int q = sc.nextInt();
            if (q < 0 || q > 1000) {
                System.out.println("Invalid number of queries. Must be between 0 and 1000.");
                return;
            }

            // Process each query
            for (int i = 0; i < q; i++) {
                int row = sc.nextInt();
                int col = sc.nextInt();
                int size = sc.nextInt();

                // Validate query parameters
                if (row < 0 || col < 0 || size < 1 ||
                        row + size > n || col + size > n) {
                    System.out.println("Invalid query parameters.");
                    return;
                }

                processQuery(matrix, row, col, size);
            }

            // Print final output as a single string
            printMatrix(matrix);
        } catch (Exception e) {
            System.out.println("Error processing input: " + e.getMessage());
        }
    }

    /**
     * Process a single query by rotating a submatrix
     * @param matrix The entire matrix
     * @param row Starting row of the submatrix
     * @param col Starting column of the submatrix
     * @param size Size of the submatrix to rotate
     */
    private static void processQuery(char[][] matrix, int row, int col, int size) {
        int top = row;
        int left = col;
        int bottom = row + size - 1;
        int right = col + size - 1;

        int numLayers = (size + 1) / 2;

        // Process each layer of the submatrix
        for (int layerNum = 1; layerNum <= numLayers; layerNum++) {
            List<Character> layer = extractLayer(matrix, top, left, bottom, right);

            // Rotate the extracted layer (alternate between clockwise and counterclockwise)
            List<Character> rotatedLayer = rotateLayer(layer, layerNum, layerNum % 2 == 1);

            // Place the rotated layer back into the matrix
            placeLayer(matrix, top, left, bottom, right, rotatedLayer);

            // Move to inner layer
            top++;
            left++;
            bottom--;
            right--;
        }
    }

    /**
     * Extract the characters along the border of a submatrix
     * @return List of characters in the order: top row, right column, bottom row, left column
     */
    private static List<Character> extractLayer(char[][] matrix, int top, int left, int bottom, int right) {
        List<Character> layer = new ArrayList<>();

        // Top row (left to right)
        for (int j = left; j <= right; j++) {
            layer.add(matrix[top][j]);
        }
        // Right column (top to bottom, excluding top-right corner)
        for (int i = top + 1; i <= bottom; i++) {
            layer.add(matrix[i][right]);
        }
        // Bottom row (right to left)
        for (int j = right - 1; j >= left; j--) {
            layer.add(matrix[bottom][j]);
        }
        // Left column (bottom to top, excluding bottom-left and top-left corners)
        for (int i = bottom - 1; i > top; i--) {
            layer.add(matrix[i][left]);
        }

        return layer;
    }

    /**
     * Rotate the layer with character shifting
     * @param layer List of characters to rotate
     * @param k Layer number (used for determining rotation)
     * @param isOdd Whether this is an odd-numbered layer
     * @return Rotated and shifted layer
     */
    private static List<Character> rotateLayer(List<Character> layer, int k, boolean isOdd) {
        int n = layer.size();
        k %= n; // Handle large rotations efficiently

        List<Character> rotated = new ArrayList<>(n);

        if (isOdd) {
            // Counterclockwise rotation
            rotated.addAll(layer.subList(k, n));
            rotated.addAll(layer.subList(0, k));
            // Shift characters backward
            for (int i = 0; i < rotated.size(); i++) {
                rotated.set(i, shiftChar(rotated.get(i), -1));
            }
        } else {
            // Clockwise rotation
            rotated.addAll(layer.subList(n - k, n));
            rotated.addAll(layer.subList(0, n - k));
            // Shift characters forward
            for (int i = 0; i < rotated.size(); i++) {
                rotated.set(i, shiftChar(rotated.get(i), 1));
            }
        }

        return rotated;
    }

    /**
     * Place the rotated layer back into the matrix
     */
    private static void placeLayer(char[][] matrix, int top, int left, int bottom, int right, List<Character> layer) {
        int idx = 0;

        // Top row (left to right)
        for (int j = left; j <= right; j++) {
            matrix[top][j] = layer.get(idx++);
        }
        // Right column (top to bottom)
        for (int i = top + 1; i <= bottom; i++) {
            matrix[i][right] = layer.get(idx++);
        }
        // Bottom row (right to left)
        for (int j = right - 1; j >= left; j--) {
            matrix[bottom][j] = layer.get(idx++);
        }
        // Left column (bottom to top)
        for (int i = bottom - 1; i > top; i--) {
            matrix[i][left] = layer.get(idx++);
        }
    }

    /**
     * Shift a character cyclically in the alphabet
     * @param ch Input character
     * @param offset Shift direction and amount
     * @return Shifted character
     */
    private static char shiftChar(char ch, int offset) {
        return (char) (((ch - 'A' + offset + 26) % 26) + 'A');
    }

    /**
     * Print the matrix as a single string
     */
    private static void printMatrix(char[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (char[] row : matrix) {
            result.append(new String(row));
        }
        System.out.println(result);
    }
}   