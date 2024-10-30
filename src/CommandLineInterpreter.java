import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CommandLineInterpreter {
    private File currentDirectory;
    private boolean active;

    CommandLineInterpreter() {
        // setting workingDirectory to the working directory at the time the application started
        currentDirectory = new File(System.getProperty("user.dir"));
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public String pwd(){
        return currentDirectory.getAbsolutePath();
    }

    public void cd(String path){
        // getting a clean absolute path
        if(!path.equals(this.pwd()) && path.charAt(1) != ':') { // when the given path is not absolute or the equal to the current dir
            // splitting the working directory path into an ArrayList
            ArrayList<String> currentPath = new ArrayList<>(Arrays.asList(this.pwd().split("\\\\")));
            // splitting the given path into an array
            String[] pathDirs = path.split("\\\\");
            for (String dir : pathDirs) {
                if (dir.equals("..")) {
                    if (currentPath.size() == 1)
                        continue;
                    currentPath.remove(currentPath.size() - 1);
                } else if (dir.equals("."))
                    continue;
                else
                    currentPath.add(dir);
            }
            path = String.join("\\", currentPath);  // absolute path
        }

        File newDirectory = new File(path);
        if(newDirectory.exists()){
            currentDirectory = newDirectory;
        }
        else{
            System.err.println("cd: Path \'" + newDirectory.getAbsolutePath() + "\' does not exist");
            return;
        }
    }

    public void mkdir(String dirPath){
        File dirToCreate = new File(currentDirectory + "\\" + dirPath);
        if(!dirToCreate.exists()){
            dirToCreate.mkdirs();
        }
        else
            System.err.println("mkdir: Item \'" + dirPath + "\' already exists");
    }

    public void rmdir(String dirPath){
        File dirToReomve = new File(currentDirectory + "\\" + dirPath);
        if(dirToReomve.exists())
            dirToReomve.delete();
        else
            System.err.println("rmdir: Item \'" + dirToReomve.getAbsolutePath() + "\' does not exist");
    }

    public void exit(){
        active = false;
    }
}
