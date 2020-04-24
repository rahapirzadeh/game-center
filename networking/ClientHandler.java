import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ClientHandler.java
 *
 * <p>This class handles communication between the client
 * and the server.  It runs in a separate thread but has a
 * link to a common list of sockets to handle broadcast.
 *
 */
public class ClientHandler implements Runnable { //reference: https://github.com/ChapmanCPSC353/mtchat
  private Socket connectionSock = null;
  private ArrayList<Socket> socketList;

  ClientHandler(Socket sock, ArrayList<Socket> socketList) {
    this.connectionSock = sock;
    this.socketList = socketList;  // Keep reference to master list
  }

  /**
   * received input from a client.
   * sends it to other clients.
   */
  public void run() {
    try {
      System.out.println("Connection made with socket " + connectionSock);
      BufferedReader clientInput = new BufferedReader(
          new InputStreamReader(connectionSock.getInputStream()));
      while (true) {
        // Get data sent from a client
        String data = clientInput.readLine();
        String clientUsername = data.split(":")[0];
        String clientMsg = data.split(":")[1].strip();
        if (clientMsg != null) {

          System.out.printf("%s: %s%n", clientUsername, clientMsg);
          // Turn around and output this data
          // to all other clients except the one
          // that sent us this information
          for (Socket s : socketList) {
            if (s != connectionSock) {
              DataOutputStream clientOutput = new DataOutputStream(s.getOutputStream());
              clientOutput.writeBytes(clientUsername + ": " + clientMsg + "\n");
            }
          }
        } else {
          // Connection was lost
          System.out.println("Closing connection for socket " + connectionSock);
          // Remove from arraylist
          socketList.remove(connectionSock);
          connectionSock.close();
          break;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      // Remove from arraylist
      socketList.remove(connectionSock);
    }
  }
} // ClientHandler for MtServer.java
