package main.commands;

public class PwdCommand extends Command implements OutputCommand {
    String output;
    public PwdCommand() {
        this.output = "";
    }
    @Override
    public void execute() {
        if(!params.isEmpty()){
            System.out.println("pwd: No arguments are needed.");
            return;
        }
        output = currentDirectory.getAbsolutePath();
    }
    @Override
    public String getOutput() {
        return output;
    }
}
