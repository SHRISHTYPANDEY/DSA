class Solution {
    public int[] rowAndMaximumOnes(int[][] mat) {
        int totalRow = mat.length;
        int totalCol = mat[0].length;
        int max = 0;
        int maxOnesRowIndex = 0;
        for(int i = 0; i < totalRow; i++) {
            int oneCount = 0;
            for(int j = 0; j < totalCol; j++) {
                oneCount += mat[i][j];
            }
            if(oneCount > max) {
                max = oneCount;
                maxOnesRowIndex = i;
            }
        }
        return new int[]{maxOnesRowIndex, max};
    }
}