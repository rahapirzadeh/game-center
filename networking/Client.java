package networking;

import helpers.Player;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client { //reference: https://github.com/ChapmanCPSC353/mtchat
  public static void main(String[] args) throws IOException {
    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("What would you like to be your username?");
    String username = inFromClient.readLine();
    Player player = new Player(2, username);
    System.out.println("What is the IP of the host you'd like to connect to?");
    String hostname = inFromClient.readLine();
    int port = 7654;

    System.out.printf("Connecting to %s on port %d...%n", hostname, port);
    try (
      Socket connectionSocket = new Socket(hostname, port);
      PrintWriter out = new PrintWriter(connectionSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    ) {
      System.out.println("Connection made.");
      System.out.println("Waiting for host to select game mode.");
      // Start conversation with server
      String fromHost, fromClient;

      // Read input from the keyboard and send it to host
      // Quit with CTRL+C
      //TODO add exit functionality/cmd
      while ((fromHost = in.readLine()) != null) {
        System.out.println("Player 1: " + fromHost);
        fromClient = inFromClient.readLine();
        if (fromClient != null) {
          System.out.println("Player 2: " + fromClient);
          out.println(fromClient);
        }
      }
    } catch (UnknownHostException e) {
      System.out.println("Don't know about host " + hostname);
      System.out.println(e.getMessage());
    } catch (IOException e) {
      System.out.println("Exception caught while connecting to host " + hostname);
      System.out.println(e.getMessage());
    }
  }
}
