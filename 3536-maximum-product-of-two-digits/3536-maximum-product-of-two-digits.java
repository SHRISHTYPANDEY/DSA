class Solution {
    public int maxProduct(int n) {
        int[] digits = new int[10];
        int len = 0;

        while (n > 0) {
            digits[len++] = n % 10;
            n /= 10;
        }

        int max = 0;

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                max = Math.max(max, digits[i] * digits[j]);
            }
        }

        return max;
    }
}