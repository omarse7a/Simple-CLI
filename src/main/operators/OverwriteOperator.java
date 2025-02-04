package main.operators;

import main.Executable;
import main.commands.OutputCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OverwriteOperator implements Executable {
    private OutputCommand command;
    private String filePath;
    public OverwriteOperator(OutputCommand cmd, String path) {
        command = cmd;
        this.filePath = path;
    }

    @Override
    public void execute() {
        if(command == null) {
            System.out.println("Invalid command: check help() to view available commands");
            return;
        }
        command.execute();
        String output = command.getOutput();

        Path path = Paths.get(filePath);
        try {
            Files.writeString(path, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
