import java.util.Scanner;

/**
 * Created by yanz on 4/13/16.
 */
public class Test {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        for(int i = 0; i < count; ++ i){
            System.out.println("Case #" + (i + 1) + ": Hello " + sc.next() + "!\n");
        }
    }
}

