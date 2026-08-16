class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int x : stones) {
            cnt[x % 3]++;
        }

        int zero = cnt[0];
        int one = cnt[1];
        int two = cnt[2];

        if (zero % 2 == 0) {
            return one > 0 && two > 0;
        }

        return Math.abs(one - two) > 2;
    }
}