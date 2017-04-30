import java.util.*;

public class Test {

    private static double eps = 0.000001;

    private static class PointF {
        double x, y;

        PointF(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof PointF)) {
                return false;
            }
            PointF cmp = (PointF) o;
            return equalsDouble(this.x, cmp.x) && equalsDouble(this.y, cmp.y);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    private static class Ray {
        int A, B, C;
        PointF start;
        Vec dir;

        Ray(int A, int B, int C, PointF start, Vec dir) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.start = start;
            this.dir = dir;
        }
    }

    private static class Vec {
        double xDir;
        double yDir;

        Vec(double x, double y) {
            xDir = x;
            yDir = y;
        }
    }

    private static Vec getVec(PointF p1, PointF p2) {
        return new Vec(p2.x - p1.x, p2.y - p1.y);
    }

    private static boolean sameSign(double a, double b) {
        if (equalsDouble(a, 0)) {
            return equalsDouble(b, 0);
        }
        if (a > 0) {
            return b > 0;
        }
        return b < 0;
    }

    private static boolean sameDir(Vec v1, Vec v2) {
        return sameSign(v1.xDir, v2.xDir) && sameSign(v1.yDir, v2.yDir);
    }

    private static boolean equalsDouble(double a, double b) {
        return a == b || Math.abs(a - b) < eps;
    }

    private static double getDouble(int v) {
        return v;
    }

    private static boolean sameLineInParallel(Ray r1, Ray r2) {
        if ((r1.C == 0 && r2.C != 0) || (r1.C != 0 && r2.C == 0)) {
            return false;
        }
        if (r1.C == 0) {
            return true;
        }
        if (r1.A != 0) {
            return equalsDouble(getDouble(r1.A) / getDouble(r2.A), getDouble(r1.C) / getDouble(r2.C));
        }
        return equalsDouble(getDouble(r1.B) / getDouble(r2.B), getDouble(r1.C) / getDouble(r2.C));
    }

    private static PointF getIntersectPoint(Ray r1, Ray r2, double denominator) {
        double xNumerator = r1.C * r2.B - r1.B * r2.C;
        double yNumerator = r1.A * r2.C - r1.C * r2.A;
        double xValue = xNumerator / denominator;
        double yValue = yNumerator / denominator;
        PointF pf = new PointF(xValue, yValue);
        if (!isValidPoint(pf)) {
            return null;
        }
        if (onTheRay(r1, pf) && onTheRay(r2, pf)) {
            return pf;
        }
        return null;
    }

    private static PointF[] getIntersectionSegment(Ray r1, Ray r2) {
        if (sameLineInParallel(r1, r2)) {
            if (r1.start.equals(r2.start)) {
                return new PointF[] {r1.start};
            }
            if (sameDir(r1.dir, r2.dir)) {
                PointF pf = sameDir(r1.dir, getVec(r1.start, r2.start)) ? r2.start : r1.start;
                return new PointF[]{pf};
            } else {
                if (sameDir(r1.dir, getVec(r1.start, r2.start))) {
                    return new PointF[] {r1.start, r2.start};
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    private static int getDenominator(Ray r1, Ray r2) {
        return r1.B * r2.A - r1.A * r2.B;
    }

    private static Ray getRay(int x1, int y1, int x2, int y2) {
        PointF st = new PointF(x1, y1);
        return new Ray(y1 - y2, x2 - x1, x1 * y2 - x2 * y1, st, getVec(st, new PointF(x2, y2)));
    }

    private static boolean onTheRay(Ray l, PointF p) {
        return l.start.equals(p) ||
                (equalsDouble(0, l.A * p.x + l.B * p.y + l.C)
                        && sameDir(l.dir, getVec(l.start, p)));
    }

    private static boolean isValidPoint(PointF pf) {
        return pf.x >= 1 && pf.x <= 1000 && pf.y >= 1 && pf.y <= 1000;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            Random rd = new Random();
            int n = sc.nextInt();
            int k = sc.nextInt();
            int rMin = 0;
            int rMax = n - 1;
            int rParam = rMax - rMin + 1;
            Ray[] rays = new Ray[n];
            for (int i = 0; i < n; ++i) {
                rays[i] = getRay(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
            }

            int interCount = 0;
            int targetInter = 100;
            Set<PointF> sp = new HashSet<>();
            while (interCount < targetInter) {
                int i, j;
                do {
                    i = rd.nextInt(rParam) + rMin;
                    j = rd.nextInt(rParam) + rMin;
                } while (i == j);

                int denominator = getDenominator(rays[i], rays[j]);
                if (denominator != 0) {
                    PointF pf = getIntersectPoint(rays[i], rays[j], denominator);
                    if (pf != null) {
                        sp.add(pf);
                    }
                } else {
                    PointF[] pfs = getIntersectionSegment(rays[i], rays[j]);
                    if (pfs != null) {
                        for(PointF pf : pfs){
                            sp.add(pf);
                        }
                    }
                }
                ++ interCount;
            }

            PointF res = null;

            for(PointF pf : sp){
                int cnt = 0;
                for(Ray r : rays){
                    if(onTheRay(r, pf)){
                        ++ cnt;
                    }
                }
                if(cnt >= k){
                    res = pf;
                    break;
                }
            }

            System.out.print("Case #" + x + ": ");
            if(res == null){
                System.out.println("no");
            }
            else {
                System.out.println(res.x + " " + res.y);
            }
        }
    }
}

