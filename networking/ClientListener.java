package networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * networking.ClientListener.java
 *
 * <p>This class runs on the client end and just
 * displays any text received from the server.
 *
 */

public class ClientListener implements Runnable { //reference: https://github.com/ChapmanCPSC353/mtchat
  private Socket connectionSocket;

  ClientListener(Socket sock) {
    this.connectionSocket = sock;
  }

  /**
   * Gets message from server and displays it to the user.
   */
  public void run() {
    try {
      BufferedReader serverInput = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      while (true) {
        // Get data sent from the server
        String serverText = serverInput.readLine();
        if (serverInput != null) {
          System.out.println(serverText);
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSocket);
          connectionSocket.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
  }
} // networking.ClientListener for MtClient
