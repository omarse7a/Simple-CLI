package main.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MkdirCommand extends Command {
    public MkdirCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        for (String param : params) {
            Path path = Paths.get(currentDirectory.getAbsolutePath() + File.separator + param).normalize();
            File dirToCreate = path.toFile();
            if (!dirToCreate.exists()) {
                boolean created = dirToCreate.mkdirs(); // Create the directories defined in the path
                if (created)
                    System.out.println("Directory created: " + dirToCreate.getAbsolutePath());
                else
                    System.out.println("mkdir: Failed to create directory: " + dirToCreate.getAbsolutePath());
            } else
                System.out.println("mkdir: Directory '" + dirToCreate + "' already exists");
        }
    }
}
