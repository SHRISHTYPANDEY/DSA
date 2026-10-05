class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                stack.push(0);
            } else {
                int current = stack.pop();

                int score;
                if (current == 0) {
                    score = 1;
                } else {
                    score = 2 * current;
                }

                stack.push(stack.pop() + score);
            }
        }

        return stack.pop();
    }
}