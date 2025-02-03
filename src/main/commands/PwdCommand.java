package main.commands;

import java.util.List;

public class PwdCommand extends OutputCommand {
    String output;
    public PwdCommand(List<String> args) {
        super(args);
    }
    @Override
    public void execute() {
        if(!params.isEmpty()){
            System.out.println("pwd: No arguments are needed.");
            return;
        }
        output = currentDirectory.getAbsolutePath();
    }
}
