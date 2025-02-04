package main;

import main.commands.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// example
// touch f1.txt
// echo "../LZW" > f1.txt
// cat f1.txt | cd

public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        cli.run();
    }
}
