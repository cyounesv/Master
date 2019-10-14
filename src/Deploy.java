import java.io.*;
import java.util.ArrayList;

public class Deploy {
    public static String[] getRessources() throws IOException, InterruptedException {
        String FolderPath = "/cal/homes/cvuillet/shavadoop/";
        String datafilepath = FolderPath + "ListeOrdiTelecom.txt";

        BufferedReader bfr = new BufferedReader(new FileReader(new File(datafilepath)));
        String str = null;
        ArrayList<String> pc = new ArrayList<>();
        //String CurrentCommand = null;
        //Integer NComputersTested = 0;
        //Integer NComputersAvailable = 0;
        //FileOutputStream fos = new FileOutputStream(FolderPath + "AvailableComputers.txt");
        //PrintWriter pw = new PrintWriter(FolderPath + "AvailableComputers.txt");

        while ((str = bfr.readLine()) != null) {
           // NComputersTested += 1;
            //CurrentCommand = "ssh " + str + " pwd";
            //ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", CurrentCommand);
            //pb = pb.redirectErrorStream(true);
            //Process p = pb.start();
            pc.add(str);
            //int notIsTimeout = p.waitFor();

            //if (notIsTimeout==0){
                //pw.println(str);
              //  NComputersAvailable += 1;

                // pb qui creer un dossier dans tmp puis copie le slave.jar
            //}
            //else{
              //  p.destroy();
            //}
        }
        bfr.close();
        //pw.close();
        //fos.close();

        System.out.printf("Found %d computers available on %d computers tested", NComputersAvailable, NComputersTested);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        getRessources();
    }

}
