package main.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TouchCommand extends Command {
    public TouchCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        if (params.isEmpty()) {
            System.out.println("touch: Missing arguments.");
            return;
        }
        if (params.size() > 1) {
            System.out.println("touch: Too many arguments.");
            return;
        }
        String target = params.getFirst();
        File file = new File(currentDirectory + File.separator + target);
        if (file.exists()) {
            boolean isModified = file.setLastModified(System.currentTimeMillis());
            if(isModified)
                System.out.println("File already exists.");
            return;
        }
        try {
            boolean isCreated = file.createNewFile();
            if(isCreated)
                System.out.println("File created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }
    }
}
