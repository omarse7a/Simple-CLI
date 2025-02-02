package main;

import main.commands.Command;
import main.commands.OutputCommand;

public class CommandInvoker {
    public CommandInvoker() {}
    public void invoke(Command cmd) {
        cmd.execute();
        if(cmd instanceof OutputCommand){
            System.out.println(((OutputCommand) cmd).getOutput());
        }
    }
}
