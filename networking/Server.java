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

/**
 * The server class starts and opens a server on a specified port, then allows 2 incoming
 * connections from clients, which represent players. The host starts the server and then can
 * share the IP to players who wish to connect.
 *
 * <p>All game logic is handled on the server, which is in charge of running the current game,
 * and the host may select a game mode or close the server as they wish.
 */
public class Server { //reference: https://github.com/ChapmanCPSC353/mtchat

  private static final String RPS = "rps";
  private static final String HM = "hm";
  private static final String TTT = "ttt";
  private static final String NC = "nc";
  private static final String EXIT = "exit";

  private int serverPort;
  private Player player1 = new Player(1);
  private Player player2 = new Player(2);
  private BufferedReader serverIn;
  private Socket clientSocket;

  private ArrayList<Socket> socketList = new ArrayList<Socket>();
  private String gameMode = NC;

  public Server(int serverPort) {
    this.serverPort = serverPort;
  }

  /**
   * Starts server and opens port for incoming connections.
   * @throws IOException  when server cannot be hosted on given port
   */
  public void startServer() throws IOException {
    try (
        ServerSocket server = new ServerSocket(serverPort);
    ) {
      serverIn = new BufferedReader(new InputStreamReader(System.in));
      System.out.printf("Hosting a game? Tell your friends to connect to %s on port %d%n",
              getPublicIP(), serverPort);

      promptForGameMode();

      System.out.println("Waiting for players to connect on port " + serverPort);

      // Wait for a connection from the client
      while (true) {
        acceptConnections(server);
        setupPlayers();
        play();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Accepts client connections to {@code server} and adds client socket to {@code socketList}.
   * @param server
   *        the server accepting connections
   * @throws IOException  when server cannot accept client connection
   */
  public void acceptConnections(ServerSocket server) throws IOException {
    clientSocket = server.accept();
    socketList.add(clientSocket);
    System.out.println("Received connection.");
  }

  /**
   * Set up client input and output streams and usernames for players 1 and 2.
   * @throws IOException  when inputs and outputs for players 1 and 2 cannot be set
   */
  public void setupPlayers() throws IOException {
    PrintWriter clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
    BufferedReader clientIn = new BufferedReader(
         new InputStreamReader(clientSocket.getInputStream()));

    if (socketList.size() == 1) {
      player1.setUsername("Player 1");
      player1.setOutToPlayer(clientOut);
      player1.setInFromPlayer(clientIn);
    } else if (socketList.size() == 2) {
      player2.setUsername("Player 2");
      player2.setOutToPlayer(clientOut);
      player2.setInFromPlayer(clientIn);
    }
  }

  /**
   * Prompts user to select game mode or exit the game.
   * @throws IOException  when input from server cannot be read
   */
  public void promptForGameMode() throws IOException {
    System.out.println("What game would you like to play? \nEnter 'rps' for rock, paper, "
        + "scissors, 'ttt' for tic tac toe, or 'hm' for hangman. \nType 'exit' to stop playing.");
    selectGameMode(serverIn.readLine());
  }

  /**
   * Starts selected game mode with player 1 and player 2 once both players have connected.
   *
   * <p>Game logic is handled on the server.
   */
  public void play() {
    if (socketList.size() == 2) {
      startGame(player1, player2);
    }
  }

  /**
   * Gets host IP address to share with clients looking to connect.
   * @return  the IP address of host
   * @throws IOException  when stream from url cannot be read
   */
  public static String getPublicIP() throws IOException { //reference: https://stackoverflow.com/questions/2939218/getting-the-external-ip-address-in-java
    URL whatsMyIP = new URL("http://checkip.amazonaws.com");
    BufferedReader br = new BufferedReader(new InputStreamReader(whatsMyIP.openStream()));
    return br.readLine();
  }

  /**
   * Selects and sets current gamemode and prints to server what game mode has been selected.
   * @param input
   *        the selected game mode
   */
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

  /**
   * Starts appropriate game given selected game mode.
   * @param p1
   *        player 1
   * @param p2
   *        player 2
   */
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

  /**
   * Starts server.
   * @param args
   *        command line arguments
   * @throws IOException  when server is unable to be started
   */
  public static void main(String[] args) throws IOException {
    new Server(Integer.parseInt(args[0])).startServer();
  }
}
