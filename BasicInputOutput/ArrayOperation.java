import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {

    public static final int TEAM_SIZE = 5;

    static class SchoolComparator implements Comparator<Integer[]>{
        @Override
        public int compare(Integer[] arr1, Integer[] arr2) {
            for(int i = 0; i < TEAM_SIZE; ++ i){
                int res = arr2[i] - arr1[i];
                if(res != 0)
                    return res;
            }
            return  0;
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 0; x < testCaseCount; ++ x){
            int schoolCount = sc.nextInt();
            ArrayList<Integer[]> arrList = new ArrayList<>();
            for(int i = 0; i < schoolCount; ++ i){
                Integer[] participants = new Integer[TEAM_SIZE];
                for(int j = 0; j < TEAM_SIZE; ++ j){
                    participants[j] = sc.nextInt();
                }
                Arrays.sort(participants, Collections.reverseOrder());
                arrList.add(participants);
            }
            Collections.sort(arrList, new SchoolComparator());
            //***output
            System.out.println("Case #" + (x + 1) + ":");
            for(Integer[] par : arrList){
                int m;
                for(m = 0; m < TEAM_SIZE; ++ m){
                    System.out.print(par[m] + " ");
                }
                System.out.println();
            }
            //*****

        }
    }
}

