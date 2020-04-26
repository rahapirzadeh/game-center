package networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Host { //reference: https://github.com/ChapmanCPSC353/mtchat

  public Host() {}

  private void getConnection() throws IOException {
    BufferedReader serverIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("What would you like to be your username?");
    String username = serverIn.readLine();
    System.out.printf("Looking to host a game? Tell your friends to connect to %s%n", getPublicIP());
    System.out.println("Waiting for player 2 to connect on port 7654.");
    // Wait for a connection from the client
    try (
      // Accept connection from client
      ServerSocket serverSocket = new ServerSocket(7654);
      Socket clientSocket = serverSocket.accept();
      PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    ) {
      System.out.println("Received connection.");
      // Start conversation with client
      String inputLine, outputLine;

      // TODO handle game logic

      } catch (IOException e) {
        System.out.println("Exception caught when listening for a connection.");
        System.out.println(e.getMessage());
    }
  }

  public static String getPublicIP() throws IOException { //reference: https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
    URL whatsMyIP = new URL("http://checkip.amazonaws.com");
    BufferedReader br = new BufferedReader(new InputStreamReader(whatsMyIP.openStream()));
    return br.readLine();
  }

  public static void main(String[] args) throws IOException {
    Host h = new Host();
    h.getConnection();
  }
}
