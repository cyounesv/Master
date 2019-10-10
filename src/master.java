import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Master {
    public static boolean sequentialProcessLauncher(String command) {

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/home/cyounes/IdeaProjects/SlaveNew/out/artifacts/SlaveNew_jar/SlaveNew.jar");
        pb.redirectErrorStream(true);
        Process process = null;
        try {
            process = pb.start();
        } catch (IOException e1){
            e1.printStackTrace();
        }
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        boolean running = true;
        boolean toolong = false;
        while(running){
            try{
                boolean notTimedOut = !process.waitFor(6, TimeUnit.SECONDS);
                if (stdInput.ready()){
                    while (stdInput.ready()) {  //Tells whether this stream is ready to be read.
                        // A buffered character stream is ready if the buffer is not empty,
                        // or if the underlying character stream is ready.
                        //Returns: True if the next read() is guaranteed not to block for input, false otherwise.
                        int c = stdInput.read(); //returns the number of characters read or -1 if end of file reached
                        System.out.print((char) c);
                    }
                } else if(notTimedOut){
                    toolong =true;
                    process.destroy();
                }
                running = notTimedOut && !toolong;
            } catch (IOException | InterruptedException e2){
                e2.printStackTrace();
            }
        }

        return !toolong;
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        // Deploy the
​        String pc[] = new String[] { "sleep 10", "ls", "sleep 10" };
        // Une facon de lancer les threads, sans attendre de retour de leur part:
        Arrays.asList(pc).parallelStream().forEach(Master::sequentialProcessLauncher);
    }

}



