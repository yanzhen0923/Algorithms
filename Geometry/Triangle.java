import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Test {


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            for(int i = 0; i < 4; ++ i) {
                sc.nextDouble();
            }

            double xWall1 = sc.nextDouble();
            double yWall1 = sc.nextDouble();
            double xWall2 = sc.nextDouble();
            double yWall2 = sc.nextDouble();
            double a = sc.nextDouble();
            double b = sc.nextDouble();

            double A = yWall1 - yWall2;
            double B = xWall2 - xWall1;
            double C = xWall1 * yWall2 - xWall2 *yWall1;


            //（a-（2A*（Aa+Bb+C））/(A*A+B*B),b-（2B*(Aa+Bb+C））/(A*A+B*B))

            double left = a - ((2 * A * (A * a + B * b + C)) / (A * A + B * B));
            double right = b - ((2 * B * (A * a + B * b + C)) / (A * A + B * B));

            System.out.println("Case #" + x + ": " + left + " " + right);
        }
    }
}

