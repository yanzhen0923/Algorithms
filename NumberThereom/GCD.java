import java.util.*;

public class Test {

    private static int GCD(int a, int b) {
        if (b==0) {
            return a;
        }
        return GCD(b,a%b);
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int cnt = sc.nextInt();
            int ans = sc.nextInt();
            for(int i = 1; i < cnt; ++ i){
                int num = sc.nextInt();
                ans = GCD(Math.max(num, ans), Math.min(num, ans));
            }
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

