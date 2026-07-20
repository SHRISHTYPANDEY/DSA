class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        int total = m * n;
        k %= total;

        int[][] ans = new int[m][n];

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                int curr = i * n + j;

                int next = (curr + k) % total;

                int r = next / n;
                int c = next % n;

                ans[r][c] = grid[i][j];
            }
        }

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            List<Integer> row = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                row.add(ans[i][j]);
            }

            res.add(row);
        }

        return res;
    }
}