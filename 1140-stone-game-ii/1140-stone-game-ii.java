class Solution {
    int[][] dp;
    int[] suffix;

    public int stoneGameII(int[] piles) {
        int n = piles.length;

        dp = new int[n][n + 1];
        suffix = new int[n + 1];

        // Suffix sum
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        return solve(0, 1, n);
    }

    private int solve(int i, int M, int n) {

        if (i >= n) {
            return 0;
        }

        if (2 * M >= n - i) {
            return suffix[i];
        }

        if (dp[i][M] != 0) {
            return dp[i][M];
        }

        int best = 0;

        for (int X = 1; X <= 2 * M && i + X <= n; X++) {

            int newM = Math.max(M, X);

            int current = suffix[i]
                    - solve(i + X, newM, n);

            best = Math.max(best, current);
        }

        return dp[i][M] = best;
    }
}