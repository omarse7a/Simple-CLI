package main;

import main.commands.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// test
// touch f1.txt
// echo "../LZW" > f1.txt
// cat f1.txt | cd

public class Main {
    public static void main(String[] args) {
        CommandParser parser = new CommandParser();
        CommandInvoker invoker = new CommandInvoker();
        CommandLineInterpreter cli = new CommandLineInterpreter();
        Scanner scanner = new Scanner(System.in);
        while (cli.isActive()) {
            System.out.print(System.getProperty("user.name") + "$ ");
            String inputLine = scanner.nextLine();
            Executable cmd = parser.parse(inputLine);
            invoker.invoke(cmd);
        }
    }
}