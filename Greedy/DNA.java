import java.util.*;

public class Algorithm{

    private static int getId(char left, char right){
        if((left == 'A' && right == 'C') || (left == 'C' && right == 'A')){
            return 0;
        }
        if((left == 'A' && right == 'G') || (left == 'G' && right == 'A')){
            return 1;
        }
        if((left == 'A' && right == 'T') || (left == 'T' && right == 'A')){
            return 2;
        }
        if((left == 'C' && right == 'G') || (left == 'G' && right == 'C')){
            return 3;
        }
        if((left == 'C' && right == 'T') || (left == 'T' && right == 'C')){
            return 4;
        }
        if((left == 'G' && right == 'T') || (left == 'T' && right == 'G')){
            return 5;
        }
        if(left == 'A' && right == 'A'){
            return 6;
        }
        if(left == 'C' && right == 'C'){
            return 7;
        }
        if(left == 'G' && right == 'G'){
            return 8;
        }
        if(left == 'T' && right == 'T'){
            return 9;
        }
        return 0;
    }

    private static class Pair implements Comparable<Pair>{
        int amt;
        int type;
        int val;

        Pair(){
            amt = 0;
            type = 0;
            val = 10;
        }

        @Override
        public int compareTo(Pair o){
            int left = this.type == 1 ? this.amt : this.amt * 2;
            int right = o.type == 1 ? o.amt : o.amt * 2;
            return left - right;
        }
    }

    private static int verify(Pair[] cnt){
        int res = 0;
        for(int i = 0; i < cnt.length; ++i){
            if(cnt[i].type == 1){
                res += (2 * cnt[i].val);
            }else if(cnt[i].val >= 0){
                res += cnt[i].val;
            }
        }
        return res;
    }

    public static void main(String[] args){

        int sz = 10;
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x){
            Pair[] count = new Pair[sz];
            for(int i = 0; i < sz; ++ i){
                count[i] = new Pair();
                if(i < 6){
                    count[i].type = 1;
                }
                else {
                    count[i].type = 2;
                }
            }
            int n = sc.nextInt(); int m = sc.nextInt();
            String[] mString = new String[m];
            String[] nString = new String[n];

            for(int i = 0; i < n; ++ i){
                nString[i] = sc.next();
            }
            for(int i = 0; i < m; ++ i){
                mString[i] = sc.next();
            }
            int length = nString[0].length();
            for(int i = 0; i < n; ++ i){
                for(int j = 0; j < m; ++ j){
                    for(int k = 0; k < length; ++ k){
                        char left = nString[i].charAt(k); char right = mString[j].charAt(k);
                        int index = getId(left, right);
                        ++ count[index].amt;
                    }
                }
            }
            Arrays.sort(count);


            for(int i = 0; i < sz; ++ i){
                count[i].val = count[i].type == 1 ? -10 : 0;
                int cur = verify(count);
                boolean mark = false;
                if(cur == 0){
                    break;
                }
                else if (cur < 0){
                    for(int z =  10; z >= (count[i].type == 1 ? -10 : 0); -- z){
                        count[i].val = z;
                        if(verify(count) == 0){
                            mark = true;
                            break;
                        }
                    }
                    if(mark)
                        break;
                }
            }
            int r = 0;
            for(Pair p : count){
                r += p.val * p.amt;
            }
            System.out.println("Case #" + x + ": " + r);
        }
    }
}

