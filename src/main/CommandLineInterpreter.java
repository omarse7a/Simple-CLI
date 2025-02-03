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
