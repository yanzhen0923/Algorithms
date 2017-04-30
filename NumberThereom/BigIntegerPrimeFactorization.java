import java.util.*;

public class Test {

    /*
    The following code is referenced from
    http://www.abaiweb.com/uploads/5/768/41768/41768.shtml
    */
    private static long getFactorialExponent(long x, long p) {
        long ans = 0;
        long stepSize = p;
        while(x >= stepSize) {
            ans += (x/stepSize);
            stepSize *= p;
        }
        return ans;
    }

    private static long getModuloResult(long n , long k, long mod) {
        long ans = 1;
        while(k > 0) {
            if((k & 1) == 1) {
                ans = (ans * n) % mod;
            }
            n = (n * n) % mod;
            k >>= 1;
        }
        return ans;
    }

    private static class Goldbach {
        private int totalNum = 664579;
        private int maxBound = 10000000;
        boolean[] isPrime;
        int[] primeSet;
        private void build() {

            isPrime = new boolean[maxBound];
            primeSet = new int[totalNum];
            Arrays.fill(isPrime, true);

            int cmp = (int) Math.sqrt(maxBound);
            for (int i = 2; i < cmp; ++i) {
                if (isPrime[i]) {
                    for (int j = i * i; j < maxBound; j += i) {
                        isPrime[j] = false;
                    }
                }
            }

            int n = -1;
            for (int i = 2; i < maxBound; i++)
                if (isPrime[i]) primeSet[++n] = i;
        }
    }

    public static void main(String[] args){
        Goldbach gb = new Goldbach();
        gb.build();
        final long mod = 223092870;
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            long n = sc.nextLong(); long m = sc.nextLong();
            long ans = 1; long exponent;
            for(int i=0; i < gb.primeSet.length && gb.primeSet[i] <= n; ++ i)
            {
                exponent = getFactorialExponent(n, gb.primeSet[i]) - getFactorialExponent(m, gb.primeSet[i]) - getFactorialExponent(n-m, gb.primeSet[i]);
                ans = (ans * getModuloResult(gb.primeSet[i], exponent, mod)) % mod;
            }
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

