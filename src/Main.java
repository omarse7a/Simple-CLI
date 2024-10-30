import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        Scanner scanner = new Scanner(System.in);
        System.out.print(cli.pwd() + "~ ");
        while(scanner.hasNextLine()) {

            String input = scanner.nextLine();
            String[] inputParts = input.split(" "); // potential problem: a path could have spaces!
            switch(inputParts[0]){
                case "pwd":
                    System.out.println(cli.pwd()); break;
                case "exit()":
                    System.exit(0); break;
                default:
                    System.out.println(inputParts[0] + ": Command does not exist"); break;
            }
            System.out.print(cli.pwd() + "~ ");

        }
    }
}