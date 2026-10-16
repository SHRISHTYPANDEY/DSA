class Solution {
    static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int size = 4 * n;

        long[] sum = new long[size];
        long[] squareSum = new long[size];
        long[] lazy = new long[size];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int r = 0; r < n; r++) {
            int x = nums[r];
            int left = last[x] + 1;

            update(1, 0, n - 1, left, r,
                   sum, squareSum, lazy);

            ans = (ans + squareSum[1]) % MOD;
            last[x] = r;
        }

        return (int) ans;
    }

    private void update(int node, int start, int end,
                        int l, int r,
                        long[] sum, long[] squareSum,
                        long[] lazy) {

        if (r < start || end < l) {
            return;
        }

        if (l <= start && end <= r) {
            long len = end - start + 1L;

            squareSum[node] =
                (squareSum[node] + 2 * sum[node] + len) % MOD;

            sum[node] = (sum[node] + len) % MOD;
            lazy[node]++;
            return;
        }

        push(node, start, end, sum, squareSum, lazy);

        int mid = start + (end - start) / 2;

        update(node * 2, start, mid, l, r,
               sum, squareSum, lazy);

        update(node * 2 + 1, mid + 1, end, l, r,
               sum, squareSum, lazy);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] =
            (squareSum[node * 2] + squareSum[node * 2 + 1]) % MOD;
    }

    private void push(int node, int start, int end,
                      long[] sum, long[] squareSum,
                      long[] lazy) {

        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = start + (end - start) / 2;

        apply(node * 2, start, mid, lazy[node],
              sum, squareSum, lazy);

        apply(node * 2 + 1, mid + 1, end, lazy[node],
              sum, squareSum, lazy);

        lazy[node] = 0;
    }

    private void apply(int node, int start, int end, long delta,
                       long[] sum, long[] squareSum,
                       long[] lazy) {

        long len = end - start + 1L;

        squareSum[node] =
            (squareSum[node] + 2 * delta % MOD * sum[node] % MOD
             + delta % MOD * delta % MOD * len) % MOD;

        sum[node] = (sum[node] + delta * len) % MOD;
        lazy[node] = (lazy[node] + delta) % MOD;
    }
}