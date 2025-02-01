package main.commands;

import java.io.File;

public class PwdCommand extends Command implements OutputCommand {
    @Override
    public String execute() {
        return currentDirectory.getAbsolutePath();
    }
}
