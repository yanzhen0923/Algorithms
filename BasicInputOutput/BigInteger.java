import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by yz on 16-4-13.
 */
public class Test {
    public static final BigInteger c2 = new BigInteger("89875517873681764");
    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        for(int i = 0; i < count; ++ i){
            BigInteger E = sc.nextBigInteger();
            System.out.println("Case #" + (i + 1) + ": " + E.multiply(c2) + "\n");
        }
    }
}

