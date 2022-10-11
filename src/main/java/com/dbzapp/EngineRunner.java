package com.dbzapp;

import io.debezium.connector.yugabytedb.*;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;

import org.apache.kafka.connect.json.*;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EngineRunner {
  private CmdLineOpts config;
  public EngineRunner(CmdLineOpts config) {
    this.config = config;
  }

  public void run() throws IOException {
    final Properties props = config.asProperties();//new Properties();
    props.setProperty("name", "engine");
    props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
    props.setProperty("offset.storage.file.filename", "/tmp/offsets.dat");
    props.setProperty("offset.flush.interval.ms", "60000");
    /* begin connector properties */
    props.setProperty("database.hostname", "yugabyte");
    props.setProperty("database.port", "5433");
    props.setProperty("database.user", "yugabyte");
    props.setProperty("database.password", "yugabyte");
    // props.setProperty("database.server.id", "85744");
    props.setProperty("database.server.name", "dbserver1");

    // props.setProperty("database.history",
    //      "io.debezium.relational.history.FileDatabaseHistory");
    // props.setProperty("database.history.file.filename",
    //      "/path/to/storage/dbhistory.dat");

    // Create the engine with this configuration ...
    try (DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
            .using(props)
            .notifying(record -> {
                System.out.println(record);
            }).build()
        ) {
    // Run the engine asynchronously ...
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(engine);

    // Do something else or wait for a signal or an event
}
  }
}
