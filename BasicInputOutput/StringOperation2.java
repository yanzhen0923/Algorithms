import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test 

    private static final String plus = "plus";
    private static final String minus = "minus";
    private static final String times = "times";
    private static final String toThePowerOf = "tothepowerof";

    private static int GetMyResult(int a, int b, String operation){
        if (operation.equals(plus)){
            return a + b;
        }
        if(operation.equals(minus)){
            return a - b;
        }
        if(operation.equals(times)){
            return a * b;
        }
        if(operation.equals(toThePowerOf)){
            return  (int)Math.pow(a, b);
        }
        return Integer.MAX_VALUE;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int lines = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < lines; ++ i){
            String singleLine = sc.nextLine();
            int len = singleLine.length();
            int beginPointer = 0; int endPointer = 0;
            int res; int intermediateNumber; String op;

            //init phase, get the first number as the first result
            for(;endPointer < len;++endPointer){
                if(!Character.isDigit(singleLine.charAt(endPointer)))
                    break;
            }
            res = Integer.parseInt(singleLine.substring(beginPointer, endPointer));
            beginPointer = endPointer;

            //loop for an operator and a number in each step
            while (endPointer < len){
                //seek the next operator
                for (;endPointer < len;++ endPointer){
                    if(Character.isDigit(singleLine.charAt(endPointer)))
                        break;
                }
                op = singleLine.substring(beginPointer, endPointer);
                beginPointer = endPointer;
                //seek the number
                for(;endPointer < len;++ endPointer){
                    if(!Character.isDigit(singleLine.charAt(endPointer)))
                        break;
                }
                intermediateNumber = Integer.parseInt(singleLine.substring(beginPointer, endPointer));
                beginPointer = endPointer;
                res = GetMyResult(res, intermediateNumber, op);
            }

            System.out.println("Case #" + (i + 1) + ": " + res);

        }
    }
}

