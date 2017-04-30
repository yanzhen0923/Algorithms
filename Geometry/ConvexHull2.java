import java.util.*;

public class Test {

    private static class ConvexHull{

        private static class Point{
            int x, y;
            Point(int x, int y){
                this.x = x; this.y = y;
            }
        }

        private static int orientation(Point left, Point mid, Point right) {
            int val = (mid.y - left.y) * (right.x - mid.x) - (mid.x - left.x) * (right.y - mid.y);
            if (val == 0){
                return 0;
            }
            return (val > 0) ? 1 : 2;
        }

        private static Point start;

        private static int distance(Point p1, Point p2) {
            return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
        }

        private static Point getSecond(Stack<Point> s){
            Point top = s.pop();
            Point ret = s.peek();
            s.push(top);
            return ret;
        }

        private static class PolarAngleComp implements Comparator<Point>{
            @Override
            public int compare(Point o1, Point o2) {
                int orient = orientation(start, o1, o2);
                if(orient != 0){
                    return orient == 2 ? -1 : 1;
                }
                return distance(start, o1) < distance(start, o2) ? -1 : 1;
            }
        }

        private static class XYComp implements Comparator<Point>{
            @Override
            public int compare(Point o1, Point o2) {
                if(o1.x - o2.x != 0){
                    return o1.x - o2.x;
                }
                return o1.y - o2.y;
            }
        }

        private static Point[] allAreCollinear(Point[] points){
            if(points.length < 3){
                return points;
            }
            int a = points[0].x; int b = points[0].y;
            int m = points[1].x; int n = points[1].y;
            for(int i = 2;i < points.length; ++ i){
                int x = points[i].x; int y = points[i].y;
                if((n - b) * (x - m) != (y - n) * (m - a)) {
                    return null;
                }
            }
            int lowIndex = 0;
            for (int i = 1; i < points.length; ++i) {
                if ((points[i].y < points[lowIndex].y) ||
                        (points[i].y == points[lowIndex].y && points[i].x < points[lowIndex].x)) {
                    lowIndex = i;
                }
            }
            int highIndex = 0;
            for (int i = 1; i < points.length; ++i) {
                if ((points[i].y > points[highIndex].y) ||
                        (points[i].y == points[highIndex].y && points[i].x > points[highIndex].x)) {
                    highIndex = i;
                }
            }
            return new Point[]{points[lowIndex], points[highIndex]};
        }

        private static Point[] getConvexHull(Point[] points) {
            if (points.length < 3) {
                Arrays.sort(points, new XYComp());
                return points;
            }

            int index = 0;
            for (int i = 1; i < points.length; ++i) {
                if ((points[i].y < points[index].y) ||
                        (points[i].y == points[index].y && points[i].x < points[index].x)) {
                    index = i;
                }
            }
            start = points[index];

            Point[] candidates = new Point[points.length - 1];
            for (int i = 0, j = 0; i < points.length; ++i) {
                if (i != index) {
                    candidates[j++] = points[i];
                }
            }
            Arrays.sort(candidates, new PolarAngleComp());

            int newSZ = 0;
            for (int i = 0; i < candidates.length; ++i) {
                while (i < candidates.length - 1) {
                    if (orientation(start, candidates[i], candidates[i + 1]) == 0) {
                        ++i;
                    } else {
                        break;
                    }
                }
                candidates[newSZ++] = candidates[i];
            }

            Stack<Point> s = new Stack<>();
            s.push(start);
            s.push(candidates[0]);
            s.push(candidates[1]);

            for (int i = 2; i < newSZ; ++i) {
                while (orientation(getSecond(s), s.peek(), candidates[i]) != 2) {
                    if (!s.empty()) {
                        s.pop();
                    }
                }
                s.push(candidates[i]);
            }

            Point[] ans = new Point[s.size()];
            for (int i = 0; i < ans.length; ++i) {
                ans[i] = s.pop();
            }
            Arrays.sort(ans, new XYComp());

            return ans;
        }
    }

