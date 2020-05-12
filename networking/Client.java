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
    System.out.println("What is the IP of the host you'd like to connect to?");
    String hostname = inFromClient.readLine();
    int port = 7654;

    System.out.printf("Connecting to %s on port %d...%n", hostname, port);
    try {
      Socket clientSocket = new Socket(hostname, port);
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      System.out.println("Connection made.");

      // Start a thread to listen and display data sent to the server
      ClientListener listener = new ClientListener(clientSocket);
      new Thread(listener).start();
      out.println(username);

      String clientInput;
      while ((clientInput = inFromClient.readLine()) != null) {
        out.println(username + ": " + clientInput);
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
