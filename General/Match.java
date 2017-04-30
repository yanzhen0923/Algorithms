import java.util.*;

public class Test {

    private static int getSum(int[] arr, int begin, int end){
        int sum = 0;
        for(int i = begin; i <= end; ++ i){
            sum += arr[i];
        }
        return sum;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x){
            int d = sc.nextInt();
            int c = sc.nextInt();
            int m = sc.nextInt();
            int[] rallyes = new int[d * (c + 1)];
            int[] motos = new int[d * (c + 1)];
            for(int i = 0; i < rallyes.length; ++ i){
                rallyes[i] = sc.nextInt();
            }
            for(int i = 0; i < motos.length; ++ i){
                motos[i] = sc.nextInt();
            }
            int ans = 0;
            for(int i = 1; i <= d; ++ i){
                int begin = (i - 1) * (c + 1);
                int end = begin + c;

                // beigin with the rallye
                int minFromRallye = Integer.MAX_VALUE;
                for(int j = begin; j <= end - 1; ++ j){
                    int sum = getSum(rallyes, begin, j) + getSum(motos, j + 1, end) + m;
                    if(sum < minFromRallye){
                        minFromRallye = sum;
                    }
                }
                int noChangeFromRallye = getSum(rallyes, begin, end);
                if(noChangeFromRallye < minFromRallye){
                    minFromRallye = noChangeFromRallye;
                }


                int minFromMoto = Integer.MAX_VALUE;
                for(int j = begin; j <= end - 1; ++ j){
                    int sum = getSum(motos, begin, j) + getSum(rallyes, j + 1, end) + m;
                    if(sum < minFromMoto){
                        minFromMoto = sum;
                    }
                }
                int noChangeFromMoto = getSum(motos, begin, end);
                if(noChangeFromMoto < minFromMoto){
                    minFromMoto = noChangeFromMoto;
                }

                ans += minFromMoto < minFromRallye ? minFromMoto : minFromRallye;
            }

            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

