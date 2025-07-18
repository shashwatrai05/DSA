import java.util.Arrays;

public class Cross_Max_Sum {
    static int N = 2;
    static int[][] mat = {
            {1, 2},
            {3, 4},
    };

    static int[][] dp;

    public static void main(String[] args) {
        dp = new int[N][1 << N];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }

        System.out.println("Maximum Sum: " + maxSum(0, 0));
    }

    static int maxSum(int row, int mask) {
        if (row == N) {
            return 0;
        }
        if (dp[row][mask] != -1) {
            return dp[row][mask];
        }

        int ans = Integer.MIN_VALUE;
        for (int j = 0; j < N; j++) { // Iterate till N, not N-1
            int temp = 1 << j;
            if ((mask & temp) == 0) { // Check if j is set
                ans = Math.max(mat[row][j] + maxSum(row + 1, mask | temp), ans);
            }
        }
        return dp[row][mask] = ans;
    }
}
