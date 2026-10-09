
class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int insertions = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (ch == '(') {
                open++;
            } else {
                // Check whether the next character is also ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    // Insert one ')' to complete the pair
                    insertions++;
                }

                // Match this closing pair with an opening '('
                if (open > 0) {
                    open--;
                } else {
                    // Insert one '(' because no opening bracket exists
                    insertions++;
                }
            }
        }

        // Each unmatched '(' needs two closing brackets
        insertions += open * 2;

        return insertions;
    }
}