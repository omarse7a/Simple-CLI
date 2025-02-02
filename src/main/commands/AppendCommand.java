package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AppendCommand extends Command {
    private OutputCommand command;
    private String filePath;
    public AppendCommand(OutputCommand cmd, String path) {
        command = cmd;
        this.filePath = path;
    }

    @Override
    public void execute() {
        command.execute();
        String output = command.getOutput();
        Path path = currentDirectory.toPath().resolve(filePath).normalize();
        if (!path.toFile().isFile()) {
            System.out.println("append: File '" + path.toString() + "' does not exist");
            return;
        }
        try {
            Files.writeString(path, output, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}