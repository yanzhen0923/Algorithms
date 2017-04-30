import java.lang.management.MemoryUsage;
import java.util.*;

public class Test {

    private static final int size = 4;
    private static final int nonSense = -3;
    private static final int waterSource = -2;
    private static final int questionMark = -1;
    private static final int isPlant = -5;
    private static final int good = -4;

    private static class MyPair{
        int i; int j;
        MyPair(int i, int j){
            this.i = i; this.j = j;
        }

        @Override
        public boolean equals(Object o){
            if( !(o instanceof MyPair)) {
                return false;
            }
            MyPair mp = (MyPair)o;
            return mp.i == i && mp.j == j;
        }

        @Override
        public int hashCode(){
            return i^j;
        }

    }

    private static class PermutationGenerator {

        // List<Map<MyPair, Integer>> result;
        List<MyPair> staticCandidates;
        Map<MyPair, Integer> staticPlantsList;
        MyPair waterSourcePos;
        Map<MyPair, Integer> plantsList;
        Queue<MyPair> q;
        Integer[][] staticLayout;
        Integer[][] staticFilter;
        int maxH;
        int resCount;


        PermutationGenerator(Integer[][] layout, Integer[][] filter, List<MyPair> candi, MyPair waterS, Map<MyPair, Integer> pList, int m) {
            this.staticLayout = layout;
            this.staticFilter = filter;
            maxH = m;
            //result = new ArrayList<>();
            resCount = 0;
            staticCandidates = candi;
            staticPlantsList = pList;
            waterSourcePos = waterS;
            plantsList = new HashMap<>();
            q = new ArrayDeque<>();
        }


        void generate(Map<MyPair, Integer> res, Integer posID) {
            if (res.size() == staticCandidates.size()) {
                if (verify(res)) {
                    ++resCount;
                }
                return;
            }
            for (int i = 0; i <= maxH; ++i) {
                res.put(staticCandidates.get(posID), i);
                generate(res, posID + 1);
                res.remove(staticCandidates.get(posID));
            }
        }

        boolean verify(Map<MyPair, Integer> assigned) {
            for (Map.Entry<MyPair, Integer> me : assigned.entrySet()) {
                staticLayout[me.getKey().i][me.getKey().j] = me.getValue();
            }

            q.clear();
            plantsList.clear();
            boolean[][] visited = new boolean[size][size];
            q.add(waterSourcePos);
            while (!q.isEmpty()) {
                MyPair mp = q.remove();
                int i = mp.i;
                int j = mp.j;
                if (!(i >= 0 && i <= size - 1 && j >= 0 && j <= size - 1)) {
                    continue;
                }
                if (visited[i][j]) {
                    continue;
                }
                visited[i][j] = true;
                if (i > 0 && staticLayout[i][j] >= staticLayout[i - 1][j]  && staticLayout[i - 1][j] != nonSense) {
                    MyPair ele = new MyPair(i - 1, j);
                    if (staticFilter[i - 1][j] == isPlant) {
                        plantsList.put(ele, good);
                    }
                    q.add(ele);
                }
                if (i < size - 1 && staticLayout[i][j] >= staticLayout[i + 1][j] && staticLayout[i + 1][j] != nonSense) {
                    MyPair ele = new MyPair(i + 1, j);
                    if (staticFilter[i + 1][j] == isPlant) {
                        plantsList.put(ele, good);
                    }
                    q.add(ele);
                }
                if (j > 0 && staticLayout[i][j] >= staticLayout[i][j - 1]  && staticLayout[i][j - 1] != nonSense) {
                    MyPair ele = new MyPair(i, j - 1);
                    if (staticFilter[i][j - 1] == isPlant) {
                        plantsList.put(ele, good);
                    }
                    q.add(ele);
                }
                if (j < size - 1 && staticLayout[i][j] >= staticLayout[i][j + 1] && staticLayout[i][j + 1] != nonSense) {
                    MyPair ele = new MyPair(i, j + 1);
                    if (staticFilter[i][j + 1] == isPlant) {
                        plantsList.put(ele, good);
                    }
                    q.add(ele);
                }
                if (plantsList.size() == staticPlantsList.size())
                    return true;
            }
            return plantsList.size() == staticPlantsList.size();
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {
            int maximalHeight = sc.nextInt();
            int waterHeight = sc.nextInt();
            sc.nextLine();
            Integer[][] staticFilter = new Integer[size][size];
            Integer[][] staticLayout = new Integer[size][size];
            List<MyPair> lm = new ArrayList<>();
            Map<MyPair, Integer> plantsList = new HashMap<>();
            for(int i = 0; i < size; ++ i){
                String info = sc.nextLine();
                for(int j = 0; j < size; ++ j){
                    if(info.charAt(j) == '_'){
                        staticFilter[i][j] = nonSense;
                        staticLayout[i][j] = nonSense;
                    }
                    else if(info.charAt(j) == '*'){
                        staticFilter[i][j] = waterSource;
                        staticLayout[i][j] = waterHeight;
                    }
                    else if(info.charAt(j) == '?'){
                        staticFilter[i][j] = questionMark;
                        lm.add(new MyPair(i, j));
                    }
                    else{
                        staticFilter[i][j] = isPlant;
                        plantsList.put(new MyPair(i, j), good);
                        staticLayout[i][j] = Character.getNumericValue(info.charAt(j));
                    }
                }
            }
            MyPair src = new MyPair(-1, -1);
            for(int i = 0; i < size; ++ i){
                for(int j = 0; j < size; ++ j){
                    if(staticFilter[i][j] == waterSource){
                        src = new MyPair(i, j);
                    }
                }
            }
            PermutationGenerator pg = new PermutationGenerator(staticLayout, staticFilter, lm, src, plantsList, maximalHeight);
            Map<MyPair, Integer> mmi = new HashMap<>();
            pg.generate(mmi, 0);
            System.out.println("Case #" + x + ": " + pg.resCount);
        }
    }
}

