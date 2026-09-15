class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        boolean[][] pal = new boolean[n][n];

        // Build palindrome DP
        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {
                int j = i + len - 1;

                if (len == 1) {
                    pal[i][j] = true;
                } else if (len == 2) {
                    pal[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    pal[i][j] = s.charAt(i) == s.charAt(j)
                            && pal[i + 1][j - 1];
                }
            }
        }

        int[] dp = new int[n + 1];

        for (int j = 1; j <= n; j++) {
            // Skip the current character
            dp[j] = dp[j - 1];

            // Try every palindrome ending at j - 1
            for (int i = 0; i < j; i++) {
                if (j - i >= k && pal[i][j - 1]) {
                    dp[j] = Math.max(dp[j], dp[i] + 1);
                }
            }
        }

        return dp[n];
    }
}