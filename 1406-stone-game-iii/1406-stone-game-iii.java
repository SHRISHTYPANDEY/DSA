class Solution {
    public String stoneGameIII(int[] stoneValue) {

        int n = stoneValue.length;
        int[] dp = new int[n + 1];

        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {

            int sum = 0;
            int best = Integer.MIN_VALUE;

            for (int j = i; j < Math.min(i + 3, n); j++) {

                sum += stoneValue[j];

                best = Math.max(
                    best,
                    sum - dp[j + 1]
                );
            }

            dp[i] = best;
        }

        if (dp[0] > 0)
            return "Alice";

        if (dp[0] < 0)
            return "Bob";

        return "Tie";
    }
}