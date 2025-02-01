package main;

import main.commands.CdCommand;
import main.commands.Command;
import main.commands.OutputCommand;
import main.commands.PwdCommand;

import java.io.File;
import java.util.ArrayList;

public class CommandParser {
    ArrayList<String> commandParts;

    public CommandParser(String line) {
        commandParts = new ArrayList<>();

    }

    public void parse(String commandline){
        split(commandline);
        ArrayList<String> operators = get_operators();
        if(operators.isEmpty()){
            for (String part : commandParts) {

            }
            return;
        }
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
    
    public ArrayList<String> get_operators(){
        ArrayList<String> operators = new ArrayList<>();
        for (String part : commandParts) {
            if(part.equals(">") || part.equals(">>") || part.equals("|")) {
                operators.add(part);
            }
        }
        return operators;
    }
    
    // simple factory method
    public Command createCommand(String command){
        // switch on command to detect action
        switch(command){
            case "pwd": return new PwdCommand();
        }
        return null;
    }
}

