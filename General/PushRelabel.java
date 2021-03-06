import java.util.*;

public class Test {

    private static class Edge{
        int from, to, cap, flow, index;
        Edge(int fr, int t, int c, int fl, int i){
            from = fr; to = t; cap = c; flow = fl; index = i;
        }
    }

    /*
    The code below is referenced from
    https://web.stanford.edu/~liszt90/acm/notebook.html#file3
    */
    private static class PushRelabel{
        int size;
        int[] excess, height, count;
        boolean[] active;
        Queue<Integer> q;
        ArrayList<ArrayList<Edge>> graph;
        PushRelabel(int sz){
            size = sz; graph = new ArrayList<>();
            excess = new int[size]; height = new int[size];
            active = new boolean[size]; count = new int[2 * size];
            for(int i = 0; i <= size; ++ i){
                graph.add(new ArrayList<Edge>());
            }
            q = new ArrayDeque<>();
        }

        void addEdge(int from, int to, int cap) {
            graph.get(from).add(new Edge(from, to, cap, 0, graph.get(to).size()));
            if(from == to) {
                List<Edge> le = graph.get(from);
                ++ le.get(le.size() - 1).index;
            }
            graph.get(to).add(new Edge(to, from, 0, 0, graph.get(from).size() - 1));
        }

        void enqueue(int v) {
            if (!active[v] && excess[v] > 0) {
                active[v] = true; q.add(v);
            }
        }

        void push(Edge e){
            int amt = Math.min(excess[e.from], e.cap - e.flow);
            if(amt == 0 || height[e.from] <= height[e.to]) return;
            e.flow += amt;
            graph.get(e.to).get(e.index).flow -= amt;
            excess[e.to] += amt; excess[e.from] -= amt;
            enqueue(e.to);
        }

        void gap(int h){
            for(int v = 0; v < size; ++ v){
                if(height[v] < h) continue;
                -- count[height[v]];
                height[v] = Math.max(height[v], size + 1);
                ++ count[height[v]];
                enqueue(v);
            }
        }

        void relabel(int v){
            -- count[height[v]];
            height[v] = 2 * size;
            for(int i = 0; i < graph.get(v).size(); ++ i){
                Edge e = graph.get(v).get(i);
                if(e.cap - e.flow > 0){
                    height[v] = Math.min(height[v], height[e.to] + 1);
                }
            }
            ++ count[height[v]];
            enqueue(v);
        }

        void disCharge(int v){
            for(int i = 0; i < graph.get(v).size() && excess[v] > 0; ++ i){
                push(graph.get(v).get(i));
            }
            if(excess[v] > 0){
                if(count[height[v]] == 1){
                    gap(height[v]);
                }
                else {
                    relabel(v);
                }
            }
        }

        int getResult(int src, int tgt){
            count[0] = size - 1; count[size] = 1; height[src] = size;
            active[src] = true; active[tgt] = true;
            for(int i = 0; i < graph.get(src).size(); ++ i){
                Edge e = graph.get(src).get(i);
                excess[src] += e.cap;
                push(e);
            }
            while (!q.isEmpty()){
                int v = q.remove();
                active[v] = false;
                disCharge(v);
            }
            int res = 0;
            for(int i = 0; i < graph.get(src).size(); ++ i){
                res += graph.get(src).get(i).flow;
            }
            return res;
        }
    }
    /*
    The code above is referenced from
    https://web.stanford.edu/~liszt90/acm/notebook.html#file3
    */

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x){
            int printers = sc.nextInt();
            int papers = sc.nextInt();
            PushRelabel pr = new PushRelabel(papers + printers + 10);
            for(int i = 1; i <= printers; ++ i){
                String assigned = sc.next();
                String[] numbers = assigned.split(",");
                for(String s : numbers){
                    String[] subNumbers = s.split("-");
                    if(subNumbers.length == 1){
                        pr.addEdge(i, Integer.valueOf(subNumbers[0]) + printers + 1, 1);
                    }
                    else {
                        int begin = Integer.valueOf(subNumbers[0]); int end = Integer.valueOf(subNumbers[1]);
                        for(int j = begin; j <= end; ++ j){
                            pr.addEdge(i, j + printers + 1, 1);
                        }
                    }
                }
            }
            for(int i = 1; i <= printers; ++ i){
                pr.addEdge(0, i, 1);
            }
            int target = printers + papers + 3;
            for(int j = 1; j <= papers; ++ j){
                pr.addEdge(j + printers + 1, target, 1);
            }
            String ans; int result = pr.getResult(0, target);
            ans = result >= papers ? "yes" : "no";
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

