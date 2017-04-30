import java.util.*;

public class Test {

    private static class Point2D{
        double x; double y;
        Point2D(double x, double y){
            this.x = x; this.y = y;
        }
    }

    private static class Vector3D{
        double x; double y; double z;
        Vector3D(double x, double y, double z){
            this.x = x; this.y = y; this.z = z;
        }

        Vector3D normalize(){
            if(z == 0){
                return null;
            }
            x /= z; y /= z; z = 1.0;
            return this;
        }

        static Vector3D reverse(Vector3D v){
            if(v.z != 0){
                return null;
            }
            return new Vector3D(-v.y, v.x, 0);
        }
    }

    private static Vector3D getCrossProduct(Vector3D v1, Vector3D v2){
        return new Vector3D(v1.y * v2.z - v1.z * v2.y, v1.x * v2.z - v1.z * v2.x, v1.x * v2.y - v1.y * v2.x);
    }

    private static Vector3D INFINITY = new Vector3D(0, 0, 1);

    private static Vector3D getVertical(Vector3D l, Vector3D p){
        Vector3D a = getCrossProduct(l, INFINITY);
        return getCrossProduct(p, Vector3D.reverse(a));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for (int x = 1; x <= cases; ++x) {
            double x1 = sc.nextDouble();
            double y1 = sc.nextDouble();
            double x2 = sc.nextDouble();
            double y2 = sc.nextDouble();
            double x3 = sc.nextDouble();
            double y3 = sc.nextDouble();

            Vector3D p1 = new Vector3D(x1, y1, 1);
            Vector3D p2 = new Vector3D(x2, y2, 1);
            Vector3D p3 = new Vector3D(x3, y3, 1);

            Vector3D l12 = getCrossProduct(p1, p2);
            Vector3D l23 = getCrossProduct(p2, p3);

            Point2D centroid = new Point2D((x1 + x2 + x3) / 3, (y1 + y2 + y3) / 3);


            Vector3D lineProjectedTol12 = getVertical(l12, p3);
            Vector3D lineProjectedTol23 = getVertical(l23, p1);

            Vector3D orthoCenter = getCrossProduct(lineProjectedTol12, lineProjectedTol23).normalize();


            Vector3D mid12 = new Vector3D((x1 + x2) / 2, (y1 + y2) / 2, 1.0);
            Vector3D mid23 = new Vector3D((x2 + x3) / 2, (y2 + y3) / 2, 1.0);

            Vector3D l12Vertical = getVertical(l12, mid12);
            Vector3D l23Vertical = getVertical(l23, mid23);

            Vector3D cirmcumCenter = getCrossProduct(l12Vertical, l23Vertical).normalize();

            System.out.println("Case #" + x + ": ");
            System.out.println(centroid.x + " " + centroid.y);
            System.out.println(orthoCenter.x + " " + orthoCenter.y);
            System.out.println(cirmcumCenter.x + " " + cirmcumCenter.y);

        }
    }
}

