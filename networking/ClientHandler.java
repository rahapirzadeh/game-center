package networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * networking.ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */
public class ClientHandler implements Runnable { //reference: https://github.com/ChapmanCPSC353/mtchat
  private Socket connectionSocket;
  private ArrayList<Socket> socketList;

  ClientHandler(Socket socket, ArrayList<Socket> socketList) {
    this.connectionSocket = socket;
    this.socketList = socketList;  // Keep reference to master list
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      System.out.println("Connection made with socket " + connectionSocket);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSocket.getInputStream()));
      while (true) {
        // Get data sent from a client
        int playerID = clientInput.read();
        String data = clientInput.readLine();
        String clientUsername = data.split(":")[0];
        String clientMsg = data.split(":")[1].strip();
        if (clientMsg != null) { //TODO add condition to see if player id is equal to curr turn's player id

          System.out.printf("%s: %s%n", clientUsername, clientMsg);
          // helpers.Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          for (Socket s : socketList) {
            if (s != connectionSocket) {
              DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
              clientOutput.writeBytes(clientUsername + ": " + clientMsg + "\n");
            }
          }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSocket);
          // Remove from arraylist
          socketList.remove(connectionSocket);
          connectionSocket.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      socketList.remove(connectionSocket);
    }
  }
} // networking.ClientHandler for MtServer.java
