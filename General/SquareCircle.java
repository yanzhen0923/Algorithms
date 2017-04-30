import java.util.*;

public class Test {

    private static class Point{
        double x, y;
        Point(double x, double y){
            this.x = x; this.y = y;
        }

    }

    private static class Solve{
        Point[] circles; Point unique;
        double INFINITY = 99999999.0;
        Solve(Point[] c, Point u){
            circles = c; unique = u;
        }

        double getSquare(Point p1, Point p2){
            double d1 = p1.x - p2.x;
            double d2 = p1.y - p2.y;
            return d1 * d1 + d2 * d2;
        }

        double getArea(){
            if(circles.length == 1){
                return Math.PI * getSquare(circles[0], unique);
            }

            double minSquareDistCircles = INFINITY;
            for (int i = 0; i < circles.length; ++i) {
                for (int j = i + 1; j < circles.length; ++j) {
                    double dist = getSquare(circles[i], circles[j]);
                    if (dist < minSquareDistCircles) {
                        minSquareDistCircles = dist;
                    }
                }
            }

            double minSquareDistUnique = INFINITY;
            for(Point p : circles) {
                double dist = getSquare(unique, p);
                if (dist < minSquareDistUnique) {
                    minSquareDistUnique = dist;
                }
            }

            if(minSquareDistUnique < minSquareDistCircles / 4){
                minSquareDistCircles = minSquareDistUnique * 4;
            }

            double a1 = ((Math.PI * minSquareDistCircles) / 4) * circles.length;
            double uniqueRadius = Math.sqrt(minSquareDistUnique) - (Math.sqrt(minSquareDistCircles) / 2);
            a1 += (Math.PI * uniqueRadius * uniqueRadius);

            double a2 = Math.PI * minSquareDistUnique;

            return Math.max(a1, a2);

        }
    }


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x){
            int n = sc.nextInt();
            Point unique = new Point(sc.nextDouble(), sc.nextDouble());
            Point[] circles = new Point[n - 1];
            for(int i = 1; i < n; ++ i){
                circles[i - 1] = new Point(sc.nextDouble(), sc.nextDouble());
            }
            Solve s = new Solve(circles, unique);
            System.out.println("Case #" + x + ": " + s.getArea());
        }
    }
}

