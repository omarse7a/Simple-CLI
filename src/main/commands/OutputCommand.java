package main.commands;

import java.util.List;

public abstract class OutputCommand extends Command {
   protected String output;
   public OutputCommand() {
      super();
      output = "";
   }
   public OutputCommand(List<String> args) {
      super(args);
      output = "";
   }
   public String getOutput() {
      return output;
   }
}
