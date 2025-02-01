package main.commands;

import java.io.File;

public abstract class Command {
    protected static File currentDirectory;
    public Command() {
        currentDirectory = new File(System.getProperty("user.dir"));
    }
}
