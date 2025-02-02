package main.commands;

public class PipeCommand extends Command{
    private OutputCommand left;   // must return an output
    private Command right;  // takes input from left

    public PipeCommand(OutputCommand l, Command r) {
        this.left = l;
        this.right = r;
    }

    public PipeCommand(OutputCommand l) {
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
