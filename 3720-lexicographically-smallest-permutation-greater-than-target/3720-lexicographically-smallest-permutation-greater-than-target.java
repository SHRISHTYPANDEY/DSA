class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        int n = s.length();

        for (int i = 0; i < n; i++) {
            int idx = target.charAt(i) - 'a';

            if (freq[idx] > 0) {
                freq[idx]--;
                continue;
            }

            for (int c = idx + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    StringBuilder ans =
                        new StringBuilder(target.substring(0, i));

                    ans.append((char) ('a' + c));
                    freq[c]--;

                    for (int x = 0; x < 26; x++) {
                        while (freq[x] > 0) {
                            ans.append((char) ('a' + x));
                            freq[x]--;
                        }
                    }

                    return ans.toString();
                }
            }

            for (int j = i - 1; j >= 0; j--) {
                int prev = target.charAt(j) - 'a';

                freq[prev]++;

                for (int c = prev + 1; c < 26; c++) {
                    if (freq[c] > 0) {
                        StringBuilder ans =
                            new StringBuilder(target.substring(0, j));

                        ans.append((char) ('a' + c));
                        freq[c]--;

                        for (int x = 0; x < 26; x++) {
                            while (freq[x] > 0) {
                                ans.append((char) ('a' + x));
                                freq[x]--;
                            }
                        }

                        return ans.toString();
                    }
                }
            }

            return "";
        }

        for (int i = n - 1; i >= 0; i--) {
            int idx = target.charAt(i) - 'a';
            freq[idx]++;

            for (int c = idx + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    StringBuilder ans =
                        new StringBuilder(target.substring(0, i));

                    ans.append((char) ('a' + c));
                    freq[c]--;

                    for (int x = 0; x < 26; x++) {
                        while (freq[x] > 0) {
                            ans.append((char) ('a' + x));
                            freq[x]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}