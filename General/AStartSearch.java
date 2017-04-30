import java.util.*;

public class Test {

    private static class Vector3D{
        int x; int y; int z;
        Vector3D(int x, int y, int z){
            this.x = x; this.y = y; this.z = z;
        }

        static Vector3D crossProduct(Vector3D v1, Vector3D v2){
            return new Vector3D(v1.y * v2.z - v1.z * v2.y, v1.x * v2.z - v1.z * v2.x, v1.x * v2.y - v1.y * v2.x);
        }

        @Override
        public boolean equals(Object o){
            if(! (o instanceof Vector3D)){
                return false;
            }
            Vector3D v = (Vector3D)o;
            return v.x == this.x && v.y == this.y;
        }

        @Override
        public int hashCode(){
            return 0;
        }
    }

    private static class Obstacle{
        int x, y, w, h;
        Vector3D[] corners;
        Vector3D[] lines;
        Obstacle(int xx, int yy, int ww, int hh){
            x = xx; y = yy; w = ww; h = hh;
            corners = new Vector3D[4];
            lines = new Vector3D[4];
            //pUL
            corners[0] = new Vector3D(x, y, 1);
            //pUR
            corners[1] = new Vector3D(x + w, y, 1);
            //pBR
            corners[2] = new Vector3D(x + w, y + h, 1);
            //pBL
            corners[3] = new Vector3D(x, y + h, 1);

            //l01
            lines[0] = Vector3D.crossProduct(corners[0], corners[1]);
            //l12
            lines[1] = Vector3D.crossProduct(corners[1], corners[2]);
            //l23
            lines[2] = Vector3D.crossProduct(corners[2], corners[3]);
            //l30
            lines[3] = Vector3D.crossProduct(corners[3], corners[0]);
        }
    }

    private static class Solve{

        Obstacle[] obs;
        Vector3D s, t;
        Map<Vector3D, Integer> viMap;
        Map<Integer, Vector3D> ivMap;
        Integer beginId = -1;
        Integer parent[];

        Solve(Obstacle[] obs, Vector3D s, Vector3D t){
            this.s = s; this.t = t; this.obs = obs;
            viMap = new HashMap<>();
            ivMap = new HashMap<>();
            parent = new Integer[410];
        }


        private static class QElement implements Comparable<QElement>{
            Vector3D p;
            double distFromSource;
            double distToTarget;
            int parentId;

            QElement(Vector3D v3D, double fromSource, double toTarget, int parentId) {
                p = v3D;
                distFromSource = fromSource;
                distToTarget = toTarget;
                this.parentId = parentId;
            }

            @Override
            public int compareTo(QElement o) {
                if(this.distFromSource + this.distToTarget - o.distFromSource - o.distToTarget < 0) {
                    return -1;
                }
                return 1;
            }
        }

        double getDistance(Vector3D p1, Vector3D p2){
            double xsq = (p1.x - p2.x) * (p1.x - p2.x) * 1.0;
            double ysq = (p1.y - p2.y) * (p1.y - p2.y) * 1.0;
            return Math.sqrt(xsq + ysq);
        }

        Integer getId(Vector3D v3D){
            Integer i = viMap.get(v3D);
            if(i == null){
                viMap.put(v3D, ++ beginId);
                ivMap.put(beginId, v3D);
                return beginId;
            }
            return i;
        }

        Vector3D getVector(Integer i){
            return ivMap.get(i);
        }

        boolean intersect(Obstacle o, Vector3D from, Vector3D to){
            Vector3D cmp = Vector3D.crossProduct(from, to);
            Set<Vector3D> inters = new HashSet<>();
            for(int i = 0; i < o.lines.length; ++ i) {
                Vector3D point = Vector3D.crossProduct(cmp, o.lines[i]);
                if(point.z == 0){
                    continue;
                }
                double interX = (point.x * 1.0) / point.z;
                double interY = (point.y * 1.0) / point.z;
                Vector3D lBegin = o.corners[i];
                Vector3D lEnd = o.corners[(i + 1) % 4];
                if(Math.min(from.x, to.x) <= interX && interX <= Math.max(from.x, to.x)
                        && Math.min(from.y, to.y) <= interY && interY <= Math.max(from.y, to.y)
                        && Math.min(lBegin.x, lEnd.x) <= interX && interX <= Math.max(lBegin.x, lEnd.x)
                        && Math.min(lBegin.y, lEnd.y) <= interY && interY <= Math.max(lBegin.y, lEnd.y)){
                    boolean addMark = false;
                    for(Vector3D p : o.corners) {
                        double checkX = p.x;
                        double checkY = p.y;
                        if(checkX == interX && checkY == interY) {
                            inters.add(p);
                            addMark = true;
                        }
                    }
                    if(!addMark)
                        return true;
                }
            }
            if(inters.size() != 2){
                return false;
            }
            else {
                Vector3D[] checks = new Vector3D[2];
                inters.toArray(checks);
                if(checks[0].x == checks[1].x || checks[0].y == checks[1].y){
                    return false;
                }
                return true;
            }
        }

