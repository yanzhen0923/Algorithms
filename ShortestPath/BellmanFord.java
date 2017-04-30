import java.util.*;

public class Test {

    private static class AdjacentEdge{
        int to; double weight; double originalWeight;
        AdjacentEdge(int t, double w, double o){
            to = t; weight = w; originalWeight = o;
        }
    }

    private static class BellmanFord{
        Queue<Integer> pq;
        Map<Integer, List<AdjacentEdge>> adjList;
        double[] distance; int origin; int target; int INFINITY = 0x3f3f3f;
        int IMPOSSIBLE = -INFINITY; int ISERROR = IMPOSSIBLE + 1;
        boolean[] visited; int[] predecessor; double[][] realValue; boolean err;
        BellmanFord(int o, int t, int size){
            this.origin = o; this.target = t;
            distance = new double[size];
            visited = new boolean[size];
            adjList = new HashMap<>();
            predecessor = new int[size];
            realValue = new double[size][size];
            Arrays.fill(distance, INFINITY);
            Arrays.fill(predecessor, -1);
            distance[origin] = 0;
            err = false;
        }

        void addEdge(int from, int to, double weight, double ow){
            List<AdjacentEdge> la = adjList.get(from);
            if(la == null){
                la = new ArrayList<>();
                la.add(new AdjacentEdge(to, weight, ow));
                adjList.put(from, la);
            }
            else {
                la.add(new AdjacentEdge(to, weight, ow));
            }
        }

        void run() {
            for (int i = 2; i < visited.length; ++i) {
                pq = new LinkedList<>();
                Arrays.fill(visited, false);
                pq.add(origin);
                while (!pq.isEmpty()) {
                    Integer qe = pq.remove();
                    if (visited[qe]) {
                        continue;
                    }
                    visited[qe] = true;
                    List<AdjacentEdge> la = adjList.get(qe);
                    if(la != null) {
                        for (AdjacentEdge ae : la) {
                            double check = distance[qe] + ae.weight;
                            if (check < distance[ae.to] && distance[qe] != INFINITY) {
                                distance[ae.to] = check;
                                predecessor[ae.to] = qe;
                                realValue[qe][ae.to] = ae.originalWeight;
                            }
                            pq.add(ae.to);
                        }
                    }
                }
            }
            for(Map.Entry<Integer, List<AdjacentEdge>> entry : adjList.entrySet()){
                int ori = entry.getKey();
                for(AdjacentEdge ae : entry.getValue()){
                    if(distance[ori] + ae.weight < distance[ae.to] && distance[ori] != INFINITY){
                        err = true;
                        return;
                    }
                }
            }

        }

        double getRes(int target){
            if(err)
                return ISERROR;
            if(distance[target] == INFINITY)
                return IMPOSSIBLE;
            double res = 1.0;
            while (predecessor[target] != -1){
                res *= realValue[predecessor[target]][target];
                target = predecessor[target];
            }
            return  res;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int currencyCount = sc.nextInt(); int edgeCount = sc.nextInt();
            BellmanFord bmf = new BellmanFord(1, currencyCount, currencyCount + 1);
            for(int i = 0; i < edgeCount; ++ i){
                int f = sc.nextInt(); int t = sc.nextInt(); double v = sc.nextDouble();
                bmf.addEdge(f, t, Math.log(v), v);
            }
            bmf.run();
            String ans;
            double result = bmf.getRes(currencyCount);
            if (result == bmf.IMPOSSIBLE) ans = "impossible";
            else if(result == bmf.ISERROR) ans = "Jackpot";
            else ans = String.format("%.6f", result);
            System.out.println("Case #" + x + ": " + ans);

        }
    }
}

