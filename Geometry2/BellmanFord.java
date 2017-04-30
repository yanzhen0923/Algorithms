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
        int origin;
        int[] trust;
        BellmanFord(int o, int size){
            this.origin = o;
            trust = new int[size];
            mil = new HashMap<>();
            qi = new ArrayDeque<>();
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
            while (!qi.isEmpty()) {
                Integer from = qi.remove();
                List<AdjacentEdge> lae = mil.get(from);
                if (lae != null) {
                    for (AdjacentEdge ae : lae) {
                        if (ae.weight > trust[ae.to] && ae.to != origin) {
                            trust[ae.to] = ae.weight;
                            qi.add(ae.to);
                        }
                    }
                }
            }
        }

        int getSum(){
            int res = 0;
            for(int i : trust) {
                res += i;
            }
            return res;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            int n = sc.nextInt();
            BellmanFord bf = new BellmanFord(1, n + 1);
            for(int i = 1; i <= n; ++ i){
                for(int j = 1; j <= n; ++ j){
                    int v = sc.nextInt();
                    if(v > 0){
                        bf.addEdge(j, i, v);
                    }
                }
            }
            bf.run();
            System.out.println("Case #" + x + ": " + bf.getSum());
        }
    }
}

