import java.util.*;

class Solution {

    private String s;
    private int index;

    public List<String> braceExpansionII(String expression) {
        s = expression;
        index = 0;

        Set<String> result = parseUnion();

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    // Handles:
    // A,B,C
    private Set<String> parseUnion() {

        Set<String> result = parseConcat();

        while (index < s.length() && s.charAt(index) == ',') {
            index++; // skip ','

            Set<String> next = parseConcat();
            result.addAll(next);
        }

        return result;
    }

    // Handles:
    // ABC
    // {a,b}cd
    // a{b,c}{d,e}
    private Set<String> parseConcat() {

        Set<String> result = new HashSet<>();
        result.add("");

        while (index < s.length()
                && s.charAt(index) != '}'
                && s.charAt(index) != ',') {

            Set<String> next;

            if (s.charAt(index) == '{') {

                index++; // skip '{'

                next = parseUnion();

                index++; // skip '}'

            } else {

                next = new HashSet<>();
                next.add(String.valueOf(s.charAt(index)));

                index++;
            }

            result = concatenate(result, next);
        }

        return result;
    }

    private Set<String> concatenate(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}