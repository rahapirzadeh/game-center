package networking;

import java.io.*;
import java.net.Socket;

public class Client { //reference: https://github.com/ChapmanCPSC353/mtchat

  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;

  public Client(String serverHostname) throws IOException {
    clientSocket = new Socket(serverHostname, 7654);
    in = new BufferedReader(new InputStreamReader(System.in));
    out = new PrintWriter(clientSocket.getOutputStream(), true);
  }

  public void play() throws IOException {
    System.out.println("Connection made.");

      // Start a thread to listen and display data sent to the server
      ClientListener listener = new ClientListener(clientSocket);
      new Thread(listener).start();

      String clientInput;
      while ((clientInput = in.readLine()) != null) {
        out.println(clientInput);
      }
  }

  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("Pass in the server IP as a command line argument");
      return;
    }
    Client client = new Client(args[0]);
    client.play();
  }
}
