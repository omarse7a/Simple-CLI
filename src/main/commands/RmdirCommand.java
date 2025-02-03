package main.commands;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RmdirCommand extends Command {
    public RmdirCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        for (String param : params) {
            Path path = Paths.get(currentDirectory.getAbsolutePath() + File.separator + param).normalize();
            File dirToRemove = path.toFile();
            if(dirToRemove.exists()) {
                boolean isDeleted = dirToRemove.delete();
                if (isDeleted)
                    System.out.println("Directory deleted: " + dirToRemove.getAbsolutePath());
                else
                    System.out.println("rmdir: Failed to remove '" + dirToRemove.getAbsolutePath() + "', directory not empty\n");
            }
            else
                System.out.println("rmdir: Directory '" + dirToRemove.getAbsolutePath() + "' does not exist");
        }
    }
}
