import java.io.*;
import java.util.*;


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

    public static void main(String[] args) {
        while (true){
            int budget = in.nextInt(), parties = in.nextInt();
            if (budget != 0 && parties != 0){
                int[][] dp = new int[parties+1][budget+1];
                int[] partyCost = new int[parties+1];
                int[] partyFun = new int[parties+1];
                for (int i=0;i<parties;i++){
                    partyCost[i]=in.nextInt();
                    partyFun[i]=in.nextInt();
                }
                for (int i=0;i<=parties;i++){
                    for (int j=0;j<=budget;j++){
                        if (i==0 || j==0){
                            dp[i][j] = 0;
                        }
                        else if (j < partyCost[i-1]){
                            dp[i][j] = dp[i-1][j];
                        }
                        else {
                            dp[i][j] = Math.max((partyFun[i-1]+dp[i-1][j-partyCost[i-1]]), dp[i-1][j]);
                        }
                    }
                }
                int totalFun = dp[parties][budget];
                int minCost = -1;
                for (int i=1;i<=budget;i++){
                    if (dp[parties][i] == totalFun){
                        minCost = i;
                        break;
                    }
                }
                System.out.println(minCost+" "+totalFun);
            }
            else {
                break;
            }
        }
    }

}




