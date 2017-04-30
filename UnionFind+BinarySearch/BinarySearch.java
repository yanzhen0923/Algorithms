import java.util.*;
/**
 * Created by yz on 16-4-13.
 */
public class Test {

    private static class Station{
        int begin;
        int end;
        Station(int begin, int end){
            this.begin = begin; this.end = end;
        }
    }

    private static class StationComparator implements Comparator<Station>{
        @Override
        public int compare(Station s1, Station s2){
            return s1.begin - s2.begin;
        }
    }

    private static int findRoomNumber(Station[] st, int lineNumber, int minNum, int maxNum){
        long low = minNum; long high = maxNum; long testLineNumber; long range;
        long target = lineNumber;
        while (low != high){
            long mid = ((low + high) >> 1);
            testLineNumber = 0; range = 0;
            for(int i = 0; i < st.length; ++ i){
                if(st[i].end < mid){
                    testLineNumber += (st[i].end - st[i].begin + 1);
                }
                else if(st[i].begin <= mid){
                    testLineNumber += (mid - st[i].begin);
                    ++ range;
                }else {
                    break;
                }
            }

            if(testLineNumber < target && target <= (testLineNumber + range)) {
                return (int)mid;
            }
            else if(lineNumber <= testLineNumber){
                high = (high == mid) ? (mid - 1) : mid;
            }
            else {
                low = (low == mid) ? (mid + 1) : mid;
            }
        }
        return (int)low;
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){

            int stationCount = sc.nextInt();
            int friendsCount = sc.nextInt();
            int minNum = Integer.MAX_VALUE; int maxNum = Integer.MIN_VALUE;
            Station[] st  = new Station[stationCount];
            int[] fr = new int[friendsCount];

            for(int i = 0; i < stationCount; ++ i){
                int u = sc.nextInt(); int v = sc.nextInt();
                minNum = Math.min(minNum, u); maxNum = Math.max(maxNum, v);
                st[i] = new Station(u, v);
            }
            for(int i = 0; i < friendsCount; ++ i){
                fr[i] = sc.nextInt();
            }

            Arrays.sort(st, new StationComparator());

            System.out.println("Case #" + x + ":");
            for(int i = 0; i < friendsCount; ++ i){
                System.out.println(findRoomNumber(st, fr[i], minNum, maxNum));
            }
        }
    }
}

