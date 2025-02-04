package main.commands;

import java.io.File;
import java.util.List;

public class RmCommand extends Command {
    public RmCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        if (params.isEmpty()) {
            System.out.println("rm: Missing arguments.");
            return;
        }
        for(String param : params) {
            File file = new File(currentDirectory + File.separator + param);
            if (!file.exists()) {
                System.out.println("File does not exist.");
            } else {
                boolean isDeleted = file.delete();
                if (isDeleted)
                    System.out.println("File deleted successfully.");
            }
        }
    }
}
