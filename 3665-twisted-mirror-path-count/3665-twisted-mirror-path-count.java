
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        // dp[i][j][0] = ways arriving from left (moving right)
        // dp[i][j][1] = ways arriving from top (moving down)
        int[][][] dp = new int[m][n][2];

        // Starting position
        dp[0][0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {

                    // Entered mirror while moving right
                    // Reflection sends robot down
                    if (dp[i][j][0] > 0 && i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1] 
                            + (long)dp[i][j][0]) % MOD);
                    }

                    // Entered mirror while moving down
                    // Reflection sends robot right
                    if (dp[i][j][1] > 0 && j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0] 
                            + (long)dp[i][j][1]) % MOD);
                    }

                } else {

                    // Empty cell: combine incoming paths
                    long ways = (dp[i][j][0] 
                               + (long)dp[i][j][1]) % MOD;

                    // Move right
                    if (j + 1 < n) {
                        dp[i][j + 1][0] =
                            (int)((dp[i][j + 1][0] + ways) % MOD);
                    }

                    // Move down
                    if (i + 1 < m) {
                        dp[i + 1][j][1] =
                            (int)((dp[i + 1][j][1] + ways) % MOD);
                    }
                }
            }
        }

        return (int)(((long)dp[m - 1][n - 1][0]
                    + dp[m - 1][n - 1][1]) % MOD);
    }
}
