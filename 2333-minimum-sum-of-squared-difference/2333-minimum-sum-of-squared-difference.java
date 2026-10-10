
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int n = nums1.length;

        int[] diff = new int[n];
        int maxDiff = 0;

        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
        }

        long totalDiff = 0;
        for (int d : diff) {
            totalDiff += d;
        }

        // All differences can become zero
        if (k >= totalDiff) {
            return 0;
        }

        int low = 0, high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long operations = 0;

            for (int d : diff) {
                if (d > mid) {
                    operations += d - mid;
                }
            }

            if (operations <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int target = low;
        long answer = 0;
        long used = 0;

        for (int d : diff) {
            if (d > target) {
                used += d - target;
                d = target;
            }
            answer += (long) d * d;
        }

        // Reduce remaining operations one level below target.
        long remaining = k - used;

        // Count differences equal to target.
        long count = 0;
        for (int d : diff) {
            if (d >= target) {
                count++;
            }
        }

        answer -= remaining * (2L * target - 1);

        return answer;
    }
}