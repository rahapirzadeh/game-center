import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class Client { //reference: https://github.com/ChapmanCPSC353/mtchat
  public static void main(String[] args) throws IOException {
    System.out.println("What would you like to be your username?");
    String username = getUserInput();
    System.out.println("What is the IP of the host you'd like to connect to?");
    String hostname = getUserInput();
    int port = 7654;

    System.out.printf("Connecting to %s on port %d...%n", hostname, port);
    try {
      Socket connectionSock = new Socket(hostname, port);

      DataOutputStream serverOutput = new DataOutputStream(connectionSock.getOutputStream());
      System.out.println("Connection made.");

      // Start a thread to listen and display data sent by the server
      ClientListener listener = new ClientListener(connectionSock);
      Thread theThread = new Thread(listener);
      theThread.start();

      // Read input from the keyboard and send it to everyone else.
      // Quit with CTRL+C
      //TODO add exit functionality/cmd
      while(true) {
        String msg = getUserInput();
        serverOutput.writeBytes(username + ": " + msg + "\n");
      }
    }
    catch(IOException e) {
      System.out.println(e.getMessage());
    }
  }

  public static String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input;
  }
}
