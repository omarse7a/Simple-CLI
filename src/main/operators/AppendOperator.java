package main.operators;

import main.Executable;
import main.commands.OutputCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AppendOperator implements Executable {
    private OutputCommand command;
    private String filePath;
    public AppendOperator(OutputCommand cmd, String path) {
        command = cmd;
        this.filePath = path;
    }

    @Override
    public void execute() {
        command.execute();
        String output = command.getOutput();

        Path path = Paths.get(filePath);
        if (!path.toFile().isFile()) {
            System.out.println("append: File '" + path + "' does not exist");
            return;
        }
        try {
            Files.writeString(path, output, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}