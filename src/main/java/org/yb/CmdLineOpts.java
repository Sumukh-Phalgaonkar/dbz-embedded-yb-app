package org.yb;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

public class CmdLineOpts {
  public String masterAddresses;
  public String hostname;
  public int databasePort = 5433;
  public String streamId;
  public String tableIncludeList;
  public String databaseName = "yugabyte";
  public String databasePassword = "yugabyte";
  public String databaseUser = "yugabyte";
  public String snapshotMode = "never";

  public static CmdLineOpts createFromArgs(String[] args) {
    Options options = new Options();

    options.addOption("master_addresses", true, "help string");
    options.addOption("stream_id", true, "help string");
    options.addOption("table_include_list", true, "help string");

    CommandLineParser parser = new DefaultParser();
    CommandLine commandLine = null;
    try {
      commandLine = parser.parse(options, args);
    } catch (Exception e) {
      System.out.println("Exception while parsing arguments: " + e);
      System.exit(-1);
    }

    CmdLineOpts configuration = new CmdLineOpts();
    configuration.initialize(commandLine);
    return configuration;
  }

  private void initialize(CommandLine commandLine) {
    if (commandLine.hasOption("master_addresses")) {
      masterAddresses = commandLine.getOptionValue("master_addresses");
      String[] nodes = masterAddresses.split(",");
      hostname = nodes[0].split(":")[0];
    }

    if (commandLine.hasOption("stream_id")) {
      streamId = commandLine.getOptionValue("stream_id");
    }

    if (commandLine.hasOption("table_include_list")) {
      tableIncludeList = commandLine.getOptionValue("table_include_list");
    }
  }

  public Properties asProperties() {
    Properties props = new Properties();
    props.setProperty("database.streamid", streamId);
    props.setProperty("database.master.address", masterAddresses+":7100");
    props.setProperty("table.include.list", tableIncludeList);
    return props;
  }

}
