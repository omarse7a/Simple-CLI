package main;

import java.io.File;
import java.nio.file.Path;

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
        // resolving the given path against the current directory, then normalizing the (..)s
        Path newPath = currentDirectory.toPath().resolve(path).normalize();
        File newDirectory = newPath.toFile();

        if (newDirectory.isDirectory()) {
            currentDirectory = newDirectory;
        } else {
            System.out.println("cd: Path '" + newDirectory.getAbsolutePath() + "' does not exist");
        }
    }

    public void mkdir(String dirPath) {
        Path path = currentDirectory.toPath().resolve(dirPath).normalize();
        File dirToCreate = path.toFile();
        if (!dirToCreate.exists()) {
            boolean created = dirToCreate.mkdirs(); // Create the directory
            if (created)
                System.out.println("Directory created: " + dirToCreate.getAbsolutePath());
            else
                System.out.println("mkdir: Failed to create directory: " + dirToCreate.getAbsolutePath());
        }
        else
            System.out.println("mkdir: Directory \'" + dirToCreate + "\' already exists");
    }

    public void rmdir(String dirPath){
        Path path = currentDirectory.toPath().resolve(dirPath).normalize();
        File dirToRemove = path.toFile();
        if(dirToRemove.exists())
            dirToRemove.delete();
            if(dirToRemove.exists())
                System.out.println("rmdir: Failed to remove \'"+ dirToRemove.getAbsolutePath() +"\', directory not empty\n");
        else
            System.out.println("rmdir: Item \'" + dirToRemove.getAbsolutePath() + "\' does not exist");
    }

    public void exit(){
        active = false;
    }
}
