import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {
    public static void main(String args[]){
        double precision = 0.000000001;
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){

            int d = sc.nextInt(); int p = sc.nextInt();
            int u = sc.nextInt(); int v = sc.nextInt();

            int lWidth = u;
            int rWidth = d - v;
            int bWidth = v - u;

            double low = 0; double high = d;

            while (high - low > precision){
                double mid = (high + low) / 2;
                int lPost = (int)(lWidth / mid);
                int rPost = (int)(rWidth / mid);
                double lRemaining = lWidth - lPost * mid;
                double rRemaining = rWidth - rPost * mid;
                double gap = lRemaining + rRemaining + bWidth;
                int availablePosts = (int)(lWidth / mid) + (int)(rWidth / mid) + 2;

                if((availablePosts - 1) >= p){
                    low = mid;
                }
                else if(availablePosts < p){
                    high = mid;
                }
                else if(gap < mid) {
                    high = mid;
                }
                else {
                    low = mid;
                }
            }
            System.out.print("Case #" + x + ": ");
            System.out.format("%.10f", (high + low) / 2);
            System.out.println();
        }
    }
}

