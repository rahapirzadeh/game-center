package networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * networking.ClientListener.java
 *
 * <p>This class runs on the client end and just
 * displays any text received from the server.
 *
 */

public class ClientListener implements Runnable { //reference: https://github.com/ChapmanCPSC353/mtchat
  private Socket clientSocket;

  ClientListener(Socket sock) {
    this.clientSocket = sock;
  }

  // Gets message from server and displays it to the user.
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));
      while (true) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          System.out.println(serverText);
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + clientSocket);
          clientSocket.close();
          break;
        }
      }
    } catch (SocketException e) {
      System.out.println("Host ended the game and closed the server.");
      System.exit(0);
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
}
