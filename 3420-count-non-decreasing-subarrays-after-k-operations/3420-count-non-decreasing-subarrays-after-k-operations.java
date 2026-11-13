
class Solution {

    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;
        int LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        // Prefix sum
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Find next strictly greater element
        int[] next = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {

            while (top >= 0 &&
                   nums[stack[top]] <= nums[i]) {
                top--;
            }

            next[i] = (top == -1) ? n : stack[top];

            stack[++top] = i;
        }

        // Binary lifting tables
        int[][] jump = new int[LOG][n + 1];
        long[][] sum = new long[LOG][n + 1];

        for (int j = 0; j < LOG; j++) {
            jump[j][n] = n;
        }

        for (int i = 0; i < n; i++) {

            jump[0][i] = next[i];

            sum[0][i] =
                (long) nums[i] * (next[i] - i);
        }

        for (int j = 1; j < LOG; j++) {

            for (int i = 0; i < n; i++) {

                int mid = jump[j - 1][i];

                jump[j][i] = jump[j - 1][mid];

                sum[j][i] =
                    sum[j - 1][i] +
                    sum[j - 1][mid];
            }
        }

        long ans = 0;

        // Sliding window with binary search
        for (int right = 0; right < n; right++) {

            int low = 0;
            int high = right;
            int best = right;

            while (low <= high) {

                int mid = low + (high - low) / 2;

                long cost = getCost(
                    mid, right, nums,
                    prefix, jump, sum, LOG
                );

                if (cost <= k) {

                    best = mid;
                    high = mid - 1;

                } else {

                    low = mid + 1;
                }
            }

            ans += right - best + 1;
        }

        return ans;
    }

    private long getCost(
        int left,
        int right,
        int[] nums,
        long[] prefix,
        int[][] jump,
        long[][] sum,
        int LOG
    ) {

        long maxSum = 0;
        int idx = left;

        // Calculate sum of prefix maximums
        for (int j = LOG - 1; j >= 0; j--) {

            int nxt = jump[j][idx];

            if (nxt <= right + 1) {

                maxSum += sum[j][idx];
                idx = nxt;
            }
        }

        // Remaining partial block
        if (idx <= right) {

            maxSum +=
                (long) nums[idx] * (right - idx + 1);
        }

        long originalSum =
            prefix[right + 1] - prefix[left];

        return maxSum - originalSum;
    }
}
