public class Main {
    public static void main(String[] args) {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        System.out.println(cli.pwd());
        cli.cd("..\\LZ77 compression and decompression");
        System.out.println(cli.pwd());
    }
}