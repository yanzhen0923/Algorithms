import java.math.BigInteger;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            BigInteger d = new BigInteger("18");
            BigInteger b = sc.nextBigInteger();
            System.out.println("Case #" + x + ": " + (b.mod(d).equals(BigInteger.ZERO) ? "yes" : "no"));
        }
    }
}

