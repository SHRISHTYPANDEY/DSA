
import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        // Step 1: Precompute minimum changes
        // required for every substring to become semi-palindrome.

        for (int len = 2; len <= n; len++) {
            for (int i = 0; i + len <= n; i++) {

                int minCost = Integer.MAX_VALUE;

                // Try every proper divisor d of len
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check palindrome in each group
                    for (int r = 0; r < d; r++) {

                        int left = i + r;
                        int right = i + r +
                                    ((len - 1 - r) / d) * d;

                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[i][i + len - 1] = minCost;
            }
        }

        // Step 2: Dynamic Programming

        int INF = 1000000;
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        // Divide string into exactly k substrings
        for (int parts = 1; parts <= k; parts++) {

            for (int end = 2 * parts; end <= n; end++) {

                for (int start = 2 * (parts - 1);
                     start <= end - 2; start++) {

                    if (dp[start][parts - 1] == INF) {
                        continue;
                    }

                    dp[end][parts] = Math.min(
                        dp[end][parts],
                        dp[start][parts - 1] +
                        cost[start][end - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}
