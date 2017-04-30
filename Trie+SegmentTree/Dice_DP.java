import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int points = sc.nextInt();
            String dc = sc.next();
            String[] dcI = dc.split(Pattern.quote("+"));
            List<Integer> li = new ArrayList<>();
            li.add(0);
            for(String s : dcI){
                int index = s.indexOf('d');
                int num = Integer.valueOf(s.substring(0, index));
                int maxV = Integer.valueOf(s.substring(index + 1));
                for(int cnt = 0; cnt < num; ++ cnt){
                    li.add(maxV);
                }
            }
            Integer[] sz = new Integer[li.size()];
            li.toArray(sz);
            int dices = sz.length - 1;

            BigInteger[][] dp = new BigInteger[dices + 1][points + 1];
            for(BigInteger[] bi : dp) {
                Arrays.fill(bi, BigInteger.ZERO);
            }
            dp[0][0] = BigInteger.ONE;
            for(int i = 1; i <= dices; ++ i){
                dp[i][0] = dp[i - 1][0].multiply(BigInteger.valueOf(sz[i]));
            }
            for(int i = 1; i <= dices; ++ i){
                for(int j = 1; j <= points; ++ j){
                    long tgt = Math.min(j, sz[i]);
                    for(int k = 1; k <= tgt; ++ k){
                        dp[i][j] = dp[i][j].add(dp[i - 1][j - k]);
                    }
                    for(long k = tgt + 1; k <= sz[i]; ++ k){
                        dp[i][j] = dp[i][j].add(dp[i - 1][0]);
                    }
                }
            }
            BigInteger gcd = dp[dices][0].gcd(dp[dices][points]);
            System.out.println("Case #" + x + ": " + dp[dices][points].divide(gcd) + "/" + dp[dices][0].divide(gcd));
        }
    }
}

