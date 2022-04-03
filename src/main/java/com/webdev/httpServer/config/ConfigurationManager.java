package com.webdev.httpServer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.webdev.httpServer.config.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
  private static ConfigurationManager myConfigurationManager;

  private static Configuration myCurrentConfiguration;

  private ConfigurationManager() {
  }

  public static ConfigurationManager getInstance() {
    if (myConfigurationManager == null) {
      myConfigurationManager = new ConfigurationManager();
    }
    return myConfigurationManager;
  }

  /*
   * Used to load a configuration file by the path provided
   * */
  public void loadConfigurationFile(String filePath) {
    FileReader fileReader = null;
    try {
      fileReader = new FileReader(filePath);
    } catch (FileNotFoundException e) {
      throw new HttpConfigurationException(e);
    }

    StringBuffer stringBuffer = new StringBuffer();
    int i;
    while (true) {
      try {
        if (((i = fileReader.read()) == -1)) break;
      } catch (IOException e) {
        throw new HttpConfigurationException(e);
      }
      stringBuffer.append((char) i);
    }
    JsonNode conf = null;
    try {
      conf = Json.parse(stringBuffer.toString());
    } catch (JsonProcessingException e) {
      throw new HttpConfigurationException("Error parsing the Configuration File", e);
    }
    try {
      myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
    } catch (JsonProcessingException e) {
      throw new HttpConfigurationException("Error parsing the Configuration File, Internal", e);
    }
  }

  /*
   * Return the Current loaded configuration
   * */
  public Configuration getCurrentConfiguration() {
    if (myCurrentConfiguration == null) {
      throw new HttpConfigurationException("No Current Configuration Set.");
    }
    return myCurrentConfiguration;
  }
}
