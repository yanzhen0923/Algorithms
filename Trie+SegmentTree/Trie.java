import java.util.*;

public class Test {

    private static class Comp implements Comparator<String> {
        public int compare(String o1, String o2) {
            return Integer.compare(o2.length(), o1.length());
        }
    }

    //The following code is referenced from http://baike.baidu.com/view/2759664.htm?fromtitle=Trie%E6%A0%91&fromid=517527&type=syn
    private static class Trie {

        private static final int SIZE = 26 * 2;
        int ans = 0;

        private class TrieNode {
            private TrieNode[] successor;
            TrieNode() {
                successor = new TrieNode[SIZE];
            }
        }

        TrieNode root;
        Trie(){
            root = new TrieNode();
        }

        void insert(String str){
            TrieNode node = root; int pos; boolean addToAns = false;
            for(int i = 0; i < str.length(); ++ i){
                char exp = str.charAt(i);
                pos = Character.isLowerCase(exp) ? exp -'a' : (exp - 'A') + 26;
                if(node.successor[pos] == null){
                    node.successor[pos] = new TrieNode();
                }else{
                    if( (i == str.length() - 1) && node.successor[pos].successor != null){
                        addToAns = true;
                    }
                }
                node = node.successor[pos];
            }
            if(addToAns){
                ++ ans;
            }
        }

    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int count = sc.nextInt();
            List<String> ls = new ArrayList<>(count);
            Trie t = new Trie();
            for(int i = 0; i < count; ++ i) {
                ls.add(sc.next());
            }
            Collections.sort(ls, new Comp());
            for(String s : ls){
                t.insert(s);
            }
            System.out.println("Case #" + x + ": " + t.ans);
        }
    }
}

