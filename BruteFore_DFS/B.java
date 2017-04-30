import java.util.*;

public class Algorithm {

    private static class MyPair{
        int role; int chapterID;
        MyPair(int role, int chapterID){
            this.role = role; this.chapterID = chapterID;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof MyPair))
                return false;
            MyPair mp = (MyPair)obj;
            return this.role == mp.role && this.chapterID == mp.chapterID;
        }

        @Override
        public int hashCode(){
            return 0;
        }
    }

    private static Integer Pairing(Integer a, Integer b){
        return  (a + b) * (a + b + 1) / 2 + a;
    }

    private static class PermutationGenerator {

        List<MyPair> filter;
        Integer resultCount;
        PermutationGenerator(List<MyPair> filter){
            this.filter = filter;
            resultCount = 0;
        }

        void generate(List<MyPair> predecessors, List<MyPair> candidates){
            if(candidates.size() == 0){
                ++ resultCount;
            }
            else {
                for (int i = 0; i < candidates.size(); ++i) {
                    if (predecessors.size() != 0) {
                        if (candidates.get(i).role == predecessors.get(predecessors.size() - 1).role) {
                            continue;
                        }
                    }
                    boolean con = false;
                    for (int k = 1; k < filter.size(); k += 2) {
                        if(filter.get(k).equals(candidates.get(i))){
                            if(candidates.contains(filter.get(k - 1))){
                                con = true;
                                break;
                            }
                        }
                    }
                    if (con) {
                        continue;
                    }

                    List<MyPair> newCandidates = new ArrayList<>(candidates);
                    List<MyPair> newPredecessors = new ArrayList<>(predecessors);
                    newPredecessors.add(newCandidates.get(i));
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
            int chars = sc.nextInt();
            int dependencies = sc.nextInt();
            List<MyPair> candi = new ArrayList<>();
            List<MyPair> myFilter = new ArrayList<>();
            for(int i = 1; i <= chars; ++ i){
                int cur = sc.nextInt();
                for(int j = 1; j <= cur; ++ j){
                    candi.add(new MyPair(i, j));
                }
                for(int j = 2; j <= cur; ++ j){
                    myFilter.add(new MyPair(i, j - 1));
                    myFilter.add(new MyPair(i, j));
                }
            }

            for(int i = 0; i < dependencies; ++ i){
                for(int j = 0; j < 2; ++ j){
                    myFilter.add(new MyPair(sc.nextInt(), sc.nextInt()));
                }
            }
            List<MyPair> pred = new ArrayList<>();
            PermutationGenerator pg = new PermutationGenerator(myFilter);
            pg.generate(pred, candi);
            System.out.println("Case #" + x + ": " + pg.resultCount);
        }
    }
}

