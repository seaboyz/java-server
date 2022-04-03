package com.webdev.httpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.webdev.httpServer.config.Configuration;
import com.webdev.httpServer.config.ConfigurationManager;

public class HttpServer {
  public static void main(String[] args) {
    System.out.println("Sever starting...");

    ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
    Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();

    System.out.println("Using Port: " + config.getPort());
    System.out.println("Using WebRoot: " + config.getWebroot());

    try (ServerSocket serverSocket = new ServerSocket(config.getPort())) {
      Socket socket = serverSocket.accept();
      InputStream inputStream = socket.getInputStream();
      OutputStream outputStream = socket.getOutputStream();

      String html = "<html><body><h1>Java Http Server</html></h1></html>";
      final String CRLF = "\n\r";

      String response = "HTTP/1.1 200 OK" + CRLF // HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
          + "Content-Length: " + html.getBytes().length + CRLF // HEADER
          + CRLF
          + html
          + CRLF + CRLF;

      outputStream.write(response.getBytes());

      inputStream.close();
      outputStream.close();
      socket.close();
      serverSocket.close();
    } catch (IOException e) {

      e.printStackTrace();
    }

  }
}
