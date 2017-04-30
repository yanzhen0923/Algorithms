import java.util.*;

public class Test {

    private static class PQElement implements Comparable<PQElement>{
        int id; int distance;
        PQElement(int id, int distance){
            this.id = id; this.distance = distance;
        }

        @Override
        public int compareTo(PQElement o) {
            return this.distance - o.distance;
        }
    }

    private static class AdjacentEdge{
        int to; int weight;
        AdjacentEdge(int t, int w){
            to = t; weight = w;
        }
    }

    private static class Dijkstra{
        Map<Integer, List<AdjacentEdge>> mil;
        Queue<PQElement> qi;
        int[] distance; int[] predecessor; boolean[] visited;
        int[] folderWeight;
        List<Integer> sources; int target; int INFINITY = 0x3f3f3f;
        Dijkstra(int t, int size){
            this.target = t;
            distance = new int[size]; predecessor = new int[size];
            visited = new boolean[size]; folderWeight = new int[size];
            mil = new HashMap<>();
            qi = new PriorityQueue<>();
            Arrays.fill(distance, INFINITY);
            Arrays.fill(visited, false);
            Arrays.fill(predecessor, -1);
            distance[target] = 0;
        }

        void setSourceList(List<Integer> sourceIDs){
            sources = sourceIDs;
        }

        void addEdge(int from, int to, int weight){
            List<AdjacentEdge> lae = mil.get(from);
            if(lae == null){
                lae = new ArrayList<>();
                lae.add(new AdjacentEdge(to, weight));
                mil.put(from, lae);
            }
            else{
                lae.add(new AdjacentEdge(to, weight));
            }
        }

        void run() {
            qi.add(new PQElement(target, distance[target]));
            while (!qi.isEmpty()){
                PQElement from = qi.remove();
                if(visited[from.id]){
                    continue;
                }
                visited[from.id] = true;
                List<AdjacentEdge> lae = mil.get(from.id);
                if(lae != null) {
                    for (AdjacentEdge ae : lae){
                        int check = distance[from.id] + ae.weight;
                        if(check < distance[ae.to]){
                            distance[ae.to] = check;
                            predecessor[ae.to] = from.id;
                            qi.add(new PQElement(ae.to, check));
                        }
                    }
                }
            }
        }

        int getResult(){
            int tmp = INFINITY; int beginNode = -1;
            for(Integer i : sources){
                if(distance[i] < tmp){
                    beginNode = i;
                    tmp = distance[i];
                }
            }
            if(tmp == INFINITY) {
                return INFINITY;
            }
            else{
                int res = 0;
                while (predecessor[beginNode] != -1){
                    res += folderWeight[beginNode];
                    beginNode = predecessor[beginNode];
                }
                return res;
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int folderCount = sc.nextInt(); int targetID = sc.nextInt();
            int takenSteps = sc.nextInt();
            Dijkstra dij = new Dijkstra(targetID, folderCount + 1);
            boolean[] mark = new boolean[folderCount + 1];
            Arrays.fill(mark, false);
            for(int i = 1; i <= folderCount; ++ i){
                dij.folderWeight[i] = sc.nextInt();
            }
            for(int i = 1; i <= folderCount; ++ i){
                int adjCount = sc.nextInt();
                for(int j = 0; j < adjCount; ++ j){
                    dij.addEdge(sc.nextInt(), i, dij.folderWeight[i]);
                }
            }
            int test; Stack<Integer> si = new Stack<>();
            List<Integer> beginList = new ArrayList<>();
            beginList.add(1);
            for(int i = 0; i < takenSteps; ++ i){
                test = sc.nextInt();
                if(test != 0){
                    beginList.add(test);
                    si.push(test);
                }
                else {
                    if(!si.isEmpty()){
                        beginList.remove(si.pop());
                    }
                }
            }
            dij.setSourceList(beginList);
            dij.run();
            int res = dij.getResult();
            String ans;
            if(res == dij.INFINITY){
                ans = "impossible";
            }
            else{
                ans = String.valueOf(res + dij.folderWeight[targetID]);
            }
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

