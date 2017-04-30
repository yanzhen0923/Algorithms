import javafx.scene.effect.InnerShadow;

import java.util.*;

public class Test {

    private static class MaxGroup{
        boolean[][] graph;
        List<Integer> absoluteMax;
       // List<List<Integer>> result;
        List<Integer> actualMax;
        //int count;
        MaxGroup(boolean[][] g, List<Integer> absMax){
            graph = g;
          //  result = new ArrayList<>();
            absoluteMax = absMax;
            actualMax = new ArrayList<>();
        }

        void generate(List<Integer> res, Integer posID) {
            if (posID == absoluteMax.size()) {
                if(verify(res)){
                    if(res.size() > actualMax.size()) {
                        actualMax = new ArrayList<>(res);
                    }
                }
                return;
            }

            generate(res, posID + 1);

            res.add(absoluteMax.get(posID));
            generate(res, posID + 1);
            res.remove(res.size() - 1);
        }

        boolean verify(List<Integer> candiList){
            for(Integer i : candiList){
                for (Integer j : candiList){
                    if(!graph[i][j]){
                        return false;
                    }
                }
            }
            return true;
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {

            int nodes = sc.nextInt(); int matches = sc.nextInt();
            boolean[][] graph = new boolean[nodes + 1][nodes + 1];
            int left, right;
            List<Integer> li = new ArrayList<>();
            for(int i = 0; i < matches; ++ i){
                left = sc.nextInt(); right = sc.nextInt();
                graph[left][right] = true;
                graph[right][left] = true;
                if(!li.contains(left)){
                    li.add(left);
                }
                if(!(li.contains(right))){
                    li.add(right);
                }
            }
            for(int i = 1; i <= nodes; ++ i){
                graph[i][i] = true;
            }

            Collections.sort(li);

            if(nodes == 1 || matches == 0){
                System.out.println("Case #" + x + ": " + 1 + " ");
                continue;
            }

            MaxGroup mg = new MaxGroup(graph, li);
            mg.generate(new ArrayList<Integer>(), 0);


           System.out.print("Case #" + x + ": ");
            for(Integer i : mg.actualMax){
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}

