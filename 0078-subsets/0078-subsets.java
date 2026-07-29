class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        solve(0,nums,new ArrayList<>(),ans);
        return ans;
    }
    void solve (int idx, int[] nums,List<Integer>curr,List<List<Integer>>ans){
        if(idx == nums.length){
            ans.add(new ArrayList<>(curr));
            return;
        }
        curr.add(nums[idx]);
        solve(idx+1,nums,curr,ans);
        curr.remove(curr.size()-1);
        solve(idx+1,nums,curr,ans);
    }
}