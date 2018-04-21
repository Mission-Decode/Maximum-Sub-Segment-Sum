import java.io.*;
import java.util.*;
/*
* Segment tree programme
* Stores sum of intervals
* 0 --> Add value to an interval
* 1 --> Query interval for the total sum
* */

public class Main {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    static long mod = 1000000007;

    static FastReader in = new FastReader();

    static long[] segTree;
    static long[] lazyTree;
    static long[] a;

    public static void main(String[] args) {

        int t = in.nextInt();
        while (t-- > 0){
            int n = in.nextInt(),c = in.nextInt();
            segTree = new long[(4*n)+1];
            lazyTree = new long[(4*n)+1];
            a = new long[n+1];
            for (int i=1;i<=n;i++){
                a[i] = in.nextLong();
            }
            constructSegTree(1, n, 1);

            while (c-- > 0) {
                int queryType = in.nextInt();
                int queryLow = in.nextInt();
                int queryHigh = in.nextInt();
                long delta;
                if (queryType == 0) {
                    delta = in.nextLong();
                    updateSegTreeLazily(1, n, queryLow, queryHigh, 1, delta);
                } else {
                    long result = querySegTree(1, n, queryLow, queryHigh, 1);
                    System.out.println(result);
                }
            }
        }
    }

    //Query the tree for an interval, returns the sum of that interval.
    private static long querySegTree(int low, int high, int queryLow, int queryHigh, int pos) {
        if (low > high){
            return 0;
        }
        propogateUpdatesLazily(low, high, pos);

        if (queryLow > high || queryHigh < low){
            return 0;
        }
        if (queryLow <= low && queryHigh >= high){
            return segTree[pos];
        }
        int mid = (low + high)/2;
        long leftSum = querySegTree(low, mid, queryLow, queryHigh, 2*pos);
        long rightSum = querySegTree(mid+1, high, queryLow, queryHigh, 2*pos+1);
        return leftSum+rightSum;
    }

    private static void updateSegTreeLazily(int low, int high, int queryLow, int queryHigh, int pos, long delta) {
        //Border checks
        if (low > high){
            return;
        }
        //Before processing any node take updates
        propogateUpdatesLazily(low, high, pos);

        //No overlap
        if (queryLow > high || queryHigh < low){
            return;
        }
        //Total overlap add value to this node cumilative to adding the values to its child.
        //During any updates, mark lazy updates to child nodes.
        if (queryLow <= low && queryHigh >= high){
            segTree[pos] += delta*(high-low+1);
            if (low != high){
                lazyTree[2*pos] += delta;
                lazyTree[2*pos+1] += delta;
            }
            return;
        }
        //Partial overlap recurse on left subtree and right subtree.
        int mid = (low + high)/2;
        updateSegTreeLazily(low, mid, queryLow, queryHigh, 2*pos, delta);
        updateSegTreeLazily(mid+1, high, queryLow, queryHigh, 2*pos+1, delta);
        segTree[pos] = segTree[2*pos]+segTree[2*pos+1];
    }

    //Check for updates in any node, if so take updates to this node,
    //Propogate the update to the child nodes.
    private static void propogateUpdatesLazily(int low, int high, int pos) {
        if (lazyTree[pos] != 0){
            segTree[pos] += lazyTree[pos]*(high-low+1);
            if (low != high){
                lazyTree[2*pos] += lazyTree[pos];
                lazyTree[2*pos+1] += lazyTree[pos];
            }
            lazyTree[pos] = 0;
        }
    }

    private static void constructSegTree(int low, int high, int pos) {
        if (low == high){
            segTree[pos] = a[low];
            return;
        }
        int mid = (low + high)/2;
        constructSegTree(low,mid,2*pos);
        constructSegTree(mid+1, high, 2*pos+1);
        segTree[pos] = segTree[2*pos]+segTree[2*pos+1];
    }

}




