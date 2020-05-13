package helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Game class implements the functionalty for the games.
 */
public abstract class Game implements Runnable {

  protected static final int WAITING = 0;
  protected static final int PLAYER1_RESPONDED = 1;
  protected static final int PLAYER2_RESPONDED = 2;

  protected int state = WAITING;
  protected Turn currTurn;
  protected Player player1;
  protected Player player2;
  protected ArrayList<Player> playerList;

  /**
  * Constructs a new instance of game.
  */
  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(player1);
    this.playerList.add(player2);
  }

  /**
   * Constructs a new instance of game using {@code p1} and {@code p2}.
   * @param p1
   *        Player 1
   * @param p2
   *        Player 2
   */
  public Game(Player p1, Player p2) {
    this.player1 = p1;
    this.player2 = p2;
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(p1);
    this.playerList.add(p2);
  }

  /**
   *Constructs a new single-player game instance of Hangman.
   */
  public abstract void run();

  /**
   * Switches player turns.
   */
  public void switchTurn() {
    Player playerWithNextTurn;
    if (getPlayerWithCurrTurn().getPlayerID() == 1) {
      playerWithNextTurn = getPlayer2();
    } else {
      playerWithNextTurn = getPlayer1();
    }
    setCurrTurn(new Turn(playerWithNextTurn));
  }

  /**
   * Sets the current player turn.
   * @param turn
   *        current turn of player
   */
  public void setCurrTurn(Turn turn) {
    this.currTurn = turn;
  }

  /**
   * Gets the current player turn.
   *
   * @return current turn
   */
  public Turn getCurrTurn() {
    return this.currTurn;
  }

  /**
   * Gets player 1.
   *
   * @return player 1
   */
  public Player getPlayer1() {
    return player1;
  }

  /**
   * Construct new instance to set player 1.
   * @param player1
   *        player 1's object
   */
  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  /**
   * Gets player 2.
   * @return player 2
   */
  public Player getPlayer2() {
    return player2;
  }

  /**
   * Construct new instance to set player 2.
   * @param player2
   *        player 2's object
   */
  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  /**
   * Method to get the player with the current turn.
   * @return getPlayer
   */
  public Player getPlayerWithCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer1();
    }
    return getPlayer2();
  }

  /**
   * Method to get the player with the current turn.
   * @return getPlayer
   */
  public Player getPlayerWithoutCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer2();
    }
    return getPlayer1();
  }

  /**
   * Prints to the players if its their turn or their opponents.
   */
  public void printWhoseTurn() {
    printlnToPlayerWithCurrTurn("Your turn: ");
    printlnToPlayerWithoutCurrTurn("Opponent's turn..");
  }

  /**
   * Plays through one round of a game.
   * @throws IOException wwhen player input cannot be reached
   */
  public abstract void tick() throws IOException;

  /**
   * Prints line to all players.
   * @param s
   *        string being printed
   */
  public void printlnToAllPlayers(String s) {
    printlnToPlayer(s, player1);
    printlnToPlayer(s, player2);
  }

  /**
   * Prints line to player with current turn.
   * @param s
   *        string being printed
   */
  public void printlnToPlayerWithCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithCurrTurn());
  }

  /**
   * Prints line to player without current turn.
   * @param s
   *        string being printed
   */
  public void printlnToPlayerWithoutCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithoutCurrTurn());
  }

  /**
   * Prints line to specified player.
   * @param s
   *        string being printed
   * @param p
   *        player being printed to
   */
  public void printlnToPlayer(String s, Player p) {
    if (p.getPlayerID() == 1) {
      getPlayer1().getOutToPlayer().println(s);
    } else {
      getPlayer2().getOutToPlayer().println(s);
    }
  }

  /**
   * Gets the user input.
   * @return input.strip()
   */
  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input;
  }

  /**
   * Gets the user input form user with current turn.
   * @return playerInput
   */
  public String getInputFromPlayerWithCurrTurn() {
    String playerInput = "";
    try {
      playerInput = getPlayerWithCurrTurn().getInFromPlayer().readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return playerInput;
  }

  /**
   * Gets the input from player 1.
   * @return playerInput
   */
  public String getInputFromPlayer1() {
    String playerInput = "";
    try {
      playerInput = getPlayer1().getInFromPlayer().readLine();
    } catch (IOException e) {
      System.out.println("Error when getting player 1 input: ");
      e.printStackTrace();
    }
    return playerInput;
  }

  /**
   * Gets the input from player 2.
   * @return playerInput
   */
  public String getInputFromPlayer2() {
    String playerInput = "";
    try {
      playerInput = getPlayer2().getInFromPlayer().readLine();
    } catch (IOException e) {
      System.out.println("Error when getting player 1 input: ");
      e.printStackTrace();
    }
    return playerInput;
  }
  
  /**
   * Asks player 1 to play again.
   * @return getInputFromPlayer1()
   */
  public String promptUserPlayAgain() {
    printlnToPlayer("Would you like to play again?"
        + "Enter 'y' to play again or 'n' to exit.", player1);
    return getInputFromPlayer1();
  }
}
