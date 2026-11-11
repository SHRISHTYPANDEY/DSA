
class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        
        long both = Math.min(need1, need2);
        
        // Strategy 1: Buy both types separately where needed
        long costA = both * Math.min(costBoth, cost1 + cost2)
                   + (long)(need1 - both) * cost1
                   + (long)(need2 - both) * cost2;
        
        // Strategy 2: Buy only type 3 items
        long costB = (long)Math.max(need1, need2) * costBoth;
        
        return Math.min(costA, costB);
    }
}
