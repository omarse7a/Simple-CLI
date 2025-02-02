package main.commands;

import java.io.File;

public class PwdCommand extends Command implements OutputCommand {
    String output;
    public PwdCommand() {
        this.output = "";
    }
    @Override
    public void execute() {
        output = currentDirectory.getAbsolutePath();
    }
    @Override
    public String getOutput() {
        return output;
    }
}
