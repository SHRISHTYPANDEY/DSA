class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Current level process karo
            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                // Check whether current string is valid
                if (isValid(current)) {
                    result.add(current);
                    found = true;
                }

                // Agar current level par valid mil gaya,
                // to next level generate nahi karna
                if (found) {
                    continue;
                }

                // Ek-ek character remove karo
                for (int j = 0; j < current.length(); j++) {

                    // Letters ko remove karne ki zarurat nahi
                    if (current.charAt(j) != '(' &&
                        current.charAt(j) != ')') {
                        continue;
                    }

                    String next =
                        current.substring(0, j)
                        + current.substring(j + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.add(next);
                    }
                }
            }

            // Current level mein valid answers mil gaye
            if (found) {
                break;
            }
        }

        return result;
    }

    private boolean isValid(String s) {

        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                balance++;
            }
            else if (ch == ')') {
                balance--;
            }

            // Closing bracket ke liye opening bracket nahi hai
            if (balance < 0) {
                return false;
            }
        }

        return balance == 0;
    }
}