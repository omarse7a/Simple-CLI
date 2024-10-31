package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter(); // cd C:\Users\hp\Desktop\FCAI\Algorithms\"assignment 1"\"part A"
//        ArrayList<String> p = new ArrayList<>(Arrays.asList("nfolder", "folder"));
//        cli.mv(p);
        Scanner scanner = new Scanner(System.in);
        while(cli.isActive()) {
            System.out.print(cli.pwd() + "~ ");
            String input = scanner.nextLine();
            String command = getCommand(input); // extracting the command
            ArrayList<String> params = getParams(input);
            switch(command){
                case "pwd":
                    System.out.println(cli.pwd()); break;
                case "cd":
                    if(params.size() == 1)
                        cli.cd(params.get(0));
                    else if(params.size() > 1)
                        System.out.println(command + ": Invalid arguments");
                    break;
                case "mkdir":
                    if(params.size() == 1)
                        cli.mkdir(params.get(0));
                    else
                        System.out.println(command + ": Invalid arguments");
                    break;
                case "rmdir":
                    if(params.size() == 1)
                        cli.rmdir(params.get(0));
                    else
                        System.out.println(command + ": Invalid arguments");
                    break;

                case "mv":
                    if(params.size() < 2)
                        System.out.println(command + ": Invalid arguments");
                    else
                        cli.mv(params);
                case "exit()":
                    cli.exit(); break;
                default:
                    System.out.println(command + ": Command does not exist"); break;
            }
        }
    }
    public static String getCommand(String line) {
        int spaceIndex = line.indexOf(' ');
        if(spaceIndex == -1)
            return line;
        return line.substring(0, spaceIndex);
    }
    public static ArrayList<String> getParams(String line) {
        ArrayList<String> params = new ArrayList<>();
        int startIndex = line.indexOf(' ') + 1; // starting from the first space after the command
        if(startIndex == 0)
            return params;
        boolean isFolderName = false;
        for(int i = startIndex; i < line.length(); i++) {
            if(line.charAt(i) == '\"')
                isFolderName = !isFolderName;
            else if (line.charAt(i) == ' ' && !isFolderName) {
                System.out.println(i);
                params.add(line.substring(startIndex, i));
                startIndex = i + 1;
            }
        }
        params.add(line.substring(startIndex, line.length()));
        // removing quotes
        for (int i = 0; i < params.size(); i++) {
            StringBuilder cleanParam = new StringBuilder(params.get(i));
            for (int j = 0; j < cleanParam.length(); j++) {
                if (cleanParam.charAt(j) == '\"') {
                    cleanParam.deleteCharAt(j);
                    j--;
                }
            }
            params.set(i, cleanParam.toString());
        }
        return params;
    }
}