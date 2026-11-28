import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        // cnt[f] = factor f wale items
        int[] cnt = new int[n + 1];
        long m = Long.MAX_VALUE;               // sabse sasta price
        for (int[] it : items) {
            cnt[it[0]]++;
            m = Math.min(m, it[1]);
        }

        // mult[f] = un items ki ginti jinka factor f ka multiple hai
        int[] mult = new int[n + 1];
        for (int f = 1; f <= n; f++) {
            for (int x = f; x <= n; x += f) {
                mult[f] += cnt[x];
            }
        }

        // sirf useful value-2 groups lo: d > 0 aur price < 2m
        long[] keys = new long[n];
        int sz = 0;
        for (int[] it : items) {
            int d = mult[it[0]] - 1;           // khud ko minus
            long p = it[1];
            if (d > 0 && p < 2 * m) {
                keys[sz++] = (p << 20) | d;    // price upar, d neeche (d < 2^20)
            }
        }
        Arrays.sort(keys, 0, sz);              // price ke hisaab se sorted

        long R = budget;
        long k = 0;
        for (int i = 0; i < sz; i++) {
            long p = keys[i] >> 20;
            long d = keys[i] & ((1L << 20) - 1);
            long t = Math.min(d, R / p);       // is price ki kitni copies afford hain
            k += t;
            R -= t * p;
            if (t < d) break;                  // aage ke items aur mehnge hain
        }

        return (int) (2 * k + R / m);
    }
}