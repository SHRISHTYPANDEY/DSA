class Solution {
    public int splitArray(int[] nums, int k) {
        int low = 0;
        int high = 0;
        for(int num:nums){
            low = Math.max(low,num);
            high += num;
        }
        int ans = high;
        while(low<=high){
            int mid = low+(high-low)/2;
            if(canSplit(nums, k, mid)){
                ans = mid;
                high = mid-1;
            } else{
                low = mid+1;
            }
        }
        return ans;
    }
    private boolean canSplit(int[] nums, int k, int maxSum){
        int count =1;
        int sum = 0;
        for(int num:nums){
            if(sum+num<=maxSum){
                sum+=num;
            }else{
                count++;
                sum = num;
                if(count>k) return false;
            }
        }
        return true;
    }
    

}