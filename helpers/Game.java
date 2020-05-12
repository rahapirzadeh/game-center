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

  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(player1);
    this.playerList.add(player2);
  }

  public Game(Player p1, Player p2) {
    this.player1 = p1;
    this.player2 = p2;
    this.currTurn = new Turn(player1);
    this.playerList = new ArrayList<Player>();
    this.playerList.add(p1);
    this.playerList.add(p2);
  }

  public abstract void run();

  public void switchTurn() {
    Player playerWithNextTurn;
    if (getPlayerWithCurrTurn().getPlayerID() == 1) {
      playerWithNextTurn = getPlayer2();
    } else {
      playerWithNextTurn = getPlayer1();
    }
    setCurrTurn(new Turn(playerWithNextTurn));
  }

  public void setCurrTurn(Turn turn) {
    this.currTurn = turn;
  }

  public Turn getCurrTurn() {
    return this.currTurn;
  }

  public Player getPlayer1() {
    return player1;
  }

  public void setPlayer1(Player player1) {
    this.player1 = player1;
  }

  public Player getPlayer2() {
    return player2;
  }

  public void setPlayer2(Player player2) {
    this.player2 = player2;
  }

  public Player getPlayerWithCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer1();
    }
    return getPlayer2();
  }

  public Player getPlayerWithoutCurrTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      return getPlayer2();
    }
    return getPlayer1();
  }

  public void printWhoseTurn() {
    printlnToPlayerWithCurrTurn("Your turn: ");
    printlnToPlayerWithoutCurrTurn("Opponent's turn..");
  }

  public abstract void tick() throws IOException;

  //public abstract boolean hasPlayerWon();

  //public abstract boolean hasPlayerLost();

  public void printlnToAllPlayers(String s) {
    printlnToPlayer(s, player1);
    printlnToPlayer(s, player2);
  }

  public void printlnToPlayerWithCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithCurrTurn());
  }

  public void printlnToPlayerWithoutCurrTurn(String s) {
    printlnToPlayer(s, getPlayerWithoutCurrTurn());
  }

  public void printlnToPlayer(String s, Player p) {
    if(p.getPlayerID() == 1) {
      getPlayer1().getOutToPlayer().println(s);
    } else {
      getPlayer2().getOutToPlayer().println(s);
    }
  }

  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input.strip();
  }

  public String getInputFromPlayerWithCurrTurn() {
    String playerInput = "";
    try {
      playerInput = getPlayerWithCurrTurn().getInFromPlayer().readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return playerInput;
  }

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

  public String promptUserPlayAgain() {
    printlnToPlayer("Would you like to play again? Enter 'y' to play again or 'n' to exit.", player1);
    return getInputFromPlayer1();
  }

  public static void promptEnterKey() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }
}
