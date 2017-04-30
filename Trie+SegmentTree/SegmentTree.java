import java.util.*;

public class Test {

    //The following code is reference from
    //http://www.geeksforgeeks.org/lazy-propagation-in-segment-tree/
    private static class SegmentTree {
        int tree[];
        int lazy[];

        SegmentTree(int sz){
            tree = new int[sz];
            lazy = new int[sz];
        }

        void rangeUpdate(int r, int rL, int rR, int uL, int uR, int addV) {
            if (lazy[r] != 0) {
                tree[r] += (rR - rL + 1) * lazy[r];
                if (rL != rR) {
                    lazy[r * 2 + 1] += lazy[r];
                    lazy[r * 2 + 2] += lazy[r];
                }
                lazy[r] = 0;
            }

            if (rL > rR || rL > uR || rR < uL)
                return;

            if (rL >= uL && rR <= uR) {
                tree[r] += (rR - rL + 1) * addV;
                if (rL != rR) {
                    lazy[r * 2 + 1] += addV;
                    lazy[r * 2 + 2] += addV;
                }
                return;
            }

            int mid = (rL + rR) / 2;
            rangeUpdate(r * 2 + 1, rL, mid, uL, uR, addV);
            rangeUpdate(r * 2 + 2, mid + 1, rR, uL, uR, addV);

            tree[r] = tree[r * 2 + 1] + tree[r * 2 + 2];
        }

        void rangeUpdate(int n, int uL, int uR, int addV) {
            rangeUpdate(0, 0, n - 1, uL, uR, addV);
        }

        int rangeQuery(int r, int rL, int rR, int qL, int qR) {
            if (lazy[r] != 0) {
                tree[r] += (rR - rL + 1) * lazy[r];

                if (rL != rR) {
                    lazy[r * 2 + 1] += lazy[r];
                    lazy[r * 2 + 2] += lazy[r];
                }
                lazy[r] = 0;
            }

            if (rL > rR || rL > qR || rR < qL)
                return 0;

            if (rL >= qL && rR <= qR)
                return tree[r];

            int mid = (rL + rR) / 2;
            return rangeQuery(2 * r + 1, rL, mid, qL, qR) +
                    rangeQuery(2 * r + 2, mid + 1, rR, qL, qR);
        }

        int rangeQuery(int n, int qL, int qR) {
            return rangeQuery(0, 0, n - 1, qL, qR);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int sz = sc.nextInt(); int queries = sc.nextInt();
            SegmentTree lst = new SegmentTree(sz << 2);
            long res = 0;
            for(int i = 0; i < queries; ++ i){
                char c = sc.next().charAt(0);
                if(c == 'q'){
                    int q = sc.nextInt();
                    res += lst.rangeQuery(sz, q - 1, q - 1);
                }
                else {
                    lst.rangeUpdate(sz, sc.nextInt() - 1, sc.nextInt() - 1, sc.nextInt());
                }
            }
            System.out.println("Case #" + x + ": " + res % 1000000007);
        }
    }
}

