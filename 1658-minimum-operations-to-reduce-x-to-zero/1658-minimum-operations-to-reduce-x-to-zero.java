class Solution {
    public int minOperations(int[] nums, int x) {
        int n = nums.length;

        long total = 0;

        for (int num : nums) {
            total += num;
        }

        long target = total - x;

        // If target is negative, it is impossible.
        if (target < 0) {
            return -1;
        }

        // If target is 0, remove all elements.
        if (target == 0) {
            return n;
        }

        int left = 0;
        long sum = 0;
        int maxLen = -1;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            // Shrink the window if its sum is too large.
            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            // Check if the current window has the target sum.
            if (sum == target) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }

        if (maxLen == -1) {
            return -1;
        }

        return n - maxLen;
    }
}