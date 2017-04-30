import java.util.*;

public class Test {

    private static class Vector3D{
        double x; double y; double z;
        Vector3D(double x, double y, double z){
            this.x = x; this.y = y; this.z = z;
        }
    }

    private static double getDeterminant(Vector3D a, Vector3D b, Vector3D c){
        double area = 0;
        area += (a.x * (b.y - c.y));
        area -= (b.x * (a.y - c.y));
        area += (c.x * (a.y - b.y));
        return area;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            int n = sc.nextInt();
            Vector3D[] points = new Vector3D[n];
            for(int i = 0; i < n; ++ i){
                points[i] = new Vector3D(sc.nextDouble(), sc.nextDouble(), 1.0);
            }
            double res = 0.0;
            for(int i = 1; i < n - 1; ++ i){
                res += getDeterminant(points[0], points[i], points[i + 1]);
            }
            res = Math.abs(res) / 2;

            System.out.println("Case #" + x + ": " + res);
        }
    }
}

