class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        // Count characters in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check whether palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;

        // Frequency of characters available for the left half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        char[] half = new char[halfLen];

        /*
         * Try to make the left half equal to target's left half.
         */
        int matched = 0;

        while (matched < halfLen) {
            int c = target.charAt(matched) - 'a';

            if (halfFreq[c] == 0) {
                break;
            }

            half[matched] = target.charAt(matched);
            halfFreq[c]--;
            matched++;
        }

        /*
         * Case 1:
         * We could not match target at position 'matched'.
         *
         * If we can put a character greater than target[matched],
         * that immediately gives the smallest answer.
         */
        if (matched < halfLen) {

            int targetChar = target.charAt(matched) - 'a';

            int bigger = -1;

            for (int c = targetChar + 1; c < 26; c++) {
                if (halfFreq[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger != -1) {
                half[matched] = (char) ('a' + bigger);
                halfFreq[bigger]--;

                fillSmallest(half, matched + 1, halfFreq);

                return makePalindrome(half, middle);
            }

            /*
             * No bigger character available at this position.
             * We need to backtrack.
             */
        }

        /*
         * Case 2:
         * Target's entire left half was matched.
         *
         * First construct the palindrome.
         * It might already be > target because of the middle/right half.
         */
        if (matched == halfLen) {

            String candidate = makePalindrome(half, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Backtracking:
         *
         * We have matched:
         *
         * target[0 ... matched-1]
         *
         * Try to increase the rightmost possible position.
         */
        for (int i = matched - 1; i >= 0; i--) {

            // Put the current character back into frequency
            int current = half[i] - 'a';
            halfFreq[current]++;

            // Find the smallest character greater than current
            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {
                if (halfFreq[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger != -1) {

                half[i] = (char) ('a' + bigger);
                halfFreq[bigger]--;

                // Fill remaining positions with smallest chars
                fillSmallest(half, i + 1, halfFreq);

                return makePalindrome(half, middle);
            }
        }

        return "";
    }

    private void fillSmallest(char[] half, int start, int[] freq) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                half[pos++] = (char) ('a' + c);
                freq[c]--;
            }
        }
    }

    private String makePalindrome(char[] half, char middle) {
        StringBuilder ans = new StringBuilder();

        // Left half
        for (char ch : half) {
            ans.append(ch);
        }

        // Middle character for odd length
        if (middle != 0) {
            ans.append(middle);
        }

        // Right half
        for (int i = half.length - 1; i >= 0; i--) {
            ans.append(half[i]);
        }

        return ans.toString();
    }
}