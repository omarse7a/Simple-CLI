package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class CatCommand extends OutputCommand {

    public CatCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        StringBuilder content = new StringBuilder();
        // when no file paths are provided -> read from user input
        if (params.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter text (enter 'Q' to stop):");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if ("q".equalsIgnoreCase(line)) {
                    break;
                }
                content.append(line).append("\n");
            }
        }
        // when file paths are provided -> read from each file
        else {
            for (String path : params) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get(path));
                    for (String line : lines) {
                        content.append(line).append("\n");
                    }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + e.getMessage() + ", please provide a valid file path.");
                }
            }
        }
        content.deleteCharAt(content.length()-1);
        output = content.toString();
    }
}
