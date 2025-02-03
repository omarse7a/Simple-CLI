package main.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CdCommand extends Command {
    public CdCommand(List<String> args) {
        super(args);
    }

    public void execute() {
        if (params.isEmpty()) {
            System.out.println("cd: Missing arguments.");
            return;
        }
        if (params.size() > 1) {
            System.out.println("cd: Too many arguments.");
            return;
        }
        Path newPath = Paths.get(currentDirectory.getAbsolutePath() + File.separator + params.getFirst()).normalize();
        //Path newPath = currentDirectory.toPath().resolve(params.getFirst()).normalize();
        File newDirectory = newPath.toFile();

        if (newDirectory.isDirectory()) {
            currentDirectory = newDirectory;
        } else {
            System.out.println("cd: Path '" + newDirectory.getAbsolutePath() + "' does not exist.");
        }
    }
}
