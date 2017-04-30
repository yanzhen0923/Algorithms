import java.util.*;

public class Test {
    /*
The code below is referenced from
https://web.stanford.edu/~liszt90/acm/notebook.html#file3
The original code is in cpp
I tried to translate this into java code
 */
    private static class Edge{
        int from, to, cap, flow, index;
        Edge(int fr, int t, int c, int fl, int i){
            from = fr; to = t; cap = c; flow = fl; index = i;
        }
    }

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
The original code is in cpp
I tried to translate this into java code
 */
    private static class MyPair{
        int left, right;
        MyPair(int l, int r){
            left = l; right = r;
        }
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof MyPair )) return false;
            MyPair mp = (MyPair)o;
            return mp.left == this.left && mp.right == this.right;
        }

        @Override
        public int hashCode() {
            return (left + right) * left * right;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        int INFINITY = 0x3f3f3f;
        for(int x = 1; x <= testCase; ++ x) {
            int teams = sc.nextInt();
            int matches = sc.nextInt();
            int[] currentScore = new int[teams + 1];
            int[][] matchList = new int[teams + 1][teams + 1];
            int[][] nodeID = new int[teams + 1][teams + 1];
            int numOfMatchNode = 1;
            for (int i = 1; i <= teams; ++i) {
                currentScore[i] = sc.nextInt();
            }
            int[] restScore = new int[teams + 1];
            for (int i = 0; i < matches; ++i) {
                int left = sc.nextInt();
                int right = sc.nextInt();
                if (right < left) {
                    int temp = right;
                    right = left;
                    left = temp;
                }
                if (matchList[left][right] == 0) {
                    ++numOfMatchNode;
                    nodeID[left][right] = numOfMatchNode ++;
                }
                ++matchList[left][right];
                ++restScore[left];
                ++restScore[right];
            }
            System.out.print("Case #" + x + ": ");
            int sourceID = 0;
            int targetID = numOfMatchNode + teams + 3;
            for (int targetTeam = 1; targetTeam <= teams; ++targetTeam) {
                PushRelabel pr = new PushRelabel(targetID + 1);
                for (int i = 1; i <= teams; ++i) {
                    for (int j = i + 1; j <= teams; ++j) {
                        if (targetTeam != i && targetTeam != j && matchList[i][j] != 0) {
                            pr.addEdge(sourceID, nodeID[i][j], matchList[i][j]);
                            pr.addEdge(nodeID[i][j], i + numOfMatchNode, INFINITY);
                            pr.addEdge(nodeID[i][j], j + numOfMatchNode, INFINITY);
                        }
                    }
                }
                boolean fail = false;
                for (int i = 1; i <= teams; ++i) {
                    if (targetTeam != i) {
                        int cap = currentScore[targetTeam] + restScore[targetTeam] - currentScore[i];
                        if (cap < 0) {
                            cap = 0;
                            fail = true;
                        }
                        pr.addEdge(i + numOfMatchNode, targetID, cap);
                    }
                }
                int res = pr.getResult(sourceID, targetID);
                String ans = res < matches - restScore[targetTeam] ? "no " : "yes ";
                if(fail) ans = "no ";
                System.out.print(ans);
            }
            System.out.println();
        }
    }
}

