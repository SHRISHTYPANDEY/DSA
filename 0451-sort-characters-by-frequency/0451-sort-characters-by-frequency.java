class Solution {
    public String frequencySort(String s) {
        StringBuilder sb = new StringBuilder();
        List<Character>[] arr = new ArrayList[s.length() + 1];
        Map<Character, Integer> map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c,map.getOrDefault(c,0)+1);
        }
          map.keySet().forEach(c -> {
            int freq = map.get(c);
            if (arr[freq] == null) {
                arr[freq] = new ArrayList<>();
            }
            arr[freq].add(c);
        });
        for(int i=arr.length-1;i>0;i--){
            if (arr[i] != null) {
                for (char c : arr[i]) {
                    for (int j = 0; j < i; j++) {
                        sb.append(c);
                    }
                }
            }
        }
            return sb.toString();
    }


}