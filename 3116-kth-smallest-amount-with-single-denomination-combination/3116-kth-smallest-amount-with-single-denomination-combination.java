import java.util.*;

class Solution {

    long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    long lcm(long a, long b, long limit) {
        long g = gcd(a, b);

        if (a / g > limit / b) {
            return limit + 1;
        }

        return (a / g) * b;
    }

    long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        for (int mask = 1; mask < (1 << n); mask++) {

            long multiple = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {
                    bits++;

                    multiple = lcm(multiple, coins[i], x);

                    if (multiple > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            long contribution = x / multiple;

            if (bits % 2 == 1) {
                total += contribution;
            } else {
                total -= contribution;
            }
        }

        return total;
    }

    public long findKthSmallest(int[] coins, int k) {

        Arrays.sort(coins);

        int m = 0;

        for (int coin : coins) {
            if (m == 0 || coins[m - 1] != coin) {
                coins[m++] = coin;
            }
        }

        int[] uniqueCoins = Arrays.copyOf(coins, m);

        long low = 1;
        long high = (long) uniqueCoins[0] * k;

        while (low < high) {

            long mid = low + (high - low) / 2;

            if (count(mid, uniqueCoins) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }
}