class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        int[] dp = new int[1000003]; // max value 10^6, x+1 tak jayega
        int ans = 1;

        for (int x : nums) {
            dp[x + 1] = dp[x] + 1;     // pehle x+1 (purani dp[x] use hogi)
            dp[x] = dp[x - 1] + 1;     // phir x (dp[x-1] use hogi)

            ans = Math.max(ans, Math.max(dp[x], dp[x + 1]));
        }
        return ans;
    }
}