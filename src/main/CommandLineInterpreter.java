package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.nio.file.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandLineInterpreter {
    private File currentDirectory;
    private boolean active;

    public CommandLineInterpreter() {
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
        if(dirToRemove.exists()) {
            dirToRemove.delete();
            if (dirToRemove.exists())
                System.out.println("rmdir: Failed to remove \'" + dirToRemove.getAbsolutePath() + "\', directory not empty\n");
            else
                System.out.println("Directory deleted: " + dirToRemove.getAbsolutePath());
        }
        else
            System.out.println("rmdir: Directory \'" + dirToRemove.getAbsolutePath() + "\' does not exist");
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
        // when no file paths are provided -> read from user input
        if (paths.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter text (enter 'Q' to stop):");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if ("q".equalsIgnoreCase(line)) {
                    break;
                }
                content.append(line).append("\n");
            }
            content.deleteCharAt(content.length()-1);
        }
        // when file paths are provided -> read from each file
        else {
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
        }
        return content.toString();
    }

    public String ls(boolean a, boolean r){
        String fileList = "";
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
                    fileList += (file.getFileName().toString() + "\n");
                }
            }
            else{
                List<Path> files = filesStream.filter(path -> a ||!path.getFileName().toString().startsWith("."))
                        .collect(Collectors.toList());
                if (r) {
                    Collections.reverse(files);
                }
                // Print each file
                for (Path file : files) {
                    fileList += (file.getFileName().toString() + "\n");
                }
            }
        }
        catch (IOException e){
            System.out.println("Error listing files: " + e.getMessage());
        }
        return fileList;
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
    public void touch(String targetName) {
        String target = pwd() + "/" + targetName;
        File file = new File(target);
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

    public void overwriteFile(String filePath, String input){
        Path path = currentDirectory.toPath().resolve(filePath).normalize();
        try {
            Files.writeString(path, input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void appendFile(String filePath, String input){
        Path path = currentDirectory.toPath().resolve(filePath).normalize();
        if(!path.toFile().isFile()){
            System.out.println("append: File \'" + path.toString() + "\' does not exist");
            return;
        }
        try {
            Files.writeString(path, input, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void help(){
        System.out.println("Our CLI Help - Available Commands:");
        System.out.println("---------------------------------------------------");
        System.out.println("pwd    : Print the current working directory.");
        System.out.println("cd     : Change the current directory. Usage: cd <directory>");
        System.out.println("ls     : Lists files and directories in the current directory. Options: -a (show all files), -r (reverse order)");
        System.out.println("mkdir  : Create a new directory. Usage: mkdir <directory_name>");
        System.out.println("rmdir  : Delete an empty directory. Usage: rmdir <directory_name>");
        System.out.println("touch  : Create new empty file. Usage: touch <file_name>");
        System.out.println("mv     : Move or rename a file or directory. Usage: mv <source> <destination>");
        System.out.println("rm     : Delete file. Usage: rm <file_name>");
        System.out.println("cat    : Concatenate files and print their contents to the screen. Usage: cat <file_name>");
        System.out.println("---------------------------------------------------");
        System.out.println("Use these commands in the CLI to manage files and directories.");
    }

    public void exit(){
        active = false;
    }
}
