package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OverwriteCommand extends Command {
    private OutputCommand command;
    private String filePath;
    public OverwriteCommand(OutputCommand cmd, String path) {
        command = cmd;
        this.filePath = path;
    }


    @Override
    public void execute() {
        command.execute();
        String output = command.getOutput();
        Path path = currentDirectory.toPath().resolve(filePath).normalize();
        try {
            Files.writeString(path, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
