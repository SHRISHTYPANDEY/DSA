import java.util.*;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {
        
        HashMap<String, String> map = new HashMap<>();
        
        // Store key -> value
        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }
        
        StringBuilder ans = new StringBuilder();
        
        int i = 0;
        
        while (i < s.length()) {
            
            if (s.charAt(i) == '(') {
                i++; // skip '('
                
                StringBuilder key = new StringBuilder();
                
                // Collect characters until ')'
                while (s.charAt(i) != ')') {
                    key.append(s.charAt(i));
                    i++;
                }
                
                // Get value from map
                if (map.containsKey(key.toString())) {
                    ans.append(map.get(key.toString()));
                } else {
                    ans.append('?');
                }
                
                i++; // skip ')'
                
            } else {
                ans.append(s.charAt(i));
                i++;
            }
        }
        
        return ans.toString();
    }
}