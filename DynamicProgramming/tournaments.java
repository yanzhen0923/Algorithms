import java.util.*;

public class Test {

    private static class Match{
        int begin; int end; int profit;
        Match(int b, int e, int p){
            begin = b; end = e; profit = p;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int tournaments = sc.nextInt();
            Match[] matches = new Match[tournaments];
            int MAX_DAYS = -1;
            for(int i = 0; i < tournaments; ++ i){
                matches[i] = new Match(sc.nextInt(), sc.nextInt(), sc.nextInt());
                if(matches[i].end > MAX_DAYS){
                    MAX_DAYS = matches[i].end;
                }
            }
            int[] ans = new int[MAX_DAYS + 1];
            for(int i = 1; i <= MAX_DAYS; ++ i){
                ans[i] = ans[i - 1];
                for(int j = 0; j < matches.length; ++ j){
                    if(matches[j].end == i) {
                        ans[i] = Math.max(ans[i], ans[matches[j].begin - 1] + matches[j].profit);
                    }
                }
            }

            System.out.println("Case #" + x + ": " + ans[MAX_DAYS]);
        }
    }
}

