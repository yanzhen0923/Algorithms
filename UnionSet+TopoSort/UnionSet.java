import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by yanz on 3/26/16.
 */
public class Test {

    private static class planet{
        int x; int y; int z;
        planet(int x, int y, int z){
            this.x = x; this.y = y; this.z = z;
        }
    }

    private static class WeightedEdge{
        int begin; int end; int weight;
        WeightedEdge(int b, int e, int z){
            this.begin = b; this.end = e; this.weight = z;
        }
    }

    static class WEComparator implements Comparator<WeightedEdge> {
        @Override
        public int compare(WeightedEdge we1, WeightedEdge we2) {
            return  we1.weight - we2.weight;
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

        int getSizeOfRoot(int id){
            return size[getRoot(id)];
        }

    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int planetsCount = sc.nextInt();
            planet[] planets = new planet[planetsCount];
            for(int i = 0; i < planetsCount; ++ i){
                planets[i] = new planet(sc.nextInt(), sc.nextInt(), sc.nextInt());
            }
            WeightedEdge[] edgeList = new WeightedEdge[ (planetsCount * (planetsCount - 1)) >> 1 ];
            int count = 0;
            for(int i = 0; i < planetsCount - 1; ++ i){
                for(int j = i + 1; j < planetsCount; ++ j){
                    edgeList[count ++] = new WeightedEdge(i, j, Math.abs(planets[i].x - planets[j].x) + Math.abs(planets[i].y - planets[j].y) + Math.abs(planets[i].z - planets[j].z));
                }
            }
            Arrays.sort(edgeList, new WEComparator());

            UnionSet us = new UnionSet(planetsCount + 1);
            int ans = 0;
            for(int i = 0; i < edgeList.length; ++ i){
                if(us.getRoot(edgeList[i].begin) != us.getRoot(edgeList[i].end)){
                    us.merge(edgeList[i].begin, edgeList[i].end);
                    ans += edgeList[i].weight;
                    if(us.getSizeOfRoot(edgeList[i].begin) == planetsCount){
                        break;
                    }
                }
            }
            System.out.println("Case #" + x + ": " + ans);

        }
    }
}

