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
    private CommandParser parser;
    private CommandInvoker invoker;

    public CommandLineInterpreter() {
        parser = new CommandParser();
        invoker = new CommandInvoker();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print(System.getProperty("user.name") + "$ ");
            String inputLine = scanner.nextLine().trim();
            if(inputLine.equals("exit()")){
                scanner.close();
                return;
            }
            if(inputLine.equals("help()")) {
                help();
                continue;
            }
            Executable cmd = parser.parse(inputLine);
            invoker.invoke(cmd);
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
}
