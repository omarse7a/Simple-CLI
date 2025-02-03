package main;

import main.commands.*;
import main.operators.AppendOperator;
import main.operators.OverwriteOperator;
import main.operators.PipeOperator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandParser {
    ArrayList<String> commandParts;

    public CommandParser() {
        commandParts = new ArrayList<>();
    }

    public Executable parse(String commandline){
        split(commandline);
        // handling commands (with operators)
        String commandOp = "";
        ArrayList<String> params = new ArrayList<>();
        boolean hasPipe = false;
        Executable cmd = null;
        for(int i = 0; i < commandParts.size(); i++) {
            String part = commandParts.get(i);
            // extracting command op code
            if(isCommand(part)){
                commandOp = part;
            }
            // handling pipes
            else if(isPipe(part)){
                cmd = createCommand(commandOp, params);
                commandOp = "";
                params.clear();
                hasPipe = true;
            }
            else if(isRedirect(part)){
                String filePath = commandParts.get(i+1);    // extracting file path
                // handling the pipe creation
                if(hasPipe){
                    Command right = createCommand(commandOp, params);
                    cmd = new PipeOperator((OutputCommand) cmd, right);
                    commandOp = "";
                    params.clear();
                }
                // redirecting a command output into a file
                if(!commandOp.isEmpty()){
                    cmd = createCommand(commandOp, params);
                }
                if(part.equals(">")){
                    return new OverwriteOperator((OutputCommand) cmd, filePath);
                }
                else {
                    return new AppendOperator((OutputCommand) cmd, filePath);
                }
            }
            // adding a parameter to parameters list
            else {
                params.add(removeQuotes(part));
            }
        }
        // handling the pipe creation
        if(hasPipe){
            Command right = createCommand(commandOp, params);
            return new PipeOperator((OutputCommand) cmd, right);
        }
        return createCommand(commandOp, params);
    }

    // splitting the commandline into parts
    public void split(String line){
        int startIndex = 0;
        boolean isFolderName = false;
        for(int i = 0; i < line.length(); i++) {
            // handling space-separated folder names to be treated as whole
            if(line.charAt(i) == '"')
                isFolderName = !isFolderName;
            // splitting at spaces
            else if (line.charAt(i) == ' ' && !isFolderName) {
                commandParts.add(line.substring(startIndex, i));
                startIndex = i + 1;
            }
        }
        commandParts.add(line.substring(startIndex, line.length()));    // adding the last part of the commandline
    }

    public boolean isPipe(String component){
        return component.equals("|");
    }

    public boolean isRedirect(String component){
        return component.equals(">") || component.equals(">>");
    }

    public static boolean isCommand(String component) {
        Set<String> validCommands = new HashSet<>(Set.of("pwd", "cd", "ls", "mkdir", "rmdir", "touch", "echo", "rm", "cat", "mv")); // "exit()", "help()"
        return validCommands.contains(component);
    }

    public String removeQuotes(String input) {
        // removing quotes from space separated file names
        StringBuilder cleanInput = new StringBuilder(input);
        for (int i = 0; i < cleanInput.length(); i++) {
            if (cleanInput.charAt(i) == '"') {
                cleanInput.deleteCharAt(i);
                i--;
            }
        }
        return cleanInput.toString();
    }

    // simple factory method
    public Command createCommand(String command, List<String> params) {
        // switch on command to detect action
        switch(command){
            case "pwd": return new PwdCommand();
            case "cd": return new CdCommand(params);
            case "ls": return new LsCommand(params);
            case "touch": return new TouchCommand(params);
            case "rm": return new RmCommand(params);
            case "echo": return new EchoCommand(params);
        }
        return null;
    }
}