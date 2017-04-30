import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {

   static class MyPair{
       int index; int value;
       public MyPair(int index, int value){
           this.index = index; this.value = value;
       }
   }

    static class MyComparator implements Comparator<MyPair>{
        @Overrid
        public int compare(MyPair a, MyPair b){
            return b.value - a.value;
        }
    }

    static int getRoot(Integer[] parent, int id){
        if(parent[id] == id)
            return id;
        else{
            int res = getRoot(parent, parent[id]);
            parent[id] = res; return res;
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 0; x < testCaseCount; ++ x){
            int notablesCount = sc.nextInt();
            int relationCount = sc.nextInt();
            int marriageCount = sc.nextInt();
            int totalRelationCount = relationCount + marriageCount;

            //first phase
            MyPair[] money = new MyPair[notablesCount];
            for(int i = 1; i < money.length; ++ i){
                MyPair mp = new MyPair(i, sc.nextInt());
                money[i] = mp;
            }
            MyPair mp = new MyPair(0, -1); money[0] = mp;
            Arrays.sort(money, new MyComparator());

            //second phase
            Integer[] parent = new Integer[notablesCount + 1];
            Integer[] size = new Integer[notablesCount + 1];
            boolean[] marriage = new boolean[notablesCount + 1];
            for(int i = 0; i < parent.length; ++ i){
                parent[i] = i; size[i] = 1;
            }
            for(int i = 0; i < totalRelationCount; ++ i){
                int left = sc.nextInt(); int right = sc.nextInt();

                int leftRoot = getRoot(parent, left);
                int rightRoot = getRoot(parent, right);

                if(size[leftRoot] > size[rightRoot]){
                    parent[rightRoot] = leftRoot;
                    size[leftRoot] += size[rightRoot];
                }
                else{
                    parent[leftRoot] = rightRoot;
                    size[rightRoot] += size[leftRoot];
                }

                if((totalRelationCount - i) <= marriageCount){
                    marriage[left] = true;
                    marriage[right] = true;
                }
            }

            boolean isPossible = false;
            for(int i = 0; i < money.length - 1; ++ i){
                if(!(getRoot(parent, parent[money[i].index]) == getRoot(parent, notablesCount) ||
                        marriage[money[i].index])){
                    System.out.println("Case #" + (x + 1) + ": " + money[i].value);
                    isPossible = true;
                    break;
                }
            }
            if(!isPossible) {
                System.out.println("Case #" + (x + 1) + ": impossible");
            }
        }
    }
}

