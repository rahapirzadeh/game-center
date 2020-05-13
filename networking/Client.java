package networking;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client { //reference: https://github.com/ChapmanCPSC353/mtchat

  private String serverHostname;
  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;

  public Client(String serverHostname) throws IOException {
    this.serverHostname = serverHostname;
  }

  /** method to connect client to server.*/
  public void connect() {
    try {
      clientSocket = new Socket(serverHostname, 7654);
      System.out.println("Connection made.");
      // Start a thread to listen and display data sent to the server
      ClientListener listener = new ClientListener(clientSocket);
      new Thread(listener).start();
    } catch (UnknownHostException e) {
      System.out.println("Unknown host " + serverHostname + ": " + e.getStackTrace());
    } catch (IOException e) {
      System.out.println("Unknown error: " + e.getStackTrace());
    }
  }

  /** method to start up client .*/
  public void setupClientIO() {
    try {
      in = new BufferedReader(new InputStreamReader(System.in));
      out = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** method to start recieving client input.*/
  public void start() throws IOException {
    String clientInput;
    while ((clientInput = in.readLine()) != null) {
      out.println(clientInput);
    }
  }

  /** main method for client.*/
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("Pass in the server IP as a command line argument");
      return;
    }
    Client client = new Client(args[0]);
    client.connect();
    client.setupClientIO();
    client.start();
  }
}
