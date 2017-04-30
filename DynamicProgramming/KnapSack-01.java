import java.util.*;

public class Test {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int types = sc.nextInt();
            int sum = sc.nextInt();
            int[] v = new int[types + 1];
            Map<Integer, Integer> viIndex = new HashMap<>();
            for(int i = 1; i <= types; ++ i){
                int va = sc.nextInt();
                v[i] = va;
                viIndex.put(va, i);
            }

            int[] min = new int[sum + 1];
            int[] pre = new int[sum + 1];
            Arrays.fill(min, 0x3f3f3f);
            Arrays.fill(pre, -1);
            min[0] = 0;
            for(int i = 1; i <= sum; ++ i){
                for(int j = 1; j <= types; ++ j){
                    if(v[j] <= i && min[i - v[j]] + 1 < min[i]){
                        min[i] = min[i - v[j]] + 1;
                        pre[i] = i - v[j];
                    }
                }
            }
            int[] output = new int[types + 1];
            int roll = sum;
            while (pre[roll] != 0){
                ++ output[viIndex.get(roll - pre[roll])];
                roll = pre[roll];
            }
            ++ output[viIndex.get(roll)];

            System.out.println("Case #" + x + ": ");
            for(int i = 1; i < output.length; ++ i){
                System.out.print(output[i] + " ");
            }
            System.out.println();
        }
    }
}

