import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int lines = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < lines; ++ i){
            String singleLine = sc.nextLine();

            int pos = singleLine.indexOf('#');
            int splitPosition = Integer.valueOf(singleLine.substring(0, pos));
            String scrambledText = singleLine.substring(pos + 1, singleLine.length());
            StringBuilder sb = new StringBuilder(scrambledText.substring(splitPosition, scrambledText.length()) + scrambledText.substring(0, splitPosition));
            System.out.println("Case #" + (i + 1) + ": " + sb.toString());


        }
    }
}

