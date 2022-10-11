package org.yb;


public class App {
  public static void main(String[] args) {
    System.out.println("Starting embedded application to run DBZ Embedded engine with Yugabyte");

    CmdLineOpts configuration = CmdLineOpts.createFromArgs(args);
    try {
      EngineRunner engineRunner = new EngineRunner(configuration);
      engineRunner.run();
    } catch (Exception e) {
      System.out.println("Exception while trying to run the engine: " + e);
      System.exit(-1);
    }
  }
}
