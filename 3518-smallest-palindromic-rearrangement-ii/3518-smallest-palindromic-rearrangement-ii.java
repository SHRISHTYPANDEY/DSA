class Solution {

    static final long LIMIT = 1000001;

    public String smallestPalindrome(String s, int k) {

        int[] freq = new int[26];

        for(char c: s.toCharArray())
            freq[c-'a']++;

        String mid = "";

        int[] half = new int[26];

        int total = 0;

        for(int i=0;i<26;i++){

            if((freq[i]&1)==1)
                mid = String.valueOf((char)(i+'a'));

            half[i]=freq[i]/2;

            total+=half[i];
        }

        if(countWays(half,total)<k)
            return "";

        StringBuilder left = new StringBuilder();

        while(total>0){

            for(int c=0;c<26;c++){

                if(half[c]==0)
                    continue;

                half[c]--;

                long ways=countWays(half,total-1);

                if(ways>=k){

                    left.append((char)(c+'a'));

                    total--;

                    break;
                }

                k-=ways;

                half[c]++;
            }
        }

        String right=new StringBuilder(left).reverse().toString();

        return left.toString()+mid+right;
    }

    long countWays(int[] freq,int total){

        long ans=1;

        int rem=total;

        for(int f:freq){

            if(f==0)
                continue;

            ans*=comb(rem,f);

            if(ans>LIMIT)
                ans=LIMIT;

            rem-=f;
        }

        return ans;
    }

    long comb(int n,int r){

        if(r>n)
            return 0;

        r=Math.min(r,n-r);

        long ans=1;

        for(int i=1;i<=r;i++){

            ans=ans*(n-r+i)/i;

            if(ans>LIMIT)
                return LIMIT;
        }

        return ans;
    }
}