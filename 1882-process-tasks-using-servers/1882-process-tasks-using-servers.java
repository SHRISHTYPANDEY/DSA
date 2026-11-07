import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length, m = tasks.length;

        // free: {weight, index}
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1])
        );
        // busy: {freeTime, weight, index}
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0])
                    : a[1] != b[1] ? Long.compare(a[1], b[1])
                    : Long.compare(a[2], b[2])
        );

        for (int i = 0; i < n; i++) free.offer(new int[]{servers[i], i});

        int[] ans = new int[m];
        long t = 0;

        for (int j = 0; j < m; j++) {
            t = Math.max(t, j);

            // koi free nahi hai -> earliest server free hone tak wait
            if (free.isEmpty()) t = Math.max(t, busy.peek()[0]);

            // jo servers t tak free ho chuke hain, unhe free heap mein wapas daalo
            while (!busy.isEmpty() && busy.peek()[0] <= t) {
                long[] s = busy.poll();
                free.offer(new int[]{(int) s[1], (int) s[2]});
            }

            int[] best = free.poll();
            ans[j] = best[1];
            busy.offer(new long[]{t + tasks[j], best[0], best[1]});
        }
        return ans;
    }
}