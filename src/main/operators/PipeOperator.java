package main.operators;

import main.Executable;
import main.commands.Command;
import main.commands.OutputCommand;

public class PipeOperator implements Executable {
    private OutputCommand left;   // must return an output
    private Command right;  // takes input from left

    public PipeOperator(OutputCommand l, Command r) {
        this.left = l;
        this.right = r;
    }

    public PipeOperator(OutputCommand l) {
        this.left = l;
    }

    public void addPipeRight(Command r){
        this.right = r;
    }

    @Override
    public void execute() {
        left.execute();
        String output = left.getOutput();
        right.addParam(output);
        right.execute();
    }
}
