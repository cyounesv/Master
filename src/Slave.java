import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Slave {

    public static void launchAction(String mode, String split_path) throws IOException {
        if (mode.equals("split")) {
            String OutputPath = split_path.replace("S", "UM");
            OutputPath = OutputPath.replace("splits", "maps");
            BufferedReader bfr = new BufferedReader(new FileReader(new File(split_path)));

            String str = null;

            PrintWriter pw = new PrintWriter(OutputPath);
            while((str=bfr.readLine()) != null){
                StringTokenizer tokenizer = new StringTokenizer(str);
                while (tokenizer.hasMoreTokens()){
                    String word = tokenizer.nextToken();
                    word = word.toLowerCase();
                    pw.print(word);
                    pw.println(" 1");
                }
            }
            pw.close();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        launchAction(args[0], args[1]);
    }



    public static int Etape4Preli(int x, int y) throws InterruptedException {
        Thread.sleep(1000);
        return x+y;
    }
}