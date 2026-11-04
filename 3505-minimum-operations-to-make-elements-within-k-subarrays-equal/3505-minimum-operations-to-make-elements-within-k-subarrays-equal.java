
import java.util.*;

class Solution {
    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long leftSum, rightSum;
    int leftSize, rightSize;

    void add(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            left.merge(val, 1, Integer::sum);
            leftSum += val;
            leftSize++;
        } else {
            right.merge(val, 1, Integer::sum);
            rightSum += val;
            rightSize++;
        }
        balance();
    }

    void remove(int val) {
        if (left.containsKey(val)) {
            erase(left, val);
            leftSum -= val;
            leftSize--;
        } else {
            erase(right, val);
            rightSum -= val;
            rightSize--;
        }
        balance();
    }

    void erase(TreeMap<Integer, Integer> map, int val) {
        if (map.get(val) == 1) {
            map.remove(val);
        } else {
            map.put(val, map.get(val) - 1);
        }
    }

    void balance() {
        int target = (leftSize + rightSize + 1) / 2;

        while (leftSize > target) {
            int val = left.lastKey();
            erase(left, val);
            leftSum -= val;
            leftSize--;

            right.merge(val, 1, Integer::sum);
            rightSum += val;
            rightSize++;
        }

        while (leftSize < target) {
            int val = right.firstKey();
            erase(right, val);
            rightSum -= val;
            rightSize--;

            left.merge(val, 1, Integer::sum);
            leftSum += val;
            leftSize++;
        }
    }

    long getCost() {
        long median = left.lastKey();

        return median * leftSize - leftSum
                + rightSum - median * rightSize;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int m = n - x + 1;

        long[] cost = new long[m];

        left.clear();
        right.clear();
        leftSum = rightSum = 0;
        leftSize = rightSize = 0;

        for (int i = 0; i < x; i++) {
            add(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            remove(nums[i - x]);
            add(nums[i]);
            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        Arrays.fill(dp[0], 0);

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                dp[j][i] = dp[j][i - 1];

                if (i >= x) {
                    dp[j][i] = Math.min(
                        dp[j][i],
                        dp[j - 1][i - x] + cost[i - x]
                    );
                }
            }
        }

        return dp[k][n];
    }
}
