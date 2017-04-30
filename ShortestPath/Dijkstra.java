import java.util.*;

public class Test {

    private static class QueueElement implements Comparable<QueueElement>{
        int vertexID; int distToSource;
        QueueElement(int v, int d){
            vertexID = v; distToSource = d;
        }

        @Override
        public int compareTo(QueueElement o) {
            return this.distToSource - o.distToSource;
        }
    }

    private static class AdjacentEdge{
        int to; int weight;
        AdjacentEdge(int t, int w){
            to = t; weight = w;
        }
    }

    private static class Dijkstra{
        Queue<QueueElement> pq;
        Map<Integer, List<AdjacentEdge>> adjList;
        int[] distance; int origin; int target; int INFINITY = 0x3f3f3f;
        boolean[] visited;
        Dijkstra(int o, int t, int size){
            this.origin = o; this.target = t;
            distance = new int[size];
            visited = new boolean[size];
            pq = new PriorityQueue<>();
            adjList = new HashMap<>();
            Arrays.fill(visited, false);
            Arrays.fill(distance, INFINITY);
            distance[origin] = 0;
        }

        void addEdge(int from, int to, int weight, int round){
            List<AdjacentEdge> la = adjList.get(from);
            if(la == null){
                la = new ArrayList<>();
                la.add(new AdjacentEdge(to, weight));
                adjList.put(from, la);
            }
            else {
                la.add(new AdjacentEdge(to, weight));
            }
            -- round;
            if(round >= 0){
                addEdge(to, from, weight, round);
            }
        }

        void run(){
            pq.add(new QueueElement(origin, 0));
            while (!pq.isEmpty()){
                QueueElement qe = pq.remove();
                if(visited[qe.vertexID]){
                    continue;
                }
                visited[qe.vertexID] = true;
                List<AdjacentEdge> la = adjList.get(qe.vertexID);
                for (AdjacentEdge ae : la){
                    int check = distance[qe.vertexID] + ae.weight;
                    if(check < distance[ae.to]){
                        distance[ae.to] = check;
                        pq.add(new QueueElement(ae.to, check));
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int pointCount = sc.nextInt(); int edgeCount = sc.nextInt();
            Dijkstra dij = new Dijkstra(1, pointCount, pointCount + 1);
            for(int i = 0; i < edgeCount; ++ i){
                dij.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt(), 1);
            }
            dij.run();
            System.out.println("Case #" + x + ": " + dij.distance[pointCount]);
        }
    }
}

