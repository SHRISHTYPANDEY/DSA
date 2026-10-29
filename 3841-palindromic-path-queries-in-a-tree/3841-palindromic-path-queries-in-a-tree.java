
import java.util.*;

class Solution {
    private List<Integer>[] adj;
    private int[] tin, tout, depth, parent;
    private int[][] up;
    private int[] bit;
    private char[] chars;
    private int timer, LOG;

    public List<Boolean> palindromePath(int n, int[][] edges, String s,
                                        String[] queries) {
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        chars = s.toCharArray();
        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        parent = new int[n];
        timer = 0;

        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        up = new int[LOG][n];
        bit = new int[n + 2];

        dfs(0, -1);

        for (int v = 0; v < n; v++) {
            up[0][v] = parent[v];
        }

        for (int j = 1; j < LOG; j++) {
            for (int v = 0; v < n; v++) {
                up[j][v] = up[j - 1][up[j - 1][v]];
            }
        }

        for (int v = 0; v < n; v++) {
            int mask = 1 << (chars[v] - 'a');
            rangeUpdate(tin[v], tout[v], mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                char c = p[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');
                int delta = oldMask ^ newMask;

                rangeUpdate(tin[u], tout[u], delta);
                chars[u] = c;
            } else {
                int v = Integer.parseInt(p[2]);
                int lca = getLCA(u, v);

                int maskU = pointQuery(tin[u]);
                int maskV = pointQuery(tin[v]);
                int maskL = pointQuery(tin[lca]);

                int pathMask = maskU ^ maskV ^ (1 << (chars[lca] - 'a'));

                ans.add((pathMask & (pathMask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int u, int p) {
        parent[u] = (p == -1) ? u : p;
        tin[u] = timer++;

        for (int v : adj[u]) {
            if (v == p) continue;
            depth[v] = depth[u] + 1;
            dfs(v, u);
        }

        tout[u] = timer;
    }

    private void rangeUpdate(int l, int r, int mask) {
        updateBIT(l, mask);
        updateBIT(r, mask);
    }

    private void updateBIT(int idx, int mask) {
        for (int i = idx + 1; i < bit.length; i += i & -i) {
            bit[i] ^= mask;
        }
    }

    private int pointQuery(int idx) {
        int mask = 0;
        for (int i = idx + 1; i > 0; i -= i & -i) {
            mask ^= bit[i];
        }
        return mask;
    }

    private int getLCA(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return parent[u];
    }
}
