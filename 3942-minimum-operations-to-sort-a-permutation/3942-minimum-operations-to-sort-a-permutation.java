class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        // Case 1: Increasing cyclic order
        boolean forward = true;
        int k = nums[0];

        for (int i = 0; i < n; i++) {
            if (nums[i] != (k + i) % n) {
                forward = false;
                break;
            }
        }

        int ans = Integer.MAX_VALUE;

        if (forward) {
            if (k == 0) {
                ans = 0;
            } else {
                ans = Math.min(n - k, k + 2);
            }
        }

        // Case 2: Decreasing cyclic order
        boolean backward = true;
        k = nums[0];

        for (int i = 0; i < n; i++) {
            if (nums[i] != (k - i + n) % n) {
                backward = false;
                break;
            }
        }

        if (backward) {
            int shift = n - 1 - nums[0];
            int cost = Math.min(shift, n - shift) + 1;
            ans = Math.min(ans, cost);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}