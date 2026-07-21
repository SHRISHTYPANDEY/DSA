class Solution {
    public int maxActiveSectionsAfterTrade(String s) {

        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }

        String t = "1" + s + "1";

        List<Character> type = new ArrayList<>();
        List<Integer> len = new ArrayList<>();

        int i = 0;
        while (i < t.length()) {
            char ch = t.charAt(i);
            int j = i;
            while (j < t.length() && t.charAt(j) == ch) j++;

            type.add(ch);
            len.add(j - i);

            i = j;
        }

        int ans = ones;

        for (i = 1; i + 1 < type.size(); i++) {

            if (type.get(i) == '1'
                    && type.get(i - 1) == '0'
                    && type.get(i + 1) == '0') {

                ans = Math.max(ans,
                        ones + len.get(i - 1) + len.get(i + 1));
            }
        }

        return ans;
    }
}