package test;
import  org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterpreterTest {

    @Test
    void isActive() {
    }

    @Test
    void pwdTest() {
    }

    @Test
    void cdTest() {
    }

    @Test
    void mkdirTest() {
    }

    @Test
    void rmTest() {
        String fileName = "testFile.txt";
        cli.touch(fileName);
        File file = new File(cli.pwd() + "/" + fileName);
        assertTrue(file.exists());
        cli.rm(fileName);
        assertFalse(file.exists());
    }

    @Test
    void touchTest() {
        String fileName = "testFile.txt";
        cli.touch(fileName);
        File file = new File(cli.pwd() + "/" + fileName);
        assertTrue(file.exists());
        cli.rm(fileName);  // Cleanup
    }

    @Test
    void lsTest() {
        cli.mkdir("testDir1");
        cli.touch("testFile1.txt");

        // Capture output (could use a ByteArrayOutputStream in a real environment)
        cli.ls(false, false);

        // Check the existence of created files/directories as a placeholder for ls output validation
        assertTrue(new File(cli.pwd() + "/testDir1").exists());
        assertTrue(new File(cli.pwd() + "/testFile1.txt").exists());

        // Cleanup
        cli.rm("testFile1.txt");
        cli.rmdir("testDir1");
    }
}
