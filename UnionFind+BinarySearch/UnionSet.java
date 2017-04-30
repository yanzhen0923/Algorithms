import java.util.*;

/**
 * Created by yz on 16-4-13.
 */
public class Test {

    private static class UnionSet{
        int elements[];
        int size[];
        UnionSet(int s){
            elements = new int[s];
            size = new int[s];
            for(int i = 0; i < s; ++ i){
                elements[i] = i; size[i] = 1;
            }
        }

        int getRoot(int id){
            if(elements[id] == id)
                return id;
            else {
                int res = getRoot(elements[id]);
                elements[id] = res;
                return res;
            }
        }

        void merge(int left, int right){
            int leftRoot = getRoot(left);
            int rightRoot = getRoot(right);
            if(leftRoot == elements[rightRoot]
                    || rightRoot == elements[leftRoot]){
                return;
            }
            if(size[leftRoot] < size[rightRoot]){
                elements[leftRoot] = rightRoot;
                size[rightRoot] += size[leftRoot];
            }
            else {
                elements[rightRoot] = leftRoot;
                size[leftRoot] += size[rightRoot];
            }
        }

    }

    private static class HatredList{
        Map<Integer, List<Integer>> mis;
        HatredList(){
            mis = new HashMap<>();
        }

        void putHatredPair(int left, int right, int round){
            List<Integer> leftList = mis.get(left);
            if(leftList == null){
                List<Integer> myList = new ArrayList<>();
                myList.add(right);
                mis.put(left, myList);
            }
            else if(!leftList.contains(right)) {
                leftList.add(right);
            }
            -- round;
            if(round >= 0) {
                putHatredPair(right, left, round);
            }
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int countriesCount = sc.nextInt(); int relations = sc.nextInt();
            UnionSet myUnionSet = new UnionSet(countriesCount + 1);
            HatredList myHatredList = new HatredList();

            for(int i = 0; i < relations; ++ i){
                char r = sc.next().charAt(0);
                if(r == 'F'){
                    myUnionSet.merge(sc.nextInt(), sc.nextInt());
                }
                else {
                    myHatredList.putHatredPair(sc.nextInt(), sc.nextInt(), 1);
                }
            }

            for (int i = 0; i < myUnionSet.elements.length; ++ i){
                List<Integer> li = myHatredList.mis.get(i);
                int root = myUnionSet.getRoot(i);
                if(li != null) {
                    for (Integer a : li) {
                        myHatredList.putHatredPair(root, a, 1);
                    }
                }
            }

            for (Map.Entry<Integer, List<Integer>> entry : myHatredList.mis.entrySet()){
                List<Integer> li = entry.getValue();
                for(int i = 1; i < li.size(); ++ i){
                    myUnionSet.merge(li.get(i - 1), li.get(i));
                }
            }

            int finalCount = myUnionSet.size[myUnionSet.getRoot(1)];

            System.out.print("Case #" + x + ": ");
            if(finalCount > (countriesCount >> 1)){
                System.out.println("yes");
            }
            else {
                System.out.println("no");
            }
        }
    }
}

