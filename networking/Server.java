package networking;

import games.Hangman;
import games.RPS;
import games.TicTacToe;
import helpers.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

  private Player player1 = new Player(1);
  private Player player2 = new Player(2);
  private BufferedReader serverIn;
  private Socket clientSocket;

  private ArrayList<Socket> socketList = new ArrayList<Socket>();
  private String gameMode = NC;

  public Server() {}

  public void getConnection() throws IOException {
    try (
        ServerSocket server = new ServerSocket(7654);
    ) {
      serverIn = new BufferedReader(new InputStreamReader(System.in));
      System.out.printf("Hosting a game? Tell your friends to connect to %s%n", getPublicIP());

      promptNewGame();

      System.out.println("Waiting for players to connect on port 7654.");

      // Wait for a connection from the client
      while (true) {
        // Accept connection from client
        clientSocket = server.accept();
        socketList.add(clientSocket);
        System.out.println("Received connection.");

        setupPlayers();
        play();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setupPlayers() throws IOException {
    PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

    if (socketList.size() == 1) {
      player1.setUsername("Player 1");
      player1.setOutFromPlayer(clientOut);
      player1.setInFromPlayer(clientIn);
    } else if (socketList.size() == 2) {
      player2.setUsername("Player 2");
      player2.setOutFromPlayer(clientOut);
      player2.setInFromPlayer(clientIn);
    }
  }
  public void promptNewGame() throws IOException {
    System.out.println("What game would you like to play? \nEnter 'rps' for rock, paper, "
        + "scissors, 'ttt' for tic tac toe, or 'hm' for hangman. \nType 'exit' to stop playing.");
    selectGameMode(serverIn.readLine());
  }

  public void play() {
    if (socketList.size() == 2) {
      // Start game and handle game logic on server
      startGame(player1, player2);
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
    } else if (input.equalsIgnoreCase(HM)) {
      this.gameMode = HM;
      System.out.println("Host chose Hangman.");
    } else if (input.equalsIgnoreCase(EXIT)) {
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
      TicTacToe ttt = new TicTacToe(p1, p2);
      ttt.run();
    } else {
      Hangman hm = new Hangman(p1, p2);
      hm.run();
    }
  }

  public static void main(String[] args) throws IOException {
    new Server().getConnection();
  }
}
