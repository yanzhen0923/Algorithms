import java.util.*;

public class Test {

    //The following code is referenced from http://introcs.cs.princeton.edu/java/96optimization/LCS.java.html
    private static int LCS(String a, String b){
        int[][] dp = new int[a.length()+1][b.length()+1];
        for (int i = a.length()-1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                if (a.charAt(i) == b.charAt(j))
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j + 1]);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            String a = sc.next(); String b = sc.next();
            int max = 0;
            for(int i = 0; i < b.length(); ++ i){
                max = Math.max(max, LCS(a,  b.substring(i) + b.substring(0, i)));
            }
            String newB = new StringBuilder(b).reverse().toString();
            for(int i = 0; i < newB.length(); ++ i){
                max = Math.max(max, LCS(a,  newB.substring(i) + newB.substring(0, i)));
            }
            System.out.println("Case #" + x + ": " + max);
        }
    }
}

