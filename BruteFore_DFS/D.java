import java.util.*;

public class Test {

    private static class PermutationGenerator {
        List<List<Integer>> result;
        PermutationGenerator(){
            result = new ArrayList<>();
        }

        void generate(List<Integer> predecessors, List<Integer> candidates){
            if(candidates.size() == 1){
                predecessors.add(candidates.get(0));
                result.add(predecessors);
            }
            else {
                for(int i = 0; i < candidates.size(); ++ i){
                    List<Integer> newCandidates = new ArrayList<>(candidates);
                    List<Integer> newPredecessors = new ArrayList<>(predecessors);
                    newPredecessors.add(candidates.get(i));
                    newCandidates.remove(i);
                    generate(newPredecessors, newCandidates);
                }
            }
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {
            int size = sc.nextInt();
            int[][] graph = new int[size][size];
            for(int i = 0; i < size; ++ i){
                for(int j = 0; j < size; ++ j){
                    graph[i][j] = sc.nextInt();
                }
            }
            PermutationGenerator pg = new PermutationGenerator();
            List<Integer> li = new ArrayList<>();
            List<Integer> ca = new ArrayList<>();
            for(int i = 0; i < size; ++ i){
                ca.add(i);
            }
            pg.generate(li, ca);

            int compare = Integer.MAX_VALUE;
            int res = -1;
            for(int i = 0; i < pg.result.size(); ++ i){
                int temp = 0;
                List<Integer> sp = pg.result.get(i);
                for(int j = 1; j < sp.size(); ++ j){
                    temp += graph[sp.get(j)][sp.get(j - 1)];
                }
                temp += graph[sp.get(0)][sp.get(sp.size() - 1)];
                if(temp < compare){
                    compare = temp;
                    res = i;
                }
            }

            System.out.print("Case #" + x + ": ");
            for(Integer e : pg.result.get(res)){
                System.out.print((e + 1) + " ");
            }
            System.out.println();
        }
    }
}

