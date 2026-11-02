class Solution {
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int n=nums.length;
        int mediam = nums[n/2];
        long moves = 0;
        for(int num:nums){
            moves += Math.abs((long) num-mediam);
        }
        return (int) moves;
    }
}