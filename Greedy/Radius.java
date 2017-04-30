import java.util.*;

public class Test {

    private static int getNextIndex(int[] list, int expandingPoint, int index, int radius){
        if(list.length == 0){
            return -1;
        }
        int i;
        for(i = index + 1; i < list.length; ++ i){
            if(list[i] - radius >= expandingPoint){
                break;
            }
        }
        if(!(i < list.length)) i = list.length - 1;

        if(list[i] - radius <= expandingPoint){
            return i;
        }
        if(i - 1 >=0 && list[i - 1] - radius <= expandingPoint){
            return i - 1;
        }
        return -1;
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {
            int totalLength = sc.nextInt();
            int posCount = sc.nextInt();
            int radius = sc.nextInt();
            int[] list = new int[posCount];
            for(int i = 0; i < posCount; ++ i) {
                list[i] = sc.nextInt();
            }
            Arrays.sort(list);

            int expandPoint = 0;
            int resCount = 0;
            int expandIndex = -1;

            while (expandPoint < totalLength){
                int newPointer = getNextIndex(list, expandPoint, expandIndex, radius);
                if(newPointer == -1 || newPointer == expandIndex){
                    resCount = -1;
                    break;
                }else {
                    ++ resCount;
                    expandIndex = newPointer;
                    expandPoint = list[expandIndex] + radius;
                }
            }
            String ans = resCount == -1 ? "impossible" : String.valueOf(resCount);

            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

