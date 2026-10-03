class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();

        if (n == 0) {
            return 0;
        }

        int[] dp = new int[n];
        int max = 0;

        for (int i = 1; i < n; i++) {

            if (s.charAt(i) == ')') {

                // Case 1: "()"
                if (s.charAt(i - 1) == '(') {
                    dp[i] = 2;

                    if (i >= 2) {
                        dp[i] += dp[i - 2];
                    }
                }

                // Case 2: "(...)"
                else {
                    int prev = i - dp[i - 1] - 1;

                    if (prev >= 0 && s.charAt(prev) == '(') {
                        dp[i] = dp[i - 1] + 2;

                        if (prev >= 1) {
                            dp[i] += dp[prev - 1];
                        }
                    }
                }
            }

            max = Math.max(max, dp[i]);
        }

        return max;
    }
}