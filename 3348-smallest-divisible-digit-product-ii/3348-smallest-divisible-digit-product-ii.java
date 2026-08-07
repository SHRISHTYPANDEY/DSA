import java.util.*;

class Solution {

    int A, B, C, D;
    int[] memo;
    final int INF = 1_000_000;

    // prime factors of each digit
    int[] f2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
    int[] f3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
    int[] f5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    int[] f7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

    int getIndex(int a, int b, int c, int d) {
        return (((a * (B + 1) + b) * (C + 1) + c) * (D + 1) + d);
    }

    // Minimum number of digits required to satisfy remaining factors
    int minDigits(int a, int b, int c, int d) {

        if (a == 0 && b == 0 && c == 0 && d == 0) {
            return 0;
        }

        int idx = getIndex(a, b, c, d);

        if (memo[idx] != -1) {
            return memo[idx];
        }

        int ans = INF;

        for (int digit = 2; digit <= 9; digit++) {

            int na = Math.max(0, a - f2[digit]);
            int nb = Math.max(0, b - f3[digit]);
            int nc = Math.max(0, c - f5[digit]);
            int nd = Math.max(0, d - f7[digit]);

            if (na == a && nb == b && nc == c && nd == d) {
                continue;
            }

            int res = minDigits(na, nb, nc, nd);

            if (res != INF) {
                ans = Math.min(ans, 1 + res);
            }
        }

        return memo[idx] = ans;
    }

    int[] useDigit(int a, int b, int c, int d, int digit) {

        return new int[] {
            Math.max(0, a - f2[digit]),
            Math.max(0, b - f3[digit]),
            Math.max(0, c - f5[digit]),
            Math.max(0, d - f7[digit])
        };
    }

    // Build lexicographically smallest suffix
    String build(int a, int b, int c, int d, int len) {

        StringBuilder ans = new StringBuilder();

        for (int pos = 0; pos < len; pos++) {

            int remainingSlots = len - pos - 1;

            for (int digit = 1; digit <= 9; digit++) {

                int[] next = useDigit(a, b, c, d, digit);

                int need = minDigits(
                    next[0],
                    next[1],
                    next[2],
                    next[3]
                );

                if (need <= remainingSlots) {

                    ans.append(digit);

                    a = next[0];
                    b = next[1];
                    c = next[2];
                    d = next[3];

                    break;
                }
            }
        }

        return ans.toString();
    }

    public String smallestNumber(String num, long t) {

        // -----------------------------
        // 1. Factorize t
        // -----------------------------

        long x = t;

        A = B = C = D = 0;

        while (x % 2 == 0) {
            A++;
            x /= 2;
        }

        while (x % 3 == 0) {
            B++;
            x /= 3;
        }

        while (x % 5 == 0) {
            C++;
            x /= 5;
        }

        while (x % 7 == 0) {
            D++;
            x /= 7;
        }

        // t contains some prime other than 2,3,5,7
        if (x != 1) {
            return "-1";
        }

        // -----------------------------
        // 2. Initialize DP
        // -----------------------------

        int size = (A + 1) * (B + 1) * (C + 1) * (D + 1);

        memo = new int[size];
        Arrays.fill(memo, -1);

        // -----------------------------
        // 3. Check if num itself works
        // -----------------------------

        boolean zeroFree = true;

        int r2 = A;
        int r3 = B;
        int r5 = C;
        int r7 = D;

        for (int i = 0; i < num.length(); i++) {

            int digit = num.charAt(i) - '0';

            if (digit == 0) {
                zeroFree = false;
                break;
            }

            r2 = Math.max(0, r2 - f2[digit]);
            r3 = Math.max(0, r3 - f3[digit]);
            r5 = Math.max(0, r5 - f5[digit]);
            r7 = Math.max(0, r7 - f7[digit]);
        }

        if (zeroFree &&
            r2 == 0 &&
            r3 == 0 &&
            r5 == 0 &&
            r7 == 0) {

            return num;
        }

        // -----------------------------
        // 4. Prefix factor counts
        // -----------------------------

        int n = num.length();

        int[] p2 = new int[n + 1];
        int[] p3 = new int[n + 1];
        int[] p5 = new int[n + 1];
        int[] p7 = new int[n + 1];

        for (int i = 0; i < n; i++) {

            int digit = num.charAt(i) - '0';

            p2[i + 1] = p2[i] + f2[digit];
            p3[i + 1] = p3[i] + f3[digit];
            p5[i + 1] = p5[i] + f5[digit];
            p7[i + 1] = p7[i] + f7[digit];
        }

        int firstZero = num.indexOf('0');

        // -----------------------------
        // 5. Try same length
        // -----------------------------

        for (int i = n - 1; i >= 0; i--) {

            if (firstZero != -1 && firstZero < i) {
    continue;
}

            int rem2 = Math.max(0, A - p2[i]);
            int rem3 = Math.max(0, B - p3[i]);
            int rem5 = Math.max(0, C - p5[i]);
            int rem7 = Math.max(0, D - p7[i]);

            int current = num.charAt(i) - '0';

            // Put a digit greater than current digit
            for (int digit = current + 1; digit <= 9; digit++) {

                int nr2 = Math.max(0, rem2 - f2[digit]);
                int nr3 = Math.max(0, rem3 - f3[digit]);
                int nr5 = Math.max(0, rem5 - f5[digit]);
                int nr7 = Math.max(0, rem7 - f7[digit]);

                int slots = n - i - 1;

                if (minDigits(nr2, nr3, nr5, nr7) <= slots) {

                    StringBuilder ans = new StringBuilder();

                    // Equal prefix
                    ans.append(num, 0, i);

                    // Greater digit
                    ans.append(digit);

                    // Smallest possible suffix
                    ans.append(build(
                        nr2, nr3, nr5, nr7,
                        slots
                    ));

                    return ans.toString();
                }
            }
        }

        int need = minDigits(A, B, C, D);

        int length = Math.max(n + 1, need);

return build(A, B, C, D, length);
    }
}