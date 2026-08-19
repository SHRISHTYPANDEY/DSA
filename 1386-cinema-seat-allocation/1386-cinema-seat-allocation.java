import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rows = new HashMap<>();

        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int s = seat[1];

            rows.put(row, rows.getOrDefault(row, 0) | (1 << (s - 1)));
        }

        int ans = (n - rows.size()) * 2;

        int left = (1 << 1) | (1 << 2) | (1 << 3) | (1 << 4);   // 2-5
        int middle = (1 << 3) | (1 << 4) | (1 << 5) | (1 << 6); // 4-7
        int right = (1 << 5) | (1 << 6) | (1 << 7) | (1 << 8);  // 6-9

        for (int mask : rows.values()) {

            boolean canLeft = (mask & left) == 0;
            boolean canMiddle = (mask & middle) == 0;
            boolean canRight = (mask & right) == 0;

            if (canLeft && canRight) {
                ans += 2;
            } else if (canLeft || canMiddle || canRight) {
                ans += 1;
            }
        }

        return ans;
    }
}