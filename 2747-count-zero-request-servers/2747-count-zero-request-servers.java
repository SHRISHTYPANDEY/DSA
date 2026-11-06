class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);

        int m = queries.length;
        int[][] sortedQueries = new int[m][2];

        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        Arrays.sort(sortedQueries, (a, b) -> a[0] - b[0]);

        int[] ans = new int[m];
        int[] freq = new int[n + 1];

        int left = 0, right = 0;
        int activeServers = 0;

        for (int[] q : sortedQueries) {
            int start = q[0] - x;
            int end = q[0];

            while (right < logs.length && logs[right][1] <= end) {
                int id = logs[right][0];

                if (freq[id] == 0) {
                    activeServers++;
                }

                freq[id]++;
                right++;
            }

            while (left < right && logs[left][1] < start) {
                int id = logs[left][0];

                freq[id]--;

                if (freq[id] == 0) {
                    activeServers--;
                }

                left++;
            }

            ans[q[1]] = n - activeServers;
        }

        return ans;
    }
}