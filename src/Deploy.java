import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Deploy {
    public static ArrayList<String> getRessources(String datafilepath) throws IOException, InterruptedException {
        // String FolderPath = "/cal/homes/cvuillet/shavadoop/";
        //String datafilepath = FolderPath + "ListeOrdiTelecom.txt";

        BufferedReader bfr = new BufferedReader(new FileReader(new File(datafilepath)));
        String str = null;
        ArrayList<String> pc = new ArrayList<>();

        while ((str = bfr.readLine()) != null) {
            pc.add(str);
        }
        bfr.close();
        return pc;
    }
    public static ArrayList<String> getAvailableRessources(ArrayList<String> computers) throws IOException, InterruptedException {
       // Master.sequentialProcessLauncher("ssh cvuillet@ssh.enst.fr hostname");
        // ArrayList<String> pc = Deploy.getRessources("/home/cyounes/Documents/INFMDI727/ListeOrdiTelecom.txt");
        ArrayList<String> okComputers = computers.parallelStream().filter(l -> Master.sequentialProcessLauncher("ssh cvuillet@"+l+" hostname" )).collect(Collectors.toCollection(ArrayList::new));
        return okComputers;
    }

    public static void deployJar(String path) throws IOException, InterruptedException {

       ArrayList<String > okComputers = Deploy.getAvailableRessources(getRessources(path));
       System.out.println(okComputers);
      // okComputers.parallelStream().map(l-> Master.sequentialProcessLauncher("ssh cvuillet@" + l + " mkdir -p /tmp/cvuillet")).collect(Collectors.toCollection(ArrayList::new));
        okComputers.parallelStream().map(l-> Master.sequentialProcessLauncher("ssh cvuillet@" + l + " mkdir -p /tmp/cvuillet;ssh "+ l + " cp ~/shavadoop/SlaveNew.jar /tmp/cvuillet/")).collect(Collectors.toCollection(ArrayList::new));

    }

}
