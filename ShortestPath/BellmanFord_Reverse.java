import java.util.*;

public class Test {

    private static class AdjacentEdge{
        int to; int weight;
        AdjacentEdge(int t, int w){
            to = t; weight = w;
        }
    }

    private static class BellmanFord{
        Map<Integer, List<AdjacentEdge>> mil;
        Queue<Integer> qi;
        int[] distance; boolean[] visited;
        int origin; int target; int INFINITY = 0x3f3f3f;
        BellmanFord(int o, int t, int size){
            this.origin = o; this.target = t;
            distance = new int[size];
            visited = new boolean[size];
            mil = new HashMap<>();
            qi = new LinkedList<>();
            Arrays.fill(distance, INFINITY);
            Arrays.fill(visited, false);
            distance[origin] = 0;
            visited[origin] = true;
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
            qi.add(origin);
            while (!qi.isEmpty()){
                Integer from = qi.remove();
                visited[from] = false;
                List<AdjacentEdge> lae = mil.get(from);
                if(lae != null) {
                    for (AdjacentEdge ae : lae){
                        int check = distance[from] + ae.weight;
                        if(check < distance[ae.to]){
                            distance[ae.to] = check;
                            if(!visited[ae.to]){
                                visited[ae.to] = true;
                                qi.add(ae.to);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int taskCount = sc.nextInt();
            BellmanFord bmf = new BellmanFord(taskCount, 1, taskCount + 1);
            int[] weights = new int[taskCount + 1];
            for(int y = 1; y <= taskCount; ++ y){
                int weight = sc.nextInt(); int successor = sc.nextInt();
                weights[y] = weight;
                for(int z = 0; z < successor; ++ z){
                    int to = sc.nextInt();
                    bmf.addEdge(to, y, -weight);
                }
            }
            bmf.run();
            System.out.println("Case #" + x + ": " + (weights[taskCount] - bmf.distance[1]));

        }
    }
}

