public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        System.out.println(cli.pwd());
        cli.cd("..");
        System.out.println(cli.pwd());
        cli.rmdir("folder1\\folder2");
        cli.cd("folder1");
        System.out.println(cli.pwd());
    }
}