
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // Step 1: Calculate reward for every item
        int[] reward = new int[n];

        for (int i = 0; i < n; i++) {
            int free = 0;

            for (int j = 0; j < n; j++) {
                if (i != j &&
                    items[j][0] % items[i][0] == 0) {
                    free++;
                }
            }

            reward[i] = free + 1;
        }

        // Group items by price
        List<Integer>[] groups = new ArrayList[budget + 1];

        for (int i = 0; i <= budget; i++) {
            groups[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            int price = items[i][1];

            if (price <= budget) {
                groups[price].add(i);
            }
        }

        // dp: items having price greater than current price
        int[] dp = new int[budget + 1];

        int ans = 0;

        // Process prices in descending order
        for (int m = budget; m >= 1; m--) {

            if (groups[m].isEmpty()) {
                continue;
            }

            int NEG = -1000000000;

            // Must select at least one item of price m
            int[] must = new int[budget + 1];
            Arrays.fill(must, NEG);

            // Step 2: Process current price group
            for (int idx : groups[m]) {

                int w = reward[idx];

                // 0/1 knapsack
                for (int c = budget; c >= m; c--) {

                    // Select this item as the first
                    // item of current price group
                    if (dp[c - m] != NEG) {
                        must[c] = Math.max(
                            must[c],
                            dp[c - m] + w
                        );
                    }

                    // Select another item of same price
                    if (must[c - m] != NEG) {
                        must[c] = Math.max(
                            must[c],
                            must[c - m] + w
                        );
                    }
                }
            }

            // Step 3: Calculate answer for minimum price m
            for (int c = m; c <= budget; c++) {

                if (must[c] == NEG) {
                    continue;
                }

                int extra = (budget - c) / m;

                ans = Math.max(
                    ans,
                    must[c] + extra
                );
            }

            // Step 4: Add current group to normal DP
            // for the next smaller minimum price
            for (int idx : groups[m]) {

                int w = reward[idx];

                for (int c = budget; c >= m; c--) {
                    dp[c] = Math.max(
                        dp[c],
                        dp[c - m] + w
                    );
                }
            }
        }

        return ans;
    }
}
