class Solution {
    public int numberOfSets(int n, int k) {
        int MOD = 1_000_000_007;

        int total = n + k - 1;
        int choose = 2 * k;

        long[] dp = new long[choose + 1];
        dp[0] = 1;

        for (int i = 1; i <= total; i++) {
            for (int j = Math.min(i, choose); j >= 1; j--) {
                dp[j] = (dp[j] + dp[j - 1]) % MOD;
            }
        }

        return (int) dp[choose];
    }
}