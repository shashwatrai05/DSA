import java.util.*;

public class EarringMatcher {

    // Method to get all rotations of the earring
    public static List<List<int[]>> getAllRotations(List<int[]> vertices) {
        List<List<int[]>> rotations = new ArrayList<>();
        int n = vertices.size();

        // Create all rotations by shifting the starting point
        for (int i = 0; i < n; i++) {
            List<int[]> rotated = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                rotated.add(vertices.get((i + j) % n));
            }
            rotations.add(rotated);
        }

        return rotations;
    }

    // Method to normalize the earring by getting the lexicographically smallest rotation
    public static List<int[]> normalizeEarring(List<int[]> vertices) {
        List<List<int[]>> rotations = getAllRotations(vertices);

        // Sort rotations lexicographically
        rotations.sort((a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                int[] pointA = a.get(i);
                int[] pointB = b.get(i);
                if (pointA[0] != pointB[0]) {
                    return pointA[0] - pointB[0];
                } else {
                    return pointA[1] - pointB[1];
                }
            }
            return 0;
        });

        // Return the smallest rotation
        return rotations.get(0);
    }

    // Method to check if two earrings are matching
    public static boolean areMatching(List<int[]> earring1, List<int[]> earring2) {
        List<int[]> norm1 = normalizeEarring(earring1);
        List<int[]> norm2 = normalizeEarring(earring2);

        // Compare the normalized forms
        for (int i = 0; i < norm1.size(); i++) {
            if (norm1.get(i)[0] != norm2.get(i)[0] || norm1.get(i)[1] != norm2.get(i)[1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read number of earrings
        int N = sc.nextInt();

        // Store earrings
        List<List<int[]>> earrings = new ArrayList<>();

        // Read each earring shape
        for (int i = 0; i < N; i++) {
            int K = sc.nextInt();  // number of sides
            List<int[]> vertices = new ArrayList<>();

            // Read each vertex of the earring
            for (int j = 0; j < K; j++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                vertices.add(new int[]{x, y});
            }

            earrings.add(vertices);
        }

        // Find matching earrings
        List<Integer> matchingPairs = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (areMatching(earrings.get(i), earrings.get(j))) {
                    matchingPairs.add(i + 1);  // 1-based index
                    matchingPairs.add(j + 1);  // 1-based index
                }
            }
        }

        // If no matching pairs, print nothing (just an empty line)
        if (matchingPairs.isEmpty()) {
            System.out.println();
        } else {
            // Sort the matching pairs and print the result
            matchingPairs.sort(Integer::compare);
            for (int idx : matchingPairs) {
                System.out.print(idx + " ");
            }
            System.out.println(); // Ensure output ends with a new line
        }

        sc.close();
    }
}
