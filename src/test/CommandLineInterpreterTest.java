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

//class CommandLineInterpreterTest {
//    private CommandLineInterpreter cli;
//
//    @BeforeEach
//    void setup() {
//        cli = new CommandLineInterpreter();
//    }
//
//    @Test
//    void cdTest() {
//        String initialDir = cli.pwd();
//        cli.mkdir("testDir");
//
//        cli.cd("testDir");
//        assertEquals(initialDir + "\\testDir", cli.pwd());
//
//        cli.cd("..");
//        cli.rmdir("testDir");
//    }
//
//    @Test
//    void lsTest(){
//        cli.touch("f1.txt");
//        cli.touch("f2.txt");
//        cli.mkdir("testDir");
//
//        String lsOutput = cli.ls(false, false);
//        assertTrue(lsOutput.contains("f1.txt"));
//        assertTrue(lsOutput.contains("f2.txt"));
//        assertTrue(lsOutput.contains("testDir"));
//
//        cli.rm("f1.txt");
//        cli.rm("f2.txt");
//        cli.rmdir("testDir");
//    }
//
//    @Test
//    void mkdirTest() {
//        cli.mkdir("newDir");
//        assertTrue(new File(cli.pwd() + "\\newDir").isDirectory());
//
//        cli.rmdir("newDir");
//    }
//
//    @Test
//    void rmdirTest() {
//        cli.mkdir("tempDir");
//        assertTrue(new File(cli.pwd() + "\\tempDir").exists());
//
//        cli.rmdir("tempDir");
//        assertFalse(new File(cli.pwd() + "\\tempDir").exists());
//    }
//
//    @Test
//    void touchTest(){
//        cli.touch("f.txt");
//        File file = new File(cli.pwd() + "\\f.txt");
//        assertTrue(file.isFile());
//
//        cli.rm("f.txt");
//    }
//
//    @Test
//    void rmTest(){
//        cli.touch("temp.txt");
//        assertTrue(new File(cli.pwd() + "\\temp.txt").exists());
//
//        cli.rm("temp.txt");
//        assertFalse(new File(cli.pwd() + "\\temp.txt").exists());
//    }
//
//    @Test
//    void catTest(){
//        cli.overwriteFile("file1.txt", "Omar");
//        cli.overwriteFile("file2.txt", "Sameh");
//
//        ArrayList<String> files = new ArrayList<>(Arrays.asList("file1.txt", "file2.txt"));
//        String content = cli.cat(files);
//
//        assertEquals("Omar\nSameh\n", content);
//
//        cli.rm("file1.txt");
//        cli.rm("file2.txt");
//    }
//
//    @Test
//    void mvTest(){
//        cli.touch("file.txt");
//        cli.mkdir("targetDir");
//
//        ArrayList<String> paths = new ArrayList<>(Arrays.asList("file.txt", "targetDir"));
//        cli.mv(paths);
//
//        assertTrue(new File(cli.pwd() + "\\targetDir\\file.txt").exists());
//        assertFalse(new File(cli.pwd() + "\\file.txt").exists());
//
//        cli.rm("targetDir\\file.txt");
//        cli.rmdir("targetDir");
//    }
//
//}