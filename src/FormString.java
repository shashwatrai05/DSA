import java.util.*;

public class FormString {
    public static void main(String[] args) {
        // Use try-with-resources to ensure Scanner is properly closed
        try (Scanner sc = new Scanner(System.in)) {
            // Input validation for number of substrings
            int N = sc.nextInt();
            if (N < 1 || N > 100) {
                System.out.println("Invalid number of substrings");
                return;
            }
            sc.nextLine(); // Consume newline

            // Store substrings with their costs
            List<Pair> substrings = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                // Validate substring input
                String substring = sc.next();
                if (substring.isEmpty() || substring.length() > 100) {
                    System.out.println("Invalid substring");
                    return;
                }

                // Validate cost input
                int cost = sc.nextInt();
                if (cost < 1 || cost > 10000) {
                    System.out.println("Invalid cost");
                    return;
                }

                substrings.add(new Pair(substring, cost));
                sc.nextLine(); // Consume newline
            }

            // Validate main string input
            String mainString = sc.nextLine().trim();
            if (mainString.isEmpty() || mainString.length() > 1000) {
                System.out.println("Invalid main string");
                return;
            }

            // Find and print the result
            int result = findMinimumCost(substrings, mainString);
            System.out.println(result == Integer.MAX_VALUE ? "Impossible" : result);
        } catch (Exception e) {
            System.out.println("Input error: " + e.getMessage());
        }
    }

    /**
     * Find the minimum cost to form the main string using given substrings
     *
     * @param substrings List of available substrings with their costs
     * @param mainString Target string to be formed
     * @return Minimum cost to form the string, or Integer.MAX_VALUE if impossible
     */
    private static int findMinimumCost(List<Pair> substrings, String mainString) {
        int m = mainString.length();
        // DP array to store minimum cost to form mainString[0:i]
        int[] dp = new int[m + 1];

        // Initialize with a large value, except 0th index
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        // Dynamic programming to find minimum cost
        for (int i = 0; i < m; i++) {
            // Skip if current position is unreachable
            if (dp[i] == Integer.MAX_VALUE) continue;

            // Try to match each substring at current position
            for (Pair pair : substrings) {
                String substring = pair.substring;
                int len = substring.length();

                // Check if substring matches at current position
                if (i + len <= m && mainString.startsWith(substring, i)) {
                    // Update minimum cost for next position
                    dp[i + len] = Math.min(dp[i + len],
                            // Prevent integer overflow
                            dp[i] == Integer.MAX_VALUE ? Integer.MAX_VALUE : dp[i] + pair.cost);
                }
            }
        }

        // Return the cost to form the entire string
        return dp[m];
    }

    /**
     * Helper class to store substring and its cost
     */
    static class Pair {
        String substring;
        int cost;

        Pair(String substring, int cost) {
            this.substring = substring;
            this.cost = cost;
        }
    }
}