import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Test {

    private static class DesertEdge {
    int begin; int end; int peopleCount;
    public DesertEdge(int begin, int end, int weight){
        this.begin = begin; this.end = end; this.peopleCount = weight;
    }
}

private static class DEComp implements Comparator<DesertEdge>{
    @Override
    public int compare(DesertEdge ce1, DesertEdge ce2){
        return ce2.peopleCount - ce1.peopleCount;
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
            int junctionsCount = sc.nextInt(); int edgeCount = sc.nextInt();
            DesertEdge[] ces = new DesertEdge[edgeCount];
            int totalWeight = 0;
            for(int i = 0; i < edgeCount; ++ i){
                int b = sc.nextInt(); int e = sc.nextInt(); int partialPeople = sc.nextInt();
                ces[i] = new DesertEdge(b, e, partialPeople);
                totalWeight += partialPeople;
            }
            Arrays.sort(ces, new DEComp());

            UnionSet us = new UnionSet(junctionsCount + 1);
            for(int i = 0; i < ces.length; ++ i){
                if(us.getRoot(ces[i].begin) != us.getRoot(ces[i].end)){
                    us.merge(ces[i].begin, ces[i].end);
                    totalWeight -= ces[i].peopleCount;
                    if(us.getSizeOfRoot(ces[i].begin) == junctionsCount){
                        break;
                    }
                }
            }
            System.out.println("Case #" + x + ": " + totalWeight);
        }
    }
}

