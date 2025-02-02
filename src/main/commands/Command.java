package main.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected static File currentDirectory;
    protected List<String> params;

    public Command() {
        currentDirectory = new File(System.getProperty("user.dir"));
        params = new ArrayList<>();
    }

    public void addParam(String param) {
        params.add(param);
    }

    public abstract void execute();
}
