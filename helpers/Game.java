package helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Game implements Runnable {

  protected static final int WAITING = 0;
  protected static final int PLAYER1_RESPONDED = 1;
  protected static final int PLAYER2_RESPONDED = 2;

  protected int state = WAITING;
  protected Turn currTurn;
  protected Player player1;
  protected Player player2;
  protected ArrayList<Player> playerList;

  /** method to pass in one player.*/
  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(player1);
    this.playerList.add(player2);
  }

  /** method to pass in both players.*/
  public Game(Player p1, Player p2) {
    this.player1 = p1;
    this.player2 = p2;
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(p1);
    this.playerList.add(p2);
  }

  /** method to pass in both players.*/
  public abstract void run();

  /** method to switch player turns.*/
  public void switchTurn() {
    Player playerWithNextTurn;
    if (getPlayerWithCurrTurn().getPlayerID() == 1) {
      playerWithNextTurn = getPlayer2();
    } else {
      playerWithNextTurn = getPlayer1();
    }
    setCurrTurn(new Turn(playerWithNextTurn));
  }

  /** method to set the current turn .*/
  public void setCurrTurn(Turn turn) {
    this.currTurn = turn;
  }

  /** method to get the current turn.*/
  public Turn getCurrTurn() {
    return this.currTurn;
  }

  /** method to get player 1.*/
  public Player getPlayer1() {
    return player1;
  }

  /** method to set player 1.*/
  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  /** method to get player 2.*/
  public Player getPlayer2() {
    return player2;
  }

  /** method to set player 2.*/
  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  /** method to get the player with the current turn.*/
  public Player getPlayerWithCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer1();
    }
    return getPlayer2();
  }

  /** method to get the player without the current turn.*/
  public Player getPlayerWithoutCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer2();
    }
    return getPlayer1();
  }

  /** method to print whose turn.*/
  public void printWhoseTurn() {
    printlnToPlayerWithCurrTurn("Your turn: ");
    printlnToPlayerWithoutCurrTurn("Opponent's turn..");
  }

  /** method to play one round of the game.*/
  public abstract void tick() throws IOException;

  /** method to print to all players.*/
  public void printlnToAllPlayers(String s) {
    printlnToPlayer(s, player1);
    printlnToPlayer(s, player2);
  }

  /** method to print to player with current turn.*/
  public void printlnToPlayerWithCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithCurrTurn());
  }

  /** method to print to player without current turn.*/
  public void printlnToPlayerWithoutCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithoutCurrTurn());
  }

  /** method to print to specified player.*/
  public void printlnToPlayer(String s, Player p) {
    if (p.getPlayerID() == 1) {
      getPlayer1().getOutToPlayer().println(s);
    } else {
      getPlayer2().getOutToPlayer().println(s);
    }
  }

  /** method to get user input.*/
  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input.strip();
  }

  /** method to get input form user with current turn.*/
  public String getInputFromPlayerWithCurrTurn() {
    String playerInput = "";
    try {
      playerInput = getPlayerWithCurrTurn().getInFromPlayer().readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return playerInput;
  }

  /** method to get input from player 1.*/
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

  /** method to get input from player 2.*/
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

  /** method to ask host to playe again.*/
  public String promptUserPlayAgain() {
    printlnToPlayer("Would you like to play again?"
        + "Enter 'y' to play again or 'n' to exit.", player1);
    return getInputFromPlayer1();
  }

  /** method to pomt enter key.*/
  public static void promptEnterKey() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }
}
