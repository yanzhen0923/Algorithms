import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;

public class Algorithm {

    private static class QueueElement implements Comparable<QueueElement>{
        int vertexID; int distToSource; int pre;
        QueueElement(int v, int d, int p){
            vertexID = v; distToSource = d; pre = p;
        }

        @Override
        public int compareTo(QueueElement o) {
            return this.distToSource - o.distToSource;
        }
    }

    private static class AdjacentEdge{
        int to; int weight; int pre;
        AdjacentEdge(int t, int w, int p){
            to = t; weight = w; pre = p;
        }
    }

    private static class Dijkstra{
        Queue<QueueElement> pq = new PriorityQueue<>();
        Map<Integer, List<AdjacentEdge>> adjList;
        int origin; int target;
        int[] distance; int[] predecessor; boolean visited[];
        Dijkstra(int o, int t, int size){
            this.origin = o; this.target = t;
            distance = new int[size];
            adjList = new HashMap<>();
            predecessor = new int[size];
            Arrays.fill(predecessor, -1);
            predecessor[origin] = origin;
            visited = new boolean[size];
        }

        void addEdge(int from, int to, int weight){
            List<AdjacentEdge> la = adjList.get(from);
            if(la == null){
                la = new ArrayList<>();
                la.add(new AdjacentEdge(to, weight, from));
                adjList.put(from, la);
            }
            else {
                la.add(new AdjacentEdge(to, weight, from));
            }
        }

        void run(){
            pq.add(new QueueElement(origin, 0, 0));
            while (!pq.isEmpty()){
                QueueElement qe = pq.remove();
                if(visited[qe.vertexID]){
                    continue;
                }
                if(qe.pre == predecessor[qe.vertexID]){
                    visited[qe.vertexID] = true;
                }
                List<AdjacentEdge> la = adjList.get(qe.vertexID);
                if(la != null){
                    for(AdjacentEdge ae : la){
                        int check = distance[qe.vertexID] + ae.weight;
                        if(check <= distance[ae.to]){
                            distance[ae.to] = check;
                            predecessor[ae.to] = qe.vertexID;
                            pq.add(new QueueElement(ae.to, check, qe.vertexID));
                        }
                    }
                }
            }
        }

        List<Integer> getResult(){
            List<Integer> res = new ArrayList<>();
            while(target != origin){
                res.add(target + 1);
                target = predecessor[target];
            }
            res.add(origin + 1);
            Collections.reverse(res);
            return res;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x){
            int nodeCount = sc.nextInt();
            Dijkstra dij = new Dijkstra(0, nodeCount - 1, nodeCount);
            for(int i = 0; i < nodeCount; ++ i){
                for(int j = 0; j < nodeCount; ++ j) {
                    int dst = sc.nextInt();
                    if(i != j){
                        dij.addEdge(i, j, dst);
                    }
                    if(i == 0){
                        dij.distance[j] = dst;
                    }
                }
            }
            dij.run();
            System.out.print("Case #" + x + ": ");
            List<Integer> res = dij.getResult();
            for(Integer i : res){
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}

