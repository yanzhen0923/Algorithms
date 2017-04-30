import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {

    private static int findBestPosition(long[] sum, int begin, long target){
        int low = 1; int high = sum.length - 1;
        long midSum; int mid = 0;
        while (low <= high){
            mid = ((low + high) >> 1);
            midSum = sum[mid] - sum[begin - 1];
            if(midSum < target){
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        return mid;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int n,p,q,r,s;
            n = sc.nextInt(); p = sc.nextInt(); q = sc.nextInt();
            r = sc.nextInt(); s = sc.nextInt();
            long[] sum = new long[n + 1]; sum[0] = 0;
            for(int i = 1; i <= n; ++ i){
                sum[i] = sum[i - 1] +  ((((long)i * (long)p + (long)q) % (long)r) + (long)s);
            }
            long ans = Long.MIN_VALUE;
            for(int a = 1; a <= n; ++ a){
                long leftSum = sum[a - 1];
                long restSum = sum[n] - leftSum;
                long targetSum = restSum >> 1;
                int pos = findBestPosition(sum, a, targetSum);

                //Adjust pos to pos/pos + 1/pos - 1
                if((sum[pos] - sum[a - 1]) < targetSum){
                    long keepPos = Math.abs(sum[pos] - sum[a - 1] - targetSum);
                    long posAddOne = Math.abs(sum[pos + 1] - sum[a - 1] - targetSum);
                    if(posAddOne < keepPos){
                        ++ pos;
                    }
                }
                else if(sum[pos] - sum[a - 1] > targetSum){
                    long keepPos = Math.abs(sum[pos] - sum[a - 1] - targetSum);
                    long posMinusOne = Math.abs(sum[pos - 1] - sum[a - 1] - targetSum);
                    if(posMinusOne < keepPos){
                        -- pos;
                    }
                }


                long midSum = sum[pos] - sum[a - 1];
                long rightSum = sum[n] - sum[pos];
                long pickedCookies = Math.max(leftSum, Math.max(midSum, rightSum));
                ans = Math.max(ans, sum[n] - pickedCookies);
            }
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

