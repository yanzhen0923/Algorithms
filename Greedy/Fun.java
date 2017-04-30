import java.util.*;

public class Test {

    static class MyItem implements Comparable<MyItem>{

        int fun; int cap;

        MyItem(int f, int c){
            fun = f; cap = c;
        }

        @Override
        public int compareTo(MyItem o) {
            return o.fun - this.fun;
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x) {
            int items = sc.nextInt(); int hours = sc.nextInt();
            MyItem[] mi = new MyItem[items];
            for(int i = 0; i < items; ++ i) {
                sc.next();
                mi[i] = new MyItem(sc.nextInt(), sc.nextInt());
            }
            Arrays.sort(mi);

            int leftMinutes = hours * 60;
            int res = 0;
            for(int i = 0; i < items && leftMinutes > 0; ++ i){
                if(mi[i].cap > leftMinutes){
                    res += (leftMinutes * mi[i].fun);
                    break;
                }
                else {
                    res += mi[i].cap * mi[i].fun;
                    leftMinutes -= mi[i].cap;
                }
            }

            System.out.println("Case #" + x + ": " + res);
        }
    }
}

