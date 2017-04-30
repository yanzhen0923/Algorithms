import java.util.*;

public class Test {

    public static class HatredPair {
        int left; int right;
        HatredPair(int l, int r) {
            left = l; right = r;
        }
    }

    private static class UnionSet{
        int parent[];
        int size[];
        UnionSet(int s){
            parent = new int[s];
            size = new int[s];
            for(int i = 0; i < s; ++ i){
                parent[i] = i; size[i] = 1;
            }
        }

        int getRoot(int id){
            if(parent[id] == id)
                return id;
            else {
                int res = getRoot(parent[id]);
                parent[id] = res;
                return res;
            }
        }

        void merge(int left, int right){
            int leftRoot = getRoot(left);
            int rightRoot = getRoot(right);
            if(leftRoot == parent[rightRoot]
                    || rightRoot == parent[leftRoot]){
                return;
            }
            if(size[leftRoot] < size[rightRoot]){
                parent[leftRoot] = rightRoot;
                size[rightRoot] += size[leftRoot];
            }
            else {
                parent[rightRoot] = leftRoot;
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

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int pizzaCount = sc.nextInt(); int pairCount = sc.nextInt();
            UnionSet us = new UnionSet(pizzaCount + 1);
            HatredPair[] hps = new HatredPair[pairCount];
            for(int i = 0; i < pairCount; ++ i){
                hps[i] = new HatredPair(sc.nextInt(), sc.nextInt());
            }
            HatredList hl = new HatredList();
            for(HatredPair hp : hps){
                hl.putHatredPair(hp.left, hp.right, 1);
            }
            for(Map.Entry<Integer, List<Integer>> entry : hl.mis.entrySet()){
                List<Integer> li = entry.getValue();
                for(int i = 1; i < li.size(); ++ i){
                    us.merge(li.get(i), li.get(i - 1));
                }
            }
            boolean res = true;
            for(HatredPair hp : hps){
                if(us.getRoot(hp.left) == us.getRoot(hp.right)){
                    res = false;
                    break;
                }
            }

            String resString = res ? "yes" : "no";
           System.out.println("Case #" + x + ": " + resString);
        }
    }
}

