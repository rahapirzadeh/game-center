import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Host { //reference: https://github.com/ChapmanCPSC353/mtchat
  private ArrayList<Socket> socketList;

  public Host() {
    socketList = new ArrayList<Socket>();
  }

  public ArrayList<Socket> getSocketList() {
    return socketList;
  }

  private void getConnection() {
    // Wait for a connection from the client
    try {
      System.out.printf("Want to host a game? Tell your friends to connect to %s%n", getPublicIP());
      System.out.println("Waiting for client connections on port 7654.");
      ServerSocket serverSock = new ServerSocket(7654);
      // Quit with CTRL+C
      //TODO add exit functionality/cmd
      while (true) {
        Socket connectionSocket = serverSock.accept();
        // Add this socket to the list
        socketList.add(connectionSocket);
        // Send to ClientHandler the socket and arraylist of all sockets
        ClientHandler handler = new ClientHandler(connectionSocket, getSocketList());
        Thread theThread = new Thread(handler);
        theThread.start();
      }
      // Will never get here, but if the above loop is given
      // an exit condition then we'll go ahead and close the socket
      // serverSock.close();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static String getPublicIP() throws IOException { //reference: https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
    URL whatsMyIP = new URL("http://checkip.amazonaws.com");
    BufferedReader br = new BufferedReader(new InputStreamReader(whatsMyIP.openStream()));
    return br.readLine();
  }

  public static void main(String[] args) {
    Host h = new Host();
    h.getConnection();
  }
}
