package networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The client class allows players to connect to a server given a hostname and port.
 *
 * <p>Allows players to send input to the server, which will be used as commands
 * to run the logic flow of the game server.
 */
public class Client { //reference: https://github.com/ChapmanCPSC353/mtchat

  private String serverHostname;
  private int serverPort;
  private Socket clientSocket;
  private BufferedReader in;
  private PrintWriter out;

  /**
   * Constructs new instance of {@code Client} and sets a server hostname to connect to.
   * @param serverHostname
   *        hostname of server client will attempt to connect to
   * @throws IOException  when server hostname is unreachable
   */
  public Client(String serverHostname, int serverPort) throws IOException {
    this.serverHostname = serverHostname;
    this.serverPort = serverPort;
  }

  /**
   * Attempts to connect to server given hostname and port.
   */
  public void connect() {
    try {
      System.out.println("Connecting to server " + serverHostname + " on port " + serverPort);
      clientSocket = new Socket(serverHostname, serverPort);
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

  /**
   * Sets input and output of new client.
   */
  public void setupClientIO() {
    try {
      in = new BufferedReader(new InputStreamReader(System.in));
      out = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Receives and prints client input while client is sending input.
   * @throws IOException  when client input cannot be reached
   */
  public void start() throws IOException {
    String clientInput;
    while ((clientInput = in.readLine()) != null) {
      out.println(clientInput);
    }
  }

  /**
   * Attempts to connect client to server and set up client IO.
   * @param args
   *        command line arguments
   * @throws IOException  when client is unable to connect to server
   */
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Pass in the server IP and port as command line arguments, "
              + "separated by a space.");
      return;
    }
    Client client = new Client(args[0], Integer.parseInt(args[1]));
    client.connect();
    client.setupClientIO();
    client.start();
  }
}
