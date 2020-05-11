package networking;

import games.*;
import helpers.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class Server { //reference: https://github.com/ChapmanCPSC353/mtchat

  private static final String RPS = "rps";
  private static final String HM = "hm";
  private static final String TTT = "ttt";
  private static final String NC = "nc";
  private static final String EXIT = "exit";

  private ArrayList<Socket> socketList;
  private String gameMode = NC;

  public Server() {
    socketList = new ArrayList<Socket>();
  }

  private void getConnection() throws IOException {
    BufferedReader serverIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.printf("Looking to host a game? Tell your friends to connect to %s%n", getPublicIP());

    System.out.println("What game would you like to play? \nEnter 'rps' for rock, paper, scissors, 'ttt' for tic tac toe, or 'hm' for hangman.");
    selectGameMode(serverIn.readLine());

    System.out.println("Waiting for players to connect on port 7654.");

    // Wait for a connection from the client
    try {
      ServerSocket serverSocket = new ServerSocket(7654);

      Player p1 = new Player(1);
      Player p2 = new Player(2);

      while (true) {
        // Accept connection from client
        Socket clientSocket = serverSocket.accept();
        System.out.println("okkk"); //eceived connection.

        PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        socketList.add(clientSocket);
        System.out.println(socketList.size());
        String playerUsername = clientIn.readLine();

        if (socketList.size() == 1) {
          p1.setUsername(playerUsername);
          p1.setOutFromPlayer(clientOut);
          p1.setInFromPlayer(clientIn);
          System.out.println("hi1");
        } else if (socketList.size() == 2){
          p2.setUsername(playerUsername);
          p2.setOutFromPlayer(clientOut);
          p2.setInFromPlayer(clientIn);
          System.out.println("hi2");
        }

        // Send client socket and updated arraylist of sockets to ClientHandler
        ClientHandler handler = new ClientHandler(clientSocket, this.socketList);
        new Thread(handler).start();

        if (socketList.size() == 2) {
          // Start game and handle game logic on server
          startGame(p1, p2);
        }
      }

    } catch (IOException e) {
      System.out.println("Exception caught when listening for a connection.");
      System.out.println(e.getMessage());
    }
  }

  public static String getPublicIP() throws IOException { //reference: https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
    URL whatsMyIP = new URL("http://checkip.amazonaws.com");
    BufferedReader br = new BufferedReader(new InputStreamReader(whatsMyIP.openStream()));
    return br.readLine();
  }

  public void selectGameMode(String input) {
    if (input.equalsIgnoreCase(RPS)) {
      this.gameMode = RPS;
      System.out.println("Host chose Rock Paper Scissors.");
    } else if (input.equalsIgnoreCase(TTT)) {
      this.gameMode = TTT;
      System.out.println("Host chose TicTacToe.");
    } else if(input.equalsIgnoreCase(HM)) {
      this.gameMode = HM;
      System.out.println("Host chose Hangman.");
    } else if(input.equalsIgnoreCase(EXIT)) {
      System.exit(0);
    } else {
      System.out.println("Sorry, I don't understand. Try again.");
      selectGameMode(NC);
    }
  }

  public void startGame(Player p1, Player p2) {
    if (this.gameMode.equals(RPS)) {
      RPS rps = new RPS(p1, p2);
      rps.run();
    } else if (this.gameMode.equals(TTT)) {
      TicTacToe ttt = new TicTacToe();
      ttt.TicTacToe();
    } else {
      Hangman hm = new Hangman(p1, p2);
      hm.run();
    }
  }

  public void setGameMode(String gameMode) {
    this.gameMode = gameMode;
  }

  public static void main(String[] args) throws IOException {
    new Server().getConnection();
  }
}
