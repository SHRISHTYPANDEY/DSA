class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;

        // minLen[i] = shortest valid subarray
        // ending at or before index i
        int[] minLen = new int[n];

        int INF = Integer.MAX_VALUE;
        java.util.Arrays.fill(minLen, INF);

        int left = 0;
        int sum = 0;
        int ans = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            // Shrink window if sum becomes too large
            while (sum > target) {
                sum -= arr[left++];
            }

            // Carry forward previous minimum
            if (right > 0) {
                minLen[right] = minLen[right - 1];
            }

            // Found a valid subarray
            if (sum == target) {
                int len = right - left + 1;

                // Need a valid subarray completely
                // before the current window
                if (left > 0 && minLen[left - 1] != INF) {
                    ans = Math.min(
                        ans,
                        len + minLen[left - 1]
                    );
                }

                // Update shortest valid subarray
                minLen[right] = Math.min(
                    minLen[right],
                    len
                );
            }
        }

        return ans == INF ? -1 : ans;
    }
}