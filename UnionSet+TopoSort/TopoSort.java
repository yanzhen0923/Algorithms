import java.util.*;

public class Test {

    private static class TopoSort{
        Map<Integer, Set<Integer>> mil;
        int[] incomingEdges, points;
        Queue<Integer> qi;
        int finalPoints;
        TopoSort(int count){
            finalPoints = 0;
            incomingEdges = new int[count + 1];
            points = new int[count + 1];
            Arrays.fill(incomingEdges, 0);
            qi = new LinkedList<>();
            mil = new HashMap<>();
        }

        void traverse(){
            for(int i = 1; i < incomingEdges.length; ++ i){
                if(incomingEdges[i] == 0){
                    qi.add(i);
                }
            }
            while (!qi.isEmpty()){
                int toPick = qi.remove();
                finalPoints += points[toPick];
                Set<Integer> li= mil.get(toPick);
                if(li != null) {
                    for (Integer i : li) {
                        --incomingEdges[i];
                        if (incomingEdges[i] == 0) {
                            qi.add(i);
                        }
                    }
                }
            }
        }

        void put(int source, int dist){
            Set<Integer> si = mil.get(source);
            if(si == null){
                si = new TreeSet<>();
                si.add(dist);
                mil.put(source, si);
                ++ incomingEdges[dist];
            }
            else{
                if(si.add(dist)){
                    ++ incomingEdges[dist];
                }
            }

        }


    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCaseCount = sc.nextInt();
        for(int x = 1; x <= testCaseCount; ++ x){
            int sticks = sc.nextInt(); int junctions = sc.nextInt();
            TopoSort tps = new TopoSort(sticks);
            for(int i = 1; i <= sticks; ++ i){
                tps.points[i] = sc.nextInt();
            }
            for(int i = 0; i < junctions; ++ i){
                tps.put(sc.nextInt(), sc.nextInt());
            }
            tps.traverse();
            System.out.println("Case #" + x + ": " + tps.finalPoints);
        }
    }
}

