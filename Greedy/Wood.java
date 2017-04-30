import java.util.*;

public class Test {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {
            int woods = sc.nextInt();
            int saws = sc.nextInt();

            int[] woodList = new int[woods];
            for (int i = 0; i < woods; ++i) {
                woodList[i] = sc.nextInt();
            }
            Arrays.sort(woodList);

            long[] sawList = new long[saws];
            int lastIndex = woods - 1;

            while (true) {
                Arrays.sort(sawList);
                for (int i = 0; lastIndex >= 0 && i < saws; --lastIndex, ++ i) {
                    sawList[i] += woodList[lastIndex];
                }
                if(lastIndex < 0) {
                    break;
                }
            }

            long res = -1;
            for(int i = 0; i < saws; ++ i){
                if(sawList[i] > res){
                    res = sawList[i];
                }
            }

            System.out.println("Case #" + x + ": " + res);
        }
    }
}