    private static class PointInPolygon {

        private static final int INFINITY = 0x3ffff;
        private static double eps = 0.00001;
        private static double yOffset = 666;


        private static boolean equalsDouble(double a, double b) {
             return a == b || Math.abs(a - b) < eps;
        }

        private static boolean largerThanDouble(double a, double b) {
            return a > b || a - b > eps;
        }

        private static boolean largerThanOrEqualTo(double a, double b){
            return  a >= b || equalsDouble(a, b) || a - b > eps;
        }

        private static class PointF {
            double x, y;
            PointF(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        private static PointF[] points;

        private static void setPoints(PointF[] pfs){
            points = pfs;
        }

        private static Range getRange(){
            double xMin = INFINITY; double yMin = INFINITY;
            double xMax = -INFINITY; double yMax= -INFINITY;
            for(PointF pf : points){
                if(largerThanDouble(xMin, pf.x)){
                    xMin = pf.x;
                }
                if(largerThanDouble(pf.x, xMax)){
                    xMax = pf.x;
                }
                if(largerThanDouble(yMin, pf.y)){
                    yMin = pf.y;
                }
                if(largerThanDouble(pf.y, yMax)){
                    yMax = pf.y;
                }
            }
            return new Range(
                    (int)Math.floor(xMax),
                    (int)Math.ceil(xMin),
                    (int)Math.floor(yMax),
                    (int)Math.ceil(yMin));
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
            boolean c4 = largerThanOrEqualTo(mid.y, Math.min(left.y, right.y));
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

        private static boolean inPolygon(PointF p) {
            PointF remote = new PointF(INFINITY, p.y + yOffset);
            int resCount = 0;
            for (int i = 0; i < points.length - 1; ++ i) {
                if (doIntersect(points[i], points[i + 1], p, remote)) {
                    if (orientationF(points[i], p, points[i + 1]) == 0) {
                        return onSegment(points[i], p, points[i + 1]);
                    }
                    ++resCount;
                }
            }
            if (doIntersect(points[points.length - 1], points[0], p, remote)) {
                if (orientationF(points[points.length - 1], p, points[0]) == 0) {
                    return onSegment(points[points.length - 1], p, points[0]);
                }
                ++resCount;
            }
            return (resCount & 1) == 1;
        }
    }

    private static class Range{
        int xMax, xMin, yMax, yMin;
        Range(int xMax, int xMin, int yMax, int yMin){
            this.xMax = xMax; this.xMin = xMin; this.yMax = yMax; this.yMin = yMin;
        }
    }

    private static void output(ConvexHull.Point[] cps){
        System.out.println(cps.length);
        for(ConvexHull.Point cp : cps){
            System.out.println(cp.x + " " + cp.y);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            int cnt = sc.nextInt();
            PointInPolygon.PointF[] pointsF = new PointInPolygon.PointF[cnt];
            for (int i = 0; i < cnt; ++ i) {
                pointsF[i] = new PointInPolygon.PointF(sc.nextDouble(), sc.nextDouble());
            }
            PointInPolygon.setPoints(pointsF);
            Range r = PointInPolygon.getRange();

            List<ConvexHull.Point> pointsArray = new ArrayList<>();
            for(int i = r.xMin; i <= r.xMax; ++ i){
                for(int j = r.yMin; j <= r.yMax; ++ j){
                    PointInPolygon.PointF pf = new PointInPolygon.PointF(i, j);
                    if(PointInPolygon.inPolygon(pf)){
                        pointsArray.add(new ConvexHull.Point(i, j));
                    }
                }
            }

            ConvexHull.Point[] points = new ConvexHull.Point[pointsArray.size()];
            pointsArray.toArray(points);

            System.out.println("Case #" + x + ": " );

            ConvexHull.Point[] co = ConvexHull.allAreCollinear(points);
            if(co != null){
                output(co);
                continue;
            }

            ConvexHull.Point[] res = ConvexHull.getConvexHull(points);
            output(res);
        }
    }
}

