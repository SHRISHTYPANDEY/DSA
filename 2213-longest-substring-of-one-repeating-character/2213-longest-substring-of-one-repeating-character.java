class Solution {

    class Node {
        int len;
        int leftCnt;
        int rightCnt;
        int best;
        char leftChar;
        char rightChar;

        Node() {
            len = 0;
            leftCnt = 0;
            rightCnt = 0;
            best = 0;
        }
    }

    Node[] tree;

    Node merge(Node a, Node b) {
        if (a.len == 0) return b;
        if (b.len == 0) return a;

        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        res.leftCnt = a.leftCnt;
        res.rightCnt = b.rightCnt;

        res.best = Math.max(a.best, b.best);

        if (a.rightChar == b.leftChar) {

            res.best = Math.max(
                res.best,
                a.rightCnt + b.leftCnt
            );

            if (a.leftCnt == a.len) {
                res.leftCnt = a.len + b.leftCnt;
            }

            if (b.rightCnt == b.len) {
                res.rightCnt = b.len + a.rightCnt;
            }
        }

        return res;
    }

    void build(int node, int l, int r, String s) {

        if (l == r) {
            tree[node].len = 1;
            tree[node].leftCnt = 1;
            tree[node].rightCnt = 1;
            tree[node].best = 1;

            tree[node].leftChar = s.charAt(l);
            tree[node].rightChar = s.charAt(l);

            return;
        }

        int mid = (l + r) / 2;

        build(node * 2, l, mid, s);
        build(node * 2 + 1, mid + 1, r, s);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int l, int r, int index, char ch) {

        if (l == r) {
            tree[node].leftChar = ch;
            tree[node].rightChar = ch;
            tree[node].leftCnt = 1;
            tree[node].rightCnt = 1;
            tree[node].best = 1;
            tree[node].len = 1;

            return;
        }

        int mid = (l + r) / 2;

        if (index <= mid) {
            update(node * 2, l, mid, index, ch);
        } else {
            update(node * 2 + 1, mid + 1, r, index, ch);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    public int[] longestRepeating(
        String s,
        String queryCharacters,
        int[] queryIndices
    ) {

        int n = s.length();

        tree = new Node[4 * n];

        for (int i = 0; i < 4 * n; i++) {
            tree[i] = new Node();
        }

        build(1, 0, n - 1, s);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {

            update(
                1,
                0,
                n - 1,
                queryIndices[i],
                queryCharacters.charAt(i)
            );

            ans[i] = tree[1].best;
        }

        return ans;
    }
}