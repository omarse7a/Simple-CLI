package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

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

    public void mv(ArrayList<String> paths){
        Path destination = Paths.get(paths.get(paths.size()-1));
        if(!destination.toFile().exists()){
            System.out.println("mv: Destination \'" + destination.toString() + "\' does not exist");
            return;
        }
        boolean isDirectory = destination.toFile().isDirectory();

        if(!isDirectory && paths.size()>2) {
            System.out.println("mv: Destination \'" + destination.toString() + "\' is not a directory");
            return;
        }
        for (int i = 0; i < paths.size() - 1; i++) {
            Path sourcePath = Paths.get(paths.get(i));
            if(!sourcePath.toFile().exists()) {
                System.out.println("mv: Source \'" + sourcePath.toString() + "\' does not exist");
                return;
            }
            Path destinationPath = isDirectory ? destination.resolve(sourcePath.getFileName()) : destination;

            try {
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String cat(ArrayList<String> paths){
        StringBuilder content = new StringBuilder();
        for (String path : paths) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(path));
                for (String line : lines) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + path + ". " + e.getMessage());
            }
        }
        return content.toString();
    }

    public void exit(){
        active = false;
    }

    
    public void ls(boolean a, boolean r){
        Path dir = Paths.get(pwd());
        try(Stream<Path> filesStream = Files.list(dir)){
            if (a) {
                List<Path> files = filesStream.filter((path -> a || !path.getFileName().toString().startsWith(".")))
                        .collect(Collectors.toList());

                if (r) {
                    Collections.reverse(files);
                }

                // Print each file
                for (Path file : files) {
                    System.out.println(file.getFileName());
                }
            }
            else{
                List<Path> files = filesStream.collect(Collectors.toList());

                if (r) {
                    Collections.reverse(files);
                }

                // Print each file
                for (Path file : files) {
                    System.out.println(file.getFileName());
                }
            }

        }
        catch (IOException e){
            System.out.println("Error listing files: " + e.getMessage());
        }
    }

    public void rm(String targetName) {
        String target = pwd() + "/" + targetName;
        Path targetPath = Paths.get(target);

        if (!Files.exists(targetPath)) {
            System.out.println("File does not exist.");
            return;
        }

        try {
            if (Files.isDirectory(targetPath)) {
                rmdir(target);
            } else {
                Files.delete(targetPath);
                System.out.println("File deleted successfully.");
            }
        } catch (IOException e) {
            System.out.println("Failed to delete: " + e.getMessage());
        }
    }

    public void touch() {
        File file = new File(pwd());
        try {
            if (file.exists()) {
                file.setLastModified(System.currentTimeMillis());
                System.out.println("File already exists.");
            }
            else {
                file.createNewFile();
                System.out.println("File created successfully.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
