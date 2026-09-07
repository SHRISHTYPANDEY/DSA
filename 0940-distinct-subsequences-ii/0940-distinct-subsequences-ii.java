class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1000000007;

        long dp = 1; 

        long[] last = new long[26];

        for (char c : s.toCharArray()) {
            int index = c - 'a';

            long newDp = (2 * dp % MOD - last[index] + MOD) % MOD;

            last[index] = dp;
            dp = newDp;
        }

        return (int) ((dp - 1 + MOD) % MOD);
    }
}