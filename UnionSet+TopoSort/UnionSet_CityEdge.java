import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Test {

    private static class CityEdge{
        int begin; int end; int weight;
        public CityEdge(int begin, int end, int weight){
            this.begin = begin; this.end = end; this.weight = weight;
        }
    }

    private static class CEComp implements Comparator<CityEdge>{
        @Override
        public int compare(CityEdge ce1, CityEdge ce2){
            return ce2.weight - ce1.weight;
        }
    }

    private static class UnionSet{
        int parent[];
        int size[];
        UnionSet(int s){
            parent = new int[s]; size = new int[s];
            for(int i = 0; i < s; ++ i){
                parent[i] = i;
            }
            Arrays.fill(size, 1);
        }

        int getRoot(int id){
            if(parent[id] == id)
                return id;
            else {
                int res = getRoot(parent[id]);
                parent[id] = res; return res;
            }
        }

        void merge(int left, int right){
            int leftRoot = getRoot(left); int rightRoot = getRoot(right);
            if(leftRoot == parent[rightRoot] || rightRoot == parent[leftRoot]){
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

        int getSizeOfRoot(int id){
            return size[getRoot(id)];
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int cityCount = sc.nextInt(); int edgeCount = sc.nextInt();
            CityEdge[] ces = new CityEdge[edgeCount];
            int totalWeight = 0;
            for(int i = 0; i < edgeCount; ++ i){
                int b = sc.nextInt(); int e = sc.nextInt(); int partialWeight = sc.nextInt() * sc.nextInt();
                ces[i] = new CityEdge(b, e, partialWeight);
                totalWeight += partialWeight;
            }
            Arrays.sort(ces, new CEComp());

            UnionSet us = new UnionSet(cityCount + 1);
            int nodeCount = 0;
            for(int i = 0; i < ces.length; ++ i){
                if(us.getRoot(ces[i].begin) != us.getRoot(ces[i].end)){
                    us.merge(ces[i].begin, ces[i].end);
                    totalWeight -= ces[i].weight;
                    nodeCount = us.getSizeOfRoot(ces[i].begin);
                    if(nodeCount == cityCount){
                        break;
                    }
                }
            }
            System.out.print("Case #" + x + ": ");
            if(nodeCount < cityCount){
                System.out.println("impossible");
            }
            else {
                System.out.println(totalWeight);
            }
        }
    }
}

