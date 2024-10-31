package test;
import main.CommandLineInterpreter;
import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterpreterTest {

    @Test
    void cdTest() {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        cli.cd("C:\\Users\\Public");
        assertEquals("C:\\Users\\Public", cli.pwd());
    }

    @Test
    void mkdir() {
    }

    @Test
    void rmdir() {
    }
}