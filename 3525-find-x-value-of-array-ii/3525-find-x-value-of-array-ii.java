class Solution {
    
    class Node {
        int product;
        int[] count;

        Node() {
            count = new int[k];
        }
    }

    int n, k;
    Node[] tree;

    Node merge(Node left, Node right) {
        Node res = new Node();

        // Product of complete segment
        res.product = (left.product * right.product) % k;

        // Prefixes completely inside left
        for (int r = 0; r < k; r++) {
            res.count[r] += left.count[r];
        }

        // Prefixes which contain all of left
        // and then some prefix of right
        for (int r = 0; r < k; r++) {
            int newRemainder = (left.product * r) % k;
            res.count[newRemainder] += right.count[r];
        }

        return res;
    }

    void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            tree[node] = new Node();

            int rem = nums[l] % k;

            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        build(node * 2, l, mid, nums);
        build(node * 2 + 1, mid + 1, r, nums);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int l, int r, int pos, int value) {
        if (l == r) {
            tree[node] = new Node();

            int rem = value % k;

            tree[node].product = rem;
            tree[node].count[rem] = 1;

            return;
        }

        int mid = l + (r - l) / 2;

        if (pos <= mid) {
            update(node * 2, l, mid, pos, value);
        } else {
            update(node * 2 + 1, mid + 1, r, pos, value);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node query(int node, int l, int r, int start) {

        if (r < start) {
            return null;
        }

        if (start <= l) {
            return tree[node];
        }

        int mid = l + (r - l) / 2;

        Node left = query(node * 2, l, mid, start);
        Node right = query(node * 2 + 1, mid + 1, r, start);

        if (left == null) return right;
        if (right == null) return left;

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        build(1, 0, n - 1, nums);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Update nums[index]
            update(1, 0, n - 1, index, value);

            // Get information for [start ... n-1]
            Node res = query(1, 0, n - 1, start);

            result[i] = res.count[x];
        }

        return result;
    }
}