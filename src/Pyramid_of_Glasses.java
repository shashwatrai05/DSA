import java.util.Scanner;

public class Pyramid_of_Glasses {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // Number of pyramid levels
        int t = sc.nextInt(); // Number of seconds of pouring
        sc.close();

        double[][] dp = new double[n + 1][n + 1]; // DP table for champagne levels
        dp[0][0] = t; // Pour all champagne into the top glass

        int fullGlasses = 0;

        for (int i = 0; i < n; i++) { // Iterate through pyramid levels
            for (int j = 0; j <= i; j++) { // Each level has `i+1` glasses
                if (dp[i][j] >= 1) { // If the glass is full
                    fullGlasses++;
                    double excess = dp[i][j] - 1;
                    dp[i][j] = 1; // Mark it as full

                    // Overflow champagne to the next level
                    if (i + 1 < n) { // Ensure we don't go out of bounds
                        dp[i + 1][j] += excess / 2.0;   // Left child
                        dp[i + 1][j + 1] += excess / 2.0; // Right child
                    }
                }
            }
        }

        System.out.println(fullGlasses);
    }
}
