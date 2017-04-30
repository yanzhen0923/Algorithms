import java.util.*;

public class Test {

    private static class PointInPolygon {

        private static final int INFINITY = 0x3ffff;
        private static double eps = 0.00001;
        private static double yOffset = 666;


        private static boolean equalsDouble(double a, double b) {
             return Math.abs(a - b) < eps;
        }

        private static boolean largerThanDouble(double a, double b) {
            return a - b > eps;
        }

        private static boolean largerThanOrEqualTo(double a, double b){
            return  equalsDouble(a, b) || a - b > eps;
        }

        private static class PointF {
            double x, y;
            PointF(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        private static int orientationF(PointF left, PointF mid, PointF right) {
            double val = (mid.y - left.y) * (right.x - mid.x) - (mid.x - left.x) * (right.y - mid.y);
            if (equalsDouble(val, 0)) {
                return 0;
            }
            return largerThanDouble(val, 0) ? 1 : 2;
        }

        private static boolean onSegment(PointF left, PointF mid, PointF right)
        {
            boolean c1 = largerThanOrEqualTo(Math.max(left.x, right.x), mid.x);
            boolean c2 = largerThanOrEqualTo(mid.x, Math.min(left.x, right.x));
            boolean c3 = largerThanOrEqualTo(Math.max(left.y, right.y), mid.y);
            boolean c4 = largerThanOrEqualTo(mid.y, Math.max(left.y, right.y));
            return c1 && c2 && c3 && c4;
        }

        private static boolean doIntersect(PointF p1, PointF q1, PointF p2, PointF q2) {
            int o1 = orientationF(p1, q1, p2);
            int o2 = orientationF(p1, q1, q2);
            int o3 = orientationF(p2, q2, p1);
            int o4 = orientationF(p2, q2, q1);
            if(o1 != o2 && o3 != o4) {
                return true;
            }

            if (o1 == 0 && onSegment(p1, p2, q1)) {
                return true;
            }
            if (o2 == 0 && onSegment(p1, q2, q1)){
                return true;
            }
            if (o3 == 0 && onSegment(p2, p1, q2)){
                return true;
            }
            if (o4 == 0 && onSegment(p2, q1, q2)){
                return true;
            }
            return false;
        }

        private static boolean inPolygon(PointF[] points, PointF p) {
            PointF remote = new PointF(INFINITY, p.y + yOffset);
            int resCount = 0;
            for (int i = 0; i < points.length; i += 2) {
                if (doIntersect(points[i], points[i + 1], p, remote)) {
                    if (orientationF(points[i], p, points[i + 1]) == 0) {
                        return onSegment(points[i], p, points[i + 1]);
                    }
                    ++resCount;
                }
            }
            return (resCount & 1) == 1;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            PointInPolygon.PointF tgt = new PointInPolygon.PointF(sc.nextInt(), sc.nextInt());


            int cnt = sc.nextInt();
            PointInPolygon.PointF[] points = new PointInPolygon.PointF[cnt * 2];

            for (int i = 0; i < cnt * 2; i += 2) {
                points[i] = new PointInPolygon.PointF(sc.nextInt(), sc.nextInt());
                points[i + 1] = new PointInPolygon.PointF(sc.nextInt(), sc.nextInt());
            }
            String ans = PointInPolygon.inPolygon(points, tgt) ? "jackpot" : "too bad";
            System.out.println("Case #" + x + ": " + ans);
        }
    }
}

