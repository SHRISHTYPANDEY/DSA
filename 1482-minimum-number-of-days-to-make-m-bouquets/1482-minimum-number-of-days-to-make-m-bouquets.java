class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
        if((long)m*k>bloomDay.length){
            return -1;
        }
        int low = Integer.MAX_VALUE;
        int high = Integer.MIN_VALUE;
        for(int day : bloomDay){
            low = Math.min(low, day);
            high = Math.max(high, day);
        }
        int ans = -1;
        while(low <= high){
            int mid = low+(high-low)/2;
            if(canMake(bloomDay, m, k, mid)){
                ans = mid;
                high = mid-1;
            }else{
                low = mid+1;
            }
        }
        return ans;
    }
    private boolean canMake(int[] bloomDay, int m, int k, int day){
        int bouquet = 0;
        int flower = 0;
        for(int bloom:bloomDay){
            if(bloom<=day){
                flower++;
                if(flower == k){
                    bouquet++;
                    flower = 0;
                }
            }else{
                flower=0;
            }
        }
        return bouquet>=m;
    }
}