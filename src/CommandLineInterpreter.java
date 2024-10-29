import java.util.ArrayList;
import java.util.Arrays;

public class CommandLineInterpreter {

    public String pwd(){
        return System.getProperty("user.dir");
    }

    public void cd(String path){
        if(!path.equals(this.pwd()) && path.charAt(1) == ':') { // when the given path is absolute path
            System.setProperty("user.dir", path);
            return;
        }
        // splitting the current path into an ArrayList
        ArrayList<String> currentPath = new ArrayList<>(Arrays.asList(this.pwd().split("\\\\")));
        String[] pathDirs = path.split("\\\\"); // splitting the given pass into an array
        for(String dir : pathDirs) {
            if(dir.equals("..")) {
                if(currentPath.size() == 1)
                    continue;
                currentPath.remove(currentPath.size() - 1);
            }
            else
                currentPath.add(dir);
        }
        String newPath = String.join("\\", currentPath);
        System.setProperty("user.dir", newPath);
    }
}
