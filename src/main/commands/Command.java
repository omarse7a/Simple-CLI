package main.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected static File currentDirectory = new File(System.getProperty("user.dir"));
    protected List<String> params;

    public Command() {
        params = new ArrayList<>();
    }

    public void addParam(String param) {
        params.add(param);
    }

    public abstract void execute();
}
