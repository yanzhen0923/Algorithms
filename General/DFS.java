import java.util.*;

public class Test {


    private static class Verifier{

        Map<Integer, char[]> mic;
        int ans;

        Verifier(Map<Integer, char[]> mic){
            this.mic = mic;
            ans = 0;
        }

        boolean verify(List<Character> lc, Character c, Integer num){
            for(int i = 0; i < lc.size(); ++ i){
                if(lc.get(i).equals(c) && num + i == 12){
                    return false;
                }
            }
            return true;
        }

        boolean validG(List<Character> lc){
            int sum = 0;
            for(Character c : lc){
                if(c.equals('G')){
                    ++ sum;
                }
            }
            return sum == 1;
        }

        void generate(List<Character> res, Integer posID){
            if(res.size() == 11) {
                if(validG(res)) {
                    ++ans;
                }
                return;
            }
            char[] lc = mic.get(posID);
            for(Character c : lc){
                if(verify(res, c, posID)) {
                    res.add(c);
                    generate(res, posID + 1);
                    res.remove(res.size() - 1);
                }
            }
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x){
            Map<Integer, char[]> mic = new HashMap<>();
            for(int i = 1; i <= 11; ++ i){
                String roles = sc.next();
                mic.put(i, roles.toCharArray());
            }

            Verifier vf = new Verifier(mic);
            vf.generate(new ArrayList<Character>(), 1);

            System.out.println("Case #" + x + ": " + vf.ans);
        }
    }
}

