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
        if(left == null || right == null) {
            System.out.println("Invalid command: check help() to view available commands");
            return;
        }
        left.execute();
        String output = left.getOutput();
        if(!output.isEmpty()){
            right.addParam(output);
            right.execute();
        }
    }
}
