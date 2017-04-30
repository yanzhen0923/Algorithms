import java.util.*;

public class Test {

    private static class Fraction{
        long nominator;
        long denominator;
        Fraction(long nominator, long denominator){
            this.nominator = nominator; this.denominator = denominator;
        }

        Fraction add(Fraction f){
            long tmpNominator = this.nominator * f.denominator + f.nominator * this.denominator;
            long tmpDenominator = this.denominator * f.denominator;
            long gcd = GCD(Math.max(tmpDenominator, tmpNominator), Math.min(tmpDenominator, tmpNominator));
            return new Fraction(tmpNominator / gcd, tmpDenominator / gcd);
        }

        Fraction minus(Fraction f){
            long tmpNominator = this.nominator * f.denominator - f.nominator * this.denominator;
            long tmpDenominator = this.denominator * f.denominator;
            long gcd = GCD(Math.max(tmpDenominator, tmpNominator), Math.min(tmpDenominator, tmpNominator));
            return new Fraction(tmpNominator / gcd, tmpDenominator / gcd);
        }

        Fraction multiply(Fraction f){
            long tmpNominator = this.nominator * f.nominator;
            long tmpDenominator =this.denominator * f.denominator;
            long gcd = GCD(Math.max(tmpDenominator, tmpNominator), Math.min(tmpDenominator, tmpNominator));
            return new Fraction(tmpNominator / gcd, tmpDenominator / gcd);
        }

        Fraction divide(int m){
            long tmpNominator = this.nominator;
            long tmpDenominator =this.denominator * m;
            long gcd = GCD(Math.max(tmpDenominator, tmpNominator), Math.min(tmpDenominator, tmpNominator));
            return new Fraction(tmpNominator / gcd, tmpDenominator / gcd);
        }

        Fraction divide(Fraction f){
            long tmpNominator = this.nominator * f.denominator;
            long tmpDenominator =this.denominator * f.nominator;
            long gcd = GCD(Math.max(tmpDenominator, tmpNominator), Math.min(tmpDenominator, tmpNominator));
            return new Fraction(tmpNominator / gcd, tmpDenominator / gcd);
        }

    }

    private static class Point{
        Fraction x, y;
        Point(Fraction x, Fraction y){
            this.x = x; this.y = y;
        }
    }

    private static Fraction getArea(Point a, Point b, Point c){
        Fraction f = new Fraction(0, 1);
        f = f.add(a.x.multiply(b.y.minus(c.y)));
        f = f.minus(b.x.multiply(a.y.minus(c.y)));
        f = f.add(c.x.multiply(a.y.minus(b.y)));
        return new Fraction(Math.abs(f.nominator), Math.abs(f.denominator));
    }

    private static Point getPoint(Point start, Point end, int m){
        Fraction xStep = end.x.minus(start.x).divide(m);
        Fraction yStep = end.y.minus(start.y).divide(m);
        return new Point(start.x.add(xStep), start.y.add(yStep));
    }

    private static long GCD(long a, long b) {
        if (b == 0) {
            return a;
        }
        return GCD(b, a % b);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            Point[] points = new Point[n];
            for(int i = 0; i < n; ++ i){
                points[i] = new Point(new Fraction(sc.nextLong(), 1), new Fraction(sc.nextLong(), 1));
            }

            Fraction wholeArea = new Fraction(0, 1);
            for(int i = 1; i < n - 1; ++ i){
                wholeArea = wholeArea.add(getArea(points[0], points[i], points[i + 1]));
            }

            Fraction subArea = new Fraction(0, 1);
            for(int i = 0; i < n; ++ i){
                Point p1 = getPoint(points[i], points[(i - 1 + n) % n], m);
                Point p2 = getPoint(points[i], points[(i + 1 + n) % n], m);
                subArea = subArea.add(getArea(p1, points[i], p2));
            }

            Fraction ans = subArea.divide(wholeArea);
            ans.nominator = Math.abs(ans.nominator);
            ans.denominator = Math.abs(ans.denominator);
            long gcd = GCD(ans.denominator, ans.nominator);
            ans.nominator /= gcd;
            ans.denominator /= gcd;
            System.out.println("Case #" + x + ": " + ans.nominator + "/" + ans.denominator);

        }
    }
}

