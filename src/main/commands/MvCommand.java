package main.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class MvCommand extends Command {
    public MvCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        if(params.size() < 2) {
            System.out.println("mv: Missing arguments");
            return;
        }
        // extracting the last param as destination path
        Path destination = Paths.get(params.getLast());
        // checking if destination is a directory
        boolean isDirectory = destination.toFile().isDirectory();

        if(!isDirectory && params.size()>2) {
            System.out.println("mv: Destination'" + destination+ "' is not a directory");
            return;
        }
        for (int i = 0; i < params.size() - 1; i++) {
            Path sourcePath = Paths.get(params.get(i));
            if(!sourcePath.toFile().exists()) {
                System.out.println("mv: Source '" + sourcePath + "' does not exist");
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
}
