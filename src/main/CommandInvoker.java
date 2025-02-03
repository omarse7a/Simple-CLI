package main;

import main.commands.OutputCommand;

public class CommandInvoker {
    public CommandInvoker() {}
    public void invoke(Executable cmd) {
        if(cmd != null) {
            cmd.execute();
            if(cmd instanceof OutputCommand){
                String output = ((OutputCommand)cmd).getOutput();
                if(!output.isEmpty())
                    System.out.println(output);
            }
        }
        else{
            System.out.println("Invalid command: check help() to view available commands");
        }
    }
}
