package com.webdev.httpServer;

import com.webdev.httpServer.config.Configuration;
import com.webdev.httpServer.config.ConfigurationManager;

public class HttpServer {
  public static void main(String[] args) {
    System.out.println("Sever starting...");

    ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
    Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();

    System.out.println("Using Port: " + config.getPort());
    System.out.println("Using WebRoot: " + config.getWebroot());
  }
}
