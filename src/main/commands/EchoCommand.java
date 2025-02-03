package main.commands;

import java.util.List;

public class EchoCommand extends OutputCommand {
    public EchoCommand(List<String> args) {
        super(args);
    }

    @Override
    public void execute() {
        if (params.size() > 1) {
            System.out.println("cd: Too many arguments.");
            return;
        }
        output = params.getFirst();
    }
}
