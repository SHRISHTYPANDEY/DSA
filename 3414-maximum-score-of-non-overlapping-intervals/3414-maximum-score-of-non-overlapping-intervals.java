import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class Result {
        long score;
        int[] indices;

        Result(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    Interval[] arr;
    int n;
    Result[][] dp;

    public int[] maximumWeight(List<List<Integer>> intervals) {
        n = intervals.size();
        arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            List<Integer> cur = intervals.get(i);

            arr[i] = new Interval(
                cur.get(0),
                cur.get(1),
                cur.get(2),
                i
            );
        }

        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l) {
                return Integer.compare(a.l, b.l);
            }
            return Integer.compare(a.r, b.r);
        });

        dp = new Result[n + 1][5];

        Result ans = solve(0, 0);

        int[] result = ans.indices;
        Arrays.sort(result);

        return result;
    }

    private Result solve(int pos, int count) {

        if (count == 4 || pos == n) {
            return new Result(0, new int[0]);
        }

        if (dp[pos][count] != null) {
            return dp[pos][count];
        }

        // Option 1: Skip current interval
        Result skip = solve(pos + 1, count);

        // Option 2: Take current interval
        int next = findNext(pos);

        Result future = solve(next, count + 1);

        int[] chosen = new int[future.indices.length + 1];

        chosen[0] = arr[pos].idx;

        for (int i = 0; i < future.indices.length; i++) {
            chosen[i + 1] = future.indices[i];
        }

        Arrays.sort(chosen);

        Result take = new Result(
            arr[pos].w + future.score,
            chosen
        );

        // Select the better result
        if (take.score > skip.score) {
            dp[pos][count] = take;
        } else if (take.score < skip.score) {
            dp[pos][count] = skip;
        } else {
            if (compare(take.indices, skip.indices) < 0) {
                dp[pos][count] = take;
            } else {
                dp[pos][count] = skip;
            }
        }

        return dp[pos][count];
    }

    // Find first interval with start > current end
    private int findNext(int pos) {
        int low = pos + 1;
        int high = n;

        int end = arr[pos].r;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid].l > end) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        return low;
    }

    // Lexicographical comparison
    private int compare(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }
}