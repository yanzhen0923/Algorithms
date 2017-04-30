import java.util.*;

public class Test {

    //The following code is referenced from
    //http://www.geeksforgeeks.org/how-to-check-if-a-given-point-lies-inside-a-polygon/
    //and
    //http://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/
    private static class Point{
        long x, y;
        Point(long x, long y){
            this.x = x; this.y = y;
        }
    }

    private static int orientation(Point left, Point mid, Point right) {
        long val = (mid.y - left.y) * (right.x - mid.x) - (mid.x - left.x) * (right.y - mid.y);
        if (val == 0){
            return 0;
        }
        return (val > 0) ? 1 : 2;
    }

    private static class ConvexHull{

        private static Point start;

        private static long distance(Point p1, Point p2) {
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
                    return (int)(o1.x - o2.x);
                }
                return (int)(o1.y - o2.y);
            }
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

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int sz = sc.nextInt();
            Point[] points = new Point[sz];
            for(int i = 0; i < sz; ++ i){
                points[i] = new Point(sc.nextLong(), sc.nextLong());
                sc.nextInt(); sc.nextInt();
            }
            Point[] res = ConvexHull.getConvexHull(points);
            System.out.println("Case #" + x + ": ");
            System.out.println(res.length);
            for(Point p : res){
                System.out.println(p.x + " " + p.y);
            }
        }
    }
}

