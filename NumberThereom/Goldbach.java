import java.util.*;

public class Test {

    private static class Goldbach {
        private int totalNum = 5761455;
        private int maxBound = 100000000;
        boolean[] isPrime;
        int[] primeSet;
        private void build() {

            isPrime = new boolean[maxBound];
            primeSet = new int[totalNum];
            Arrays.fill(isPrime, true);
            isPrime[0] = false;
            isPrime[1] = false;

            int cmp = (int)Math.sqrt(maxBound);
            for (int i = 2; i < cmp; ++ i) {
                if (isPrime[i]) {
                    for (int j = i * i; j < maxBound; j += i) {
                        isPrime[j] = false;
                    }
                }
            }

            int n = -1;
            for (int i = 2; i < maxBound; i++) {
                if (isPrime[i]) primeSet[++n] = i;
            }
        }

        int[] getFromEven(int num){
            for(int i = 0;i < primeSet.length;++ i){
                if(isPrime[num - primeSet[i]]){
                    return new int[]{primeSet[i], num - primeSet[i]};
                }
            }
            return null;
        }

        int[] getFromOdd(int num){
            for(int i = 0; i < primeSet.length; ++ i){
                int first = primeSet[i];
                for(int j = i; j < primeSet.length; ++ j){
                    if(first + primeSet[j] <= num) {
                        if (isPrime[num - first - primeSet[j]]){
                            return new int[]{first, primeSet[j], num - first - primeSet[j]};
                        }
                    }
                }
            }
            return null;
        }

    }




    public static void main(String[] args){
        Goldbach gb = new Goldbach();
        gb.build();
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int guess = sc.nextInt();
            int ans[] = (guess & 1) == 1 ? gb.getFromOdd(guess) : gb.getFromEven(guess);
            Arrays.sort(ans);

            System.out.print("Case #" + x + ": ");
            for(int i : ans){
                if(i > 0){
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }
    }
}

