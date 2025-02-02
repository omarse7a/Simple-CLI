package main;

import main.commands.Command;
import main.commands.PwdCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandParser {
    ArrayList<String> commandParts;

    public CommandParser(String line) {
        commandParts = new ArrayList<>();
    }

    public void parse(String commandline){
        split(commandline);
        ArrayList<String> operators = getOperators();
        // handling simple commands (with no operators)
        if(operators.isEmpty()){
            String command = "";
            ArrayList<String> params = new ArrayList<>();
            for (String part : commandParts) {
                if(isCommand(part)){
                    command = part;
                }
                else {
                    params.add(removeQuotes(part));
                }
            }
            createCommand(command, params);
            return;
        }
        // handling more complex commands (with operators)
        for (String operator : operators) {
            for (String part : commandParts) {
                
            }
        }
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
    
    public ArrayList<String> getOperators(){
        ArrayList<String> operators = new ArrayList<>();
        for (String part : commandParts) {
            if(part.equals(">") || part.equals(">>") || part.equals("|")) {
                operators.add(part);
            }
        }
        return operators;
    }

    public static boolean isCommand(String component) {
        Set<String> validCommands = new HashSet<>(Set.of("pwd", "cd", "ls", "mkdir", "rmdir", "touch", "mv", "rm", "cat")); // "exit()", "help()"
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
        }
        return null;
    }
}