        boolean  isValidPath(Vector3D from, Vector3D to){
            int cmpXMax = Math.max(from.x, to.x);
            int cmpXMin = Math.min(from.x, to.x);
            int cmpYMax = Math.max(from.y, to.y);
            int cmpYMin = Math.min(from.y, to.y);
            for(Obstacle o : obs){
                int xMin = o.x; int yMin = o.y;
                int xMax = o.x + o.w; int yMax = o.y + o.h;
                if(cmpXMin <= xMin && xMin <= cmpXMax
                        || cmpXMin <= xMax && xMax <= cmpXMax
                        || cmpYMin <= yMin && yMin <= cmpYMax
                        || cmpYMin <= yMax && yMax <= cmpYMax){
                    if(intersect(o, from, to)){
                        return false;
                    }
                }
            }
            return true;
        }

        List<Vector3D> getCandidates(Vector3D from){
            List<Vector3D> lv = new ArrayList<>();
            for(Obstacle obstacle : obs){
                for(Vector3D v3D : obstacle.corners){
                    if(isValidPath(from, v3D) && !v3D.equals(from)) {
                        lv.add(v3D);
                    }
                }
            }
            if(isValidPath(from, t)){
                lv.add(t);
            }
            return lv;
        }

        List<Vector3D> constructPath(){
            Vector3D cur = t;
            List<Vector3D> lv = new ArrayList<>();
            while (!parent[getId(cur)].equals(getId(cur))){
                lv.add(new Vector3D(cur.x, cur.y, cur.z));
                cur = getVector(parent[getId(cur)]);
            }
            lv.add(s);
            Collections.reverse(lv);
            return lv;
        }

        List<Vector3D> AStarSearch(){
            Arrays.fill(parent, 0);
            Queue<QElement> q = new PriorityQueue<>();
            Map<Vector3D, Double> mvd = new HashMap<>();
            parent[getId(s)] = getId(s);
            QElement qe = new QElement(s, 0, getDistance(s, t), getId(s));
            q.add(qe);
            mvd.put(qe.p, qe.distFromSource + qe.distToTarget);

            while (!q.isEmpty()){
                QElement cur = q.remove();
                int curId = getId(cur.p);
                Double historyD = mvd.get(cur.p);
                if(historyD != null){
                    if(historyD < cur.distFromSource + cur.distToTarget){
                        continue;
                    }
                }
                parent[curId] = cur.parentId;
                if(cur.p.equals(t)){
                    return constructPath();
                }
                List<Vector3D> adj = getCandidates(cur.p);
                for(Vector3D v3D : adj){
                    Double distFrom = cur.distFromSource + getDistance(cur.p, v3D);
                    Double distTo = getDistance(v3D, t);
                    Double distSum = distFrom + distTo;
                    Double distHistoryOpen = mvd.get(v3D);
                    if(distHistoryOpen != null){
                        if(distHistoryOpen <= distSum){
                            continue;
                        }
                    }
                    QElement qElement = new QElement(v3D, distFrom, distTo, curId);
                    q.add(qElement);
                    mvd.put(qElement.p, distSum);
                }
                mvd.put(cur.p, cur.distFromSource + cur.distToTarget);
            }
            return null;
        }

    }



    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();
        for(int x = 1; x <= testCase; ++ x){
            int w = sc.nextInt();
            int h = sc.nextInt();
            int n = sc.nextInt();
            Obstacle[] obs = new Obstacle[n];
            for(int i = 0; i < n; ++ i){
                obs[i] = new Obstacle(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt());
            }
            Solve slv = new Solve(obs, new Vector3D(sc.nextInt(), sc.nextInt(), 1), new Vector3D(sc.nextInt(), sc.nextInt(), 1));
            List<Vector3D> res = slv.AStarSearch();
            System.out.print("Case #" + x + ": ");
            for(Vector3D v3D : res){
                System.out.print("(" + v3D.x + "," + v3D.y + ") ");
            }
            System.out.println();
        }
    }
}

