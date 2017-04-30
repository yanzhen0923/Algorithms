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

        void rangeUpdate(int r, int rL, int rR, int qL,
                         int qR, int max) {
            if (lazy[r] != 0) {
                tree[r] = Math.max(tree[r], lazy[r]);

                if (rL != rR) {
                    lazy[r * 2 + 1] = Math.max(lazy[r * 2 + 1], lazy[r]);
                    lazy[r * 2 + 2] = Math.max(lazy[r * 2 + 2], lazy[r]);
                }
                lazy[r] = 0;
            }

            if (rL > rR || rL > qR || rR < qL)
                return;

            if (rL >= qL && rR <= qR) {
                tree[r] = Math.max(tree[r], max);

                if (rL != rR) {
                    lazy[r * 2 + 1] = Math.max(lazy[r * 2 + 1], max);
                    lazy[r * 2 + 2] = Math.max(lazy[r * 2 + 2], max);
                }
                return;
            }

            int mid = (rL + rR) / 2;
            rangeUpdate(r * 2 + 1, rL, mid, qL, qR, max);
            rangeUpdate(r * 2 + 2, mid + 1, rR, qL, qR, max);

            tree[r] = Math.max(tree[r * 2 + 1], tree[r * 2 + 2]);
        }

        void rangeUpdate(int n, int qL, int qR, int max) {
            rangeUpdate(0, 0, n - 1, qL, qR, max);
        }

        int rangeMax(int r, int rL, int rR, int qL, int qR) {
            if (lazy[r] != 0) {
                tree[r] = Math.max(tree[r], lazy[r]);

                if (rL != rR) {
                    lazy[r * 2 + 1] = Math.max(lazy[r * 2 + 1], lazy[r]);
                    lazy[r * 2 + 2] = Math.max(lazy[r * 2 + 2], lazy[r]);
                }
                lazy[r] = 0;
            }

            if (rL > rR || rL > qR || rR < qL)
                return 0;

            if (rL >= qL && rR <= qR)
                return tree[r];

            int mid = (rL + rR) / 2;
            return Math.max(rangeMax(2 * r + 1, rL, mid, qL, qR),
                    rangeMax(2 * r + 2, mid + 1, rR, qL, qR));
        }

        int rangeMax(int n, int qs, int qe) {
            return rangeMax(0, 0, n - 1, qs, qe);
        }
    }

        public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int cases = sc.nextInt();
        for(int x = 1; x <= cases; ++ x) {
            int sz = sc.nextInt(); int blocks = sc.nextInt();
            SegmentTree lst = new SegmentTree(sz << 2);
            System.out.print("Case #" + x + ": ");
            for(int i = 0; i < blocks; ++ i){
                int width = sc.nextInt();
                int height = sc.nextInt();
                int offset = sc.nextInt();
                int offsetR = offset + width - 1;
                int qHeight = lst.rangeMax(sz, offset, offsetR);
                int newHeight = qHeight + height;
                lst.rangeUpdate(sz, offset, offsetR, newHeight);
                int ans = lst.rangeMax(sz, 0, sz - 1);
                System.out.print(ans + " ");
            }
            System.out.println();
        }
    }
}

