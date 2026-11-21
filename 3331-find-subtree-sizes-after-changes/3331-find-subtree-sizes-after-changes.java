
class Solution {
    List<Integer>[] tree;
    int[] newParent;
    int[] ans;

    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        tree = new ArrayList[n];
        newParent = parent.clone();
        ans = new int[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        // Find new parents using original tree
        dfs(0, parent, s, last);

        // Build modified tree
        for (int i = 0; i < n; i++) {
            tree[i].clear();
        }

        for (int i = 1; i < n; i++) {
            tree[newParent[i]].add(i);
        }

        // Calculate subtree sizes
        calculateSize(0);

        return ans;
    }

    void dfs(int node, int[] parent, String s, int[] last) {
        int ch = s.charAt(node) - 'a';

        int old = last[ch];

        if (old != -1) {
            newParent[node] = old;
        }

        last[ch] = node;

        for (int child : tree[node]) {
            dfs(child, parent, s, last);
        }

        last[ch] = old;
    }

    int calculateSize(int node) {
        int size = 1;

        for (int child : tree[node]) {
            size += calculateSize(child);
        }

        return ans[node] = size;
    }
}
