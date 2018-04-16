import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;
        public FastReader()
        {
            br = new BufferedReader(new
                     InputStreamReader(System.in));
        }
        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        int nextInt()
        {
            return Integer.parseInt(next());
        }
        long nextLong()
        {
            return Long.parseLong(next());
        }
        double nextDouble()
        {
            return Double.parseDouble(next());
        }
        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
  
    static class segTree{
        int segsum,bestsum,prefixsum,suffixsum;
    }

    static segTree tree[];
    static int a[];
    static segTree treeDummy;
    
    public static void buildSegTree(int node,int start,int end){
        if(start==end){
            tree[node].bestsum=a[start];
            tree[node].prefixsum=a[start];
            tree[node].suffixsum=a[start];
            tree[node].segsum=a[start];
        }
        else{
            int mid=(start+end)>>1;
            int leftNode = node<<1;
            int righNode = leftNode+1;
            tree[leftNode] = new segTree();
            tree[righNode] = new segTree();
            buildSegTree(leftNode,start,mid);
            buildSegTree(righNode,mid+1,end);
            merge(tree[node],tree[leftNode],tree[righNode]);
        }
    }
    
    public static void merge(segTree node,segTree left,segTree right){
        node.segsum=left.segsum+right.segsum;
        node.prefixsum=Math.max(left.prefixsum,left.segsum+right.prefixsum);
        node.suffixsum=Math.max(right.suffixsum,right.segsum+left.suffixsum);
        node.bestsum=Math.max(left.bestsum,Math.max(right.bestsum,left.suffixsum+right.prefixsum));
    }
    
    public static segTree querySegTree(int node,int leftNode,int rightNode,int l,int r){
        if(r<leftNode||l>rightNode){
           return treeDummy;
        }
        if(l<=leftNode&&r>=rightNode){
           return tree[node]; 
        }
        else{
            int mid = (leftNode+rightNode)>>1;
            int left = node<<1;
            int rigt = left+1;
            if(r<=mid){
                return querySegTree(left, leftNode,mid, l, r);
            }
            else if(l>mid){
                return querySegTree(rigt,mid+1, rightNode, l, r);
            }
            else{
                segTree leftChild=new segTree();
                segTree rightChild=new segTree();
                leftChild = querySegTree(left, leftNode,mid, l, r);
                rightChild = querySegTree(rigt, mid+1, rightNode, l, r);
                merge(tree[node],leftChild,rightChild);
            }
            return tree[node];
        }
    }
    
    public static void getDummy(){
        treeDummy = new segTree();
        treeDummy.bestsum=Integer.MIN_VALUE;
        treeDummy.prefixsum=Integer.MIN_VALUE;
        treeDummy.segsum=Integer.MIN_VALUE;
        treeDummy.suffixsum=Integer.MIN_VALUE;
    }
    
    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        int n=sc.nextInt();
        a = new int[n+1];
        for(int i=1;i<=n;i++){
            a[i] = sc.nextInt();
        }
        tree = new segTree[(4*n)+1];
        tree[1]=new segTree();
        getDummy();
        buildSegTree(1,1,n);
        int m = sc.nextInt();
        while(m-- >0){
            int x = sc.nextInt(),y = sc.nextInt();
            segTree res=new segTree();
            res=querySegTree(1, 1, n, x, y);
            System.out.println(res.bestsum);
        }
    }
}
