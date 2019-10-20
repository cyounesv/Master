import sun.awt.X11.XSystemTrayPeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Master {
    public static boolean sequentialProcessLauncher(String command) {
//"java", "-jar", "/home/cyounes/IdeaProjects/SlaveNew/out/artifacts/SlaveNew_jar/SlaveNew.jar"
        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
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
        while(running) {
            try {
                boolean notTimedOut = !process.waitFor(10, TimeUnit.SECONDS);
                if (stdInput.ready()) {
                    while (stdInput.ready()) {
                        stdInput.read();
                        //System.out.println(stdInput.read());
                        // A buffered character stream is ready if the buffer is not empty,
                        // or if the underlying character stream is ready.
                        //Returns: True if the next read() is guaranteed not to block for input, false otherwise.
                    }
                } else if (notTimedOut && process.isAlive()) {
                    toolong = true;
                    process.destroy();
                }
                running = notTimedOut && !toolong;
            } catch (IOException | InterruptedException e2) {
                e2.printStackTrace();
            }
        }

        return !toolong && process.exitValue()==0? true: false;
    }

    public static void copySplits(ArrayList<String> okComputers) {
        String localPath = "/home/cyounes/Documents/INFMDI727/Splits/";
        for(int i=0; i<=2; i++) {
            String command1 = "ssh cvuillet@" + okComputers.get(i) + " mkdir -p /tmp/cvuillet/splits";
            String command2 = "scp " + localPath + "S" + i + ".txt cvuillet@" + okComputers.get(i) + ":/tmp/cvuillet/splits/";
            System.out.println(command1 + ";" + command2);
            Master.sequentialProcessLauncher(command1);
            Master.sequentialProcessLauncher(command2);
        }
    }

    public static void LaunchSlaveMapper(ArrayList<String> okComputers) {

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "/cal/homes/cvuillet/shavadoop/ListeOrdiTelecom.txt";
        String path2 = "/home/cyounes/Documents/INFMDI727/ListeOrdiTelecom.txt";

        ArrayList<String > okComputers = Deploy.getAvailableRessources(Deploy.getRessources(path2));
        System.out.println(okComputers);
        //Deploy.deployJar(path2);
        //Clean.clean(path2);
        //Master.copySplits(okComputers);
        //okComputers.parallelStream().map(l-> Master.sequentialProcessLauncher("ssh cvuillet@" + l + " java -jar SlaveNew.jar ")).collect(Collectors.toCollection(ArrayList::new));

    }
}

// Appel fonction getRessources de Deploy qui va me retourner un tableau de string contenant les noms des ordis
// Une facon de lancer les threads, sans attendre de retour de leur part:
// Arrays.asList(pc).parallelStream().map(l -> sequentialProcessLauncher("ssh cvuillet@"+l+"pwd" )).collect(Collectors.toCollection(ArrayList::new));

//Arrays.asList(pc).parallelStream().map(l -> sequentialProcessLauncher("ssh cvuillet@"+l+" java -jar slave blablabla")).collect(Collectors.toCollection(ArrayList::new));


// si retour = not too long je vais pouvoir ecrire dans mon fichier

