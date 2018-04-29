import java.io.*;
import java.util.*;

public class Main {

    static long mod = 1000000007;

    static FastReader in = new FastReader();

    static final String CORRECT = "No suspicious bugs found!";
    static final String INCORRECT = "Suspicious bugs found!";
    static boolean ansFlag;
    static ArrayList<Integer>[] bugList;
    static Integer[] visited;

    public static void main(String[] args) throws IOException {
        int testCases = in.nextInt();
        int scenario = 1;
        while (scenario <= testCases){
            Integer bugs = in.nextInt();
            Long interactions = in.nextLong();
            initialize(bugs);
            reset();

            for (long i=1;i<=interactions;i++){
                Integer u=in.nextInt(),v=in.nextInt();
                bugList[u].add(v);
                bugList[v].add(u);
            }

            applyBfs();
            displayOutput(scenario);
            scenario++;
        }
    }

    private static void displayOutput(int scenario) {
        System.out.println("Scenario #"+scenario+":");
        if (ansFlag){
            System.out.println(CORRECT);
        }
        else {
            System.out.println(INCORRECT);
        }
    }

    private static void initialize(Integer bugs) {
        bugList = new ArrayList[bugs+1];
        visited = new Integer[bugs+1];
        ansFlag = true;
    }

    private static void applyBfs() {
        for (int i = 1;i<bugList.length;i++){
            if (visited[i]==0 && ansFlag){
                visited[i] = 1;
                bfsUtil(i);
            }
        }
    }

    private static void bfsUtil(int root) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            Integer node = queue.remove();
            Iterator<Integer> iterator = bugList[node].listIterator();
            while (iterator.hasNext()){
                Integer nextNode = iterator.next();
                if (visited[nextNode] == visited[node]){
                    ansFlag = false;
                    return;
                }
                else if (visited[nextNode] == 0){
                    queue.add(nextNode);
                    visited[nextNode] = -visited[node];
                }
            }
        }
    }

    private static void reset() {
        for (int i=0;i<bugList.length;i++){
            bugList[i] = new ArrayList();
        }
        Arrays.fill(visited, 0);
    }
}

//Fastest Reader
class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;
    public Reader(){
        din=new DataInputStream(System.in);
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public Reader(String file_name) throws IOException{
        din=new DataInputStream(new FileInputStream(file_name));
        buffer=new byte[BUFFER_SIZE];
        bufferPointer=bytesRead=0;
    }

    public String readLine() throws IOException{
        byte[] buf=new byte[64]; // line length
        int cnt=0,c;
        while((c=read())!=-1){
            if(c=='\n')break;
            buf[cnt++]=(byte)c;
        }
        return new String(buf,0,cnt);
    }

    public int nextInt() throws IOException{
        int ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public long nextLong() throws IOException{
        long ret=0;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c=read();
        do{ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(neg)return -ret;
        return ret;
    }

    public double nextDouble() throws IOException{
        double ret=0,div=1;byte c=read();
        while(c<=' ')c=read();
        boolean neg=(c=='-');
        if(neg)c = read();
        do {ret=ret*10+c-'0';}while((c=read())>='0'&&c<='9');
        if(c=='.')while((c=read())>='0'&&c<='9')
            ret+=(c-'0')/(div*=10);
        if(neg)return -ret;
        return ret;
    }

    private void fillBuffer() throws IOException{
        bytesRead=din.read(buffer,bufferPointer=0,BUFFER_SIZE);
        if(bytesRead==-1)buffer[0]=-1;
    }

    private byte read() throws IOException{
        if(bufferPointer==bytesRead)fillBuffer();
        return buffer[bufferPointer++];
    }

    public void close() throws IOException{
        if(din==null) return;
        din.close();
    }
}

//Fast Reader
class FastReader {
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

