import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int Q = queries.length;
        int[] ans = new int[Q];

        int totalOnes = 0;
        for (int i = 0; i < n; i++) if (s.charAt(i) == '1') totalOnes++;

        List<int[]> runsList = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int j = i;
            char c = s.charAt(i);
            while (j < n && s.charAt(j) == c) j++;
            runsList.add(new int[]{i, j - 1, c == '1' ? 1 : 0});
            i = j;
        }
        int R = runsList.size();
        int[] rStart = new int[R], rEnd = new int[R], rType = new int[R];
        for (int k = 0; k < R; k++) {
            rStart[k] = runsList.get(k)[0];
            rEnd[k]   = runsList.get(k)[1];
            rType[k]  = runsList.get(k)[2];
        }

        List<int[]> interior = new ArrayList<>();
        int[] candidateByL = new int[n];
        int[] candidateByR = new int[n];
        Arrays.fill(candidateByL, -1);
        Arrays.fill(candidateByR, -1);

        for (int k = 1; k < R - 1; k++) {
            if (rType[k] == 1) {
                int zs = rStart[k - 1];
                int rs = rStart[k];
                int re = rEnd[k];
                int ze2 = rEnd[k + 1];
                int A = rs - zs;
                int B = ze2 - re;
                int idx = interior.size();
                interior.add(new int[]{zs, rs, re, ze2, A, B, A + B});

                for (int p = zs + 1; p <= rs - 1; p++) candidateByL[p] = idx;
                for (int p = re + 1; p <= ze2 - 1; p++) candidateByR[p] = idx;
            }
        }

        int M = interior.size();

        Integer[] qOrder = new Integer[Q];
        for (int idx = 0; idx < Q; idx++) qOrder[idx] = idx;
        Arrays.sort(qOrder, (a, b) -> queries[b][0] - queries[a][0]);

        Integer[] runOrder = new Integer[M];
        for (int idx = 0; idx < M; idx++) runOrder[idx] = idx;
        Arrays.sort(runOrder, (a, b) -> interior.get(b)[0] - interior.get(a)[0]);

        int[] fenwick = new int[n + 2];
        int[] caseAResult = new int[Q];

        int ptr = 0;
        for (int qi : qOrder) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            while (ptr < M) {
                int[] run = interior.get(runOrder[ptr]);
                if (run[0] >= l) {
                    int pos = run[3] + 1; 
                    int val = run[6];     
                    while (pos <= n) {
                        if (fenwick[pos] < val) fenwick[pos] = val;
                        pos += pos & (-pos);
                    }
                    ptr++;
                } else break;
            }
            int pos = r + 1;
            int res = 0;
            while (pos > 0) {
                if (fenwick[pos] > res) res = fenwick[pos];
                pos -= pos & (-pos);
            }
            caseAResult[qi] = res;
        }

        for (int qi = 0; qi < Q; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            int gain = caseAResult[qi];

            int m1 = candidateByR[r];
            if (m1 != -1) {
                int[] run = interior.get(m1);
                int rs = run[1], re = run[2], A = run[4], B = run[5];
                if (rs > l) {
                    int g = Math.min(A, rs - l) + Math.min(B, r - re);
                    if (g > gain) gain = g;
                }
            }

            int m2 = candidateByL[l];
            if (m2 != -1) {
                int[] run = interior.get(m2);
                int rs = run[1], re = run[2], A = run[4], B = run[5];
                if (re < r) {
                    int g = Math.min(A, rs - l) + Math.min(B, r - re);
                    if (g > gain) gain = g;
                }
            }

            ans[qi] = totalOnes + gain;
        }

        List<Integer> result = new ArrayList<>(Q);
        for (int v : ans) result.add(v);
        return result;
    }
}