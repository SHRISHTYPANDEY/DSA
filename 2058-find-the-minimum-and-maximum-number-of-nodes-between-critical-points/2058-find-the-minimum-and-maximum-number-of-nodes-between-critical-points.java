/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int prev = -1;
        int minDist = Integer.MAX_VALUE;
        int maxDist = -1;

        ListNode prevNode = head;
        ListNode curr = head.next;
        int pos = 1;

        while (curr.next != null) {
            if ((curr.val > prevNode.val && curr.val > curr.next.val) ||
                (curr.val < prevNode.val && curr.val < curr.next.val)) {

                if (first == -1) {
                    first = pos;
                }

                if (prev != -1) {
                    minDist = Math.min(minDist, pos - prev);
                    maxDist = Math.max(maxDist, pos - first);
                }

                prev = pos;
            }

            prevNode = curr;
            curr = curr.next;
            pos++;
        }

        if (maxDist == -1) {
            return new int[]{-1, -1};
        }

        return new int[]{minDist, maxDist};
    }
}