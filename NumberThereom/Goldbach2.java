import java.util.*;

public class Test {

    private static class PermutationGenerator {
        Map<Integer, Integer> result;
        PermutationGenerator(){
            result = new HashMap<>();
        }

        String getString(List<Character> ans){
            StringBuilder result = new StringBuilder(ans.size());
            for (Character c : ans) {
                result.append(c);
            }
            return  result.toString();
        }

        void generate(List<Character> candidates, List<Character> predecessors){
            if(candidates.size() == 1){
                predecessors.add(candidates.get(0));
                int parsed = Integer.valueOf(getString(predecessors));
                result.put(parsed, 0);
            }
            else {
                for(int i = 0; i < candidates.size(); ++ i){
                    List<Character> newCandidates = new ArrayList<>(candidates);
                    List<Character> newPredecessors = new ArrayList<>(predecessors);
                    newPredecessors.add(candidates.get(i));
                    newCandidates.remove(i);
                        generate(newCandidates, newPredecessors);
                }
            }
        }
    }


    private static class Goldbach {
        private int totalNum = 5761455;
        private int maxBound = 100000000;
        boolean[] isPrime;
        int[] primeSet;
        private void build() {

            isPrime = new boolean[maxBound];
            primeSet = new int[totalNum];
            Arrays.fill(isPrime, true);

            int cmp = (int) Math.sqrt(maxBound);
            for (int i = 2; i < cmp; ++i) {
                if (isPrime[i]) {
                    for (int j = i * i; j < maxBound; j += i) {
                        isPrime[j] = false;
                    }
                }
            }

            int n = -1;
            for (int i = 2; i < maxBound; i++)
                if (isPrime[i]) primeSet[++n] = i;
        }

        int getIndexLargerThan(int num){
            int i = 0; int j = primeSet.length - 1;
            while (i < j - 1){
                int mid = (i + j) / 2;
                if(primeSet[mid] < num){
                    i = mid;
                }
                else {
                    j = mid;
                }
            }
            int left = Math.min(i, j);
            while (true){
                if(primeSet[left] > num){
                    return left;
                }
                ++ left;
            }
        }
    }

    public static void main(String[] args){
        Goldbach gb = new Goldbach();
        gb.build();
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int k = sc.nextInt();
            int startIndex = gb.getIndexLargerThan(k);
            for(int m = startIndex; m < gb.maxBound ; ++ m) {

                String kString = String.valueOf(gb.primeSet[m]);
                char[] digits = kString.toCharArray();
                Character[] newDigits = new Character[digits.length];
                for (int i = 0; i < newDigits.length; ++i) {
                    newDigits[i] = digits[i];
                }
                PermutationGenerator pg = new PermutationGenerator();
                List<Character> pre = Arrays.asList(newDigits);
                pg.generate(pre, new ArrayList<Character>());
                double compare = Math.log10(gb.primeSet[m]);
                int primeCnt = 0;
                for(Map.Entry<Integer, Integer> me : pg.result.entrySet()){
                    if(gb.isPrime[me.getKey()]){
                        ++ primeCnt;
                    }
                }
                if(primeCnt == pg.result.size() || primeCnt > compare){
                    System.out.println("Case #" + x + ": " + gb.primeSet[m]);
                    break;
                }
            }
        }
    }
}

