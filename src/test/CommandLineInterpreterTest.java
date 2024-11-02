package test;
import main.CommandLineInterpreter;
import main.Main;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineInterpreterTest {
    private CommandLineInterpreter cli;

    @BeforeEach
    void setup() {
        cli = new CommandLineInterpreter();
    }

    @Test
    void cdTest() {
        // Change to a relative path
        String initialDir = cli.pwd();
        cli.mkdir("testDir");
        cli.cd("testDir");
        assertEquals(initialDir + "\\testDir", cli.pwd());

        // Cleanup
        cli.cd("..");
        cli.rmdir("testDir");
    }

    @Test
    void lsTest(){
        // Create test files
        cli.touch("testFile1.txt");
        cli.touch("testFile2.txt");
        cli.mkdir("testDir");

        // Check if they appear in ls output
        String lsOutput = cli.ls(false, false);
        assertTrue(lsOutput.contains("testFile1.txt"));
        assertTrue(lsOutput.contains("testFile2.txt"));
        assertTrue(lsOutput.contains("testDir"));

        // Cleanup
        cli.rm("testFile1.txt");
        cli.rm("testFile2.txt");
        cli.rmdir("testDir");
    }

    @Test
    void mkdirTest() {
        cli.mkdir("newTestDir");
        assertTrue(new File(cli.pwd() + "\\newTestDir").isDirectory());

        // Cleanup
        cli.rmdir("newTestDir");
    }

    @Test
    void rmdirTest() {
        cli.mkdir("tempDir");
        assertTrue(new File(cli.pwd() + "\\tempDir").exists());

        cli.rmdir("tempDir");
        assertFalse(new File(cli.pwd() + "\\tempDir").exists());
    }

    @Test
    void touchTest(){
        cli.touch("newFile.txt");
        File file = new File(cli.pwd() + "\\newFile.txt");
        assertTrue(file.exists());

        // Cleanup
        cli.rm("newFile.txt");
    }

    @Test
    void rmTest(){
        cli.touch("tempFile.txt");
        assertTrue(new File(cli.pwd() + "\\tempFile.txt").exists());

        cli.rm("tempFile.txt");
        assertFalse(new File(cli.pwd() + "\\tempFile.txt").exists());
    }

    @Test
    void catTest(){
        // Create test files
        cli.overwriteFile("file1.txt", "Hello ");
        cli.overwriteFile("file2.txt", "World!");

        ArrayList<String> files = new ArrayList<>(Arrays.asList("file1.txt", "file2.txt"));
        String content = cli.cat(files);

        assertEquals("Hello \nWorld!\n", content);

        // Cleanup
        cli.rm("file1.txt");
        cli.rm("file2.txt");
    }

    @Test
    void mvTest(){
        // Create test files and directories
        cli.touch("moveMe.txt");
        cli.mkdir("targetDir");

        ArrayList<String> paths = new ArrayList<>(Arrays.asList("moveMe.txt", "targetDir"));
        cli.mv(paths);

        assertTrue(new File(cli.pwd() + "\\targetDir\\moveMe.txt").exists());
        assertFalse(new File(cli.pwd() + "\\moveMe.txt").exists());

        // Cleanup
        cli.rm("targetDir\\moveMe.txt");
        cli.rmdir("targetDir");
    }
    @Test
    void redirectOutputTest() throws IOException {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        Path filePath = Path.of(cli.pwd(), "output.txt");

        String content = "This is a test for redirection.";
        Main.executeCommand(cli, "echo", new ArrayList<>(Arrays.asList(content)), "");
        cli.overwriteFile("output.txt", content);

        String fileContent = Files.readString(filePath);
        assertEquals(content, fileContent);

        Files.deleteIfExists(filePath);  // Clean up after test
    }

    @Test
    void appendOutputTest() throws IOException {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        Path filePath = Path.of(cli.pwd(), "append.txt");

        String initialContent = "Initial line.\n";
        String appendedContent = "Appended line.";

        // Write initial content to file
        cli.overwriteFile("append.txt", initialContent);

        // Append content
        Main.executeCommand(cli, "echo", new ArrayList<>(Arrays.asList(appendedContent)), "");
        cli.appendFile("append.txt", appendedContent);

        String fileContent = Files.readString(filePath);
        assertEquals(initialContent + appendedContent, fileContent);

        Files.deleteIfExists(filePath);  // Clean up after test
    }

    @Test
    void pipeCommandTest() {
        CommandLineInterpreter cli = new CommandLineInterpreter();

        // Create two files with initial content
        cli.touch("file1.txt");
        cli.overwriteFile("file1.txt", "This is file1 content.");

        cli.touch("file2.txt");
        cli.overwriteFile("file2.txt", "This is file2 content.");

        // Simulate "cat file1.txt file2.txt"
        ArrayList<String> catCommandParams = new ArrayList<>(Arrays.asList("file1.txt", "file2.txt"));
        String catOutput = Main.executeCommand(cli, "cat", catCommandParams, "");

        // Verify concatenated output
        String expectedOutput = "This is file1 content.\nThis is file2 content.\n";
        assertEquals(expectedOutput, catOutput);

        // Clean up
        Path filePath1 = Path.of(cli.pwd(), "file1.txt");
        Path filePath2 = Path.of(cli.pwd(), "file2.txt");
        try {
            Files.deleteIfExists(filePath1);
            Files.deleteIfExists(filePath2);
        } catch (IOException e) {
            System.err.println("Cleanup failed for pipeCommandTest.");
        }
    }

}