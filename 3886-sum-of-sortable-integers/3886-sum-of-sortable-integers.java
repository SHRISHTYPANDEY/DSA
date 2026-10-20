class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;
            if (check(nums, k)) ans += k;
        }
        return ans;
    }

    private boolean check(int[] a, int k) {
        int n = a.length;
        int prevMax = Integer.MIN_VALUE;
        for (int s = 0; s < n; s += k) {
            int descents = 0;
            int descentPos = -1;
            for (int i = s; i < s + k; i++) {
                int next = (i + 1 < s + k) ? a[i + 1] : a[s];
                if (a[i] > next) {
                    descents++;
                    descentPos = i;
                }
            }
            if (descents > 1) return false;

            int mn, mx;
            if (descents == 0) {
                mn = a[s];
                mx = a[s + k - 1];
            } else {
                // after the descent starts the sorted order
                int startIdx = (descentPos + 1 < s + k) ? descentPos + 1 : s;
                mn = a[startIdx];
                mx = a[descentPos];
            }
            if (mn < prevMax) return false;
            prevMax = mx;
        }
        return true;
    }
}