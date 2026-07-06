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
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;
        while(curr!=null && count<k){
            curr = curr.next;
            count++;
        }
        if(count<k) return head;

        ListNode prev = null;
        curr = head;
        count =0;
        while(curr!=null && count<k){
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
            count++;
        }
        head.next = reverseKGroup(curr,k);
        return prev;
    }
}