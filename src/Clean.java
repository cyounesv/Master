import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Clean {
    public static void clean(String path) throws IOException, InterruptedException {

        ArrayList<String> okComputers = Deploy.getAvailableRessources(Deploy.getRessources(path));
        okComputers.parallelStream().map(l-> Master.sequentialProcessLauncher("ssh cvuillet@" + l + " rm -rf /tmp/cvuillet/")).collect(Collectors.toCollection(ArrayList::new));
    }
}

