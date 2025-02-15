package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LsCommand extends OutputCommand {
    // maximum of two args should be passed
    public LsCommand (List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        List<Boolean> options;
        if(params.isEmpty()){
            options = List.of(false, false);
        }
        else if (params.size() == 1 && params.get(0).equals("-a")) {
            options = List.of(true, false);
        }
        else if (params.size() == 1 && params.get(0).equals("-r")) {
            options = List.of(false, true);
        }
        else if ((params.size() == 2 && params.get(0).equals("-a") && params.get(1).equals("-r"))
                || (params.size() == 2 && params.get(0).equals("-r") && params.get(1).equals("-a"))){
            options = List.of(true, true);
        }
        else{
            System.out.println("ls: Invalid arguments.");
            return;
        }

        StringBuilder list = new StringBuilder();
        Path dir = Paths.get(currentDirectory.getAbsolutePath());
        try(Stream<Path> filesStream = Files.list(dir)){
            // if option (-a) is applied
            if (options.getFirst()) {
                List<Path> files = filesStream.filter((path -> options.getFirst()|| !path.getFileName().toString().startsWith(".")))
                        .collect(Collectors.toList());
                // if option (-r) is also applied
                if (options.getLast()) {
                    Collections.reverse(files);
                }
                // Print each file
                for (Path file : files) {
                    list.append(file.getFileName().toString()).append("\n");
                }
            }
            // if option (-a) is not applied
            else{
                List<Path> files = filesStream.filter(path -> options.getFirst() ||!path.getFileName().toString().startsWith("."))
                        .collect(Collectors.toList());
                // if option (-r) is applied
                if (options.getLast()) {
                    Collections.reverse(files);
                }
                // Print each file
                for (Path file : files) {
                    list.append(file.getFileName().toString()).append("\n");
                }
            }
        }
        catch (IOException e){
            System.out.println("Error listing files: " + e.getMessage());
        }
        list.deleteCharAt(list.length()-1);
        output = list.toString();
    }
}
