import java.util.*;

public class Test {
    private static class Point{
        double x, y;
        Point(double x, double y){
            this.x = x; this.y = y;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            int n = sc.nextInt();
            int d = sc.nextInt();
            int a = sc.nextInt();
            StringBuilder target = new StringBuilder(sc.next());
            Map<Character, StringBuilder> mss = new HashMap<>();
            for(int i = 0; i < n ;++ i){
                String tmp = sc.next();
                mss.put(tmp.charAt(0), new StringBuilder(tmp.substring(3)));
            }

            for(int i = 0; i < d; ++ i){
                StringBuilder generated = new StringBuilder();
                for(int j = 0; j < target.length(); ++ j){
                    StringBuilder tmp = mss.get(target.charAt(j));
                    if(tmp != null){
                        generated.append(tmp);
                    }
                    else {
                        generated.append(target.charAt(j));
                    }
                }
                target = generated;
            }
            System.out.println("Case #" + x + ": ");
            Point cur = new Point(0, 0);
            System.out.println(cur.x + " " + cur.y);
            int angle = 0;
            for(int i = 0; i < target.length(); ++ i) {
                char c = target.charAt(i);
                if (c == '+') {
                    angle += a;
                } else if (c == '-') {
                    angle -= a;
                } else {
                    cur.x += Math.cos(Math.toRadians(angle));
                    cur.y += Math.sin(Math.toRadians(angle));
                    System.out.println(cur.x + " " + cur.y);
                }

            }
        }
    }
}

