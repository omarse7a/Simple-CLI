package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        Scanner scanner = new Scanner(System.in);
        while(cli.isActive()) {
            System.out.print(cli.pwd() + "$ ");
            String inputLine = scanner.nextLine();
            String output = "";
            ArrayList<String> components = modifiedSplit(inputLine);
            for(int i = 0; i < components.size(); i++) {

                if(isCommand(components.get(i))) {
                    String command = components.get(i);
                    ArrayList<String> params = new ArrayList<>();
                    for(int j = i+1; j < components.size(); j++) {
                        if(!isConnector(components.get(j))) {
                            params.add(removeQuotes(components.get(j)));
                            i++;
                        }
                        else
                            break;
                    }
                    output = executeCommand(cli, command, params, output);
                }

                else if(isConnector(components.get(i))) {
                    String connector = components.get(i);
                    if(connector.equals(">")){
                        String filePath = components.get(i+1);
                        cli.overwriteFile(filePath, output);
                    }
                    else if(connector.equals(">>")){
                        String filePath = components.get(i+1);
                        cli.appendFile(filePath, output);
                    }
                    else{
                        String command = components.get(i+1);
                        ArrayList<String> params = new ArrayList<>();
                        for(int j = i+1; j < components.size(); j++) {
                            if(!isConnector(components.get(j))) {
                                params.add(removeQuotes(components.get(j)));
                                i++;
                            }
                            else
                                break;
                        }
                        output = executeCommand(cli, command, params, output);
                    }
                }
                else
                    output = removeQuotes(components.get(i));
            }
        }
    }

    public static ArrayList<String> modifiedSplit(String line) {
        ArrayList<String> values = new ArrayList<>();
        int startIndex = 0; // starting from the first space after the command

        boolean isFolderName = false;
        for(int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\"')
                isFolderName = !isFolderName;
            else if (line.charAt(i) == ' ' && !isFolderName) {
                values.add(line.substring(startIndex, i));
                startIndex = i + 1;
            }
        }
        values.add(line.substring(startIndex, line.length()));
        return values;
    }
    public static String removeQuotes(String input) {
        // removing quotes
        StringBuilder cleanInput = new StringBuilder(input);
        for (int j = 0; j < cleanInput.length(); j++) {
            if (cleanInput.charAt(j) == '\"') {
                cleanInput.deleteCharAt(j);
                j--;
            }
        }
        return cleanInput.toString();
    }

    public static boolean isCommand(String component) {
        String[] validCommands = { "pwd", "cd", "ls", "mkdir", "rmdir", "touch", "mv", "rm", "cat", "exit()", "help()"};
        for(String command : validCommands) {
            if (command.equals(component))
                return true;
        }
        return false;
    }

    public static boolean isConnector(String component){
        return (component.equals(">") || component.equals(">>") || component.equals("|"));
    }

    public static String executeCommand(CommandLineInterpreter cli, String command, ArrayList<String> args, String prevOutput) {
        if(!prevOutput.isBlank())
            args.add(prevOutput);
        String output = "";
        switch(command){
            case "pwd":
                output = cli.pwd();
                System.out.println(output);
                break;
            case "cd":
                if(args.size() == 1)
                    cli.cd(args.get(0));
                else if(args.size() > 1)
                    System.out.println(command + ": Invalid arguments");
                break;
            case "mkdir":
                if(args.size() == 1)
                    cli.mkdir(args.get(0));
                else
                    System.out.println(command + ": Invalid arguments");
                break;
            case "rmdir":
                if(args.size() == 1)
                    cli.rmdir(args.get(0));
                else
                    System.out.println(command + ": Invalid arguments");
                break;
            case "mv":
                if(args.size() < 2)
                    System.out.println(command + ": Invalid arguments");
                else
                    cli.mv(args);
                break;

            case "cat":
                output = cli.cat(args);
                System.out.println(output);
                break;

            case "ls":
                if(args.isEmpty()){
                    output = cli.ls(false, false);
                    System.out.println(output);
                }
                else if (args.size() == 1 && args.get(0).equals("-a")) {
                    output = cli.ls(true, false);
                    System.out.println(output);
                }
                else if (args.size() == 1 && args.get(0).equals("-r")) {
                    output = cli.ls(false, true);
                    System.out.println(output);
                }
                else if ((args.size() == 2 && args.get(0).equals("-a") && args.get(1).equals("-r"))
                        || (args.size() == 2 && args.get(0).equals("-r") && args.get(1).equals("-a"))){
                    output = cli.ls(true, true);
                    System.out.println(output);
                }
                else{
                    System.out.println(command + ": Invalid arguments");
                }
                break;

            case "touch":
                if (args.isEmpty()){
                    System.out.println(command + ": Invalid arguments");
                }
                else{
                    cli.touch(args.get(0));
                }
                break;
            case "rm":
                if (args.isEmpty()){
                    System.out.println(command + ": Invalid arguments");
                }
                else{
                    cli.rm(args.get(0));
                }
                break;
            case "help()":
                cli.help();break;
            case "exit()":
                cli.exit(); break;
            default:
                System.out.println(command + ": Command does not exist"); break;
        }
        return output;
    }
}