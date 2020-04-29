package helpers;

import java.io.IOException;
import java.util.Scanner;

public abstract class Game implements Runnable {

  protected static final int WAITING = 0;
  protected static final int PLAYER1_RESPONDED = 1;
  protected static final int PLAYER2_RESPONDED = 2;

  protected int state = WAITING;
  protected Turn currTurn;
  protected Player player1;
  protected Player player2;

  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.currTurn = new Turn(player1);
  }

  public abstract void run();

  public void switchTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      setCurrTurn(new Turn(player2));
    } else {
      setCurrTurn(new Turn(player1));
    }
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

  public void printWhoseTurn() {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      System.out.print(player1.getUsername() + "'s helpers.Turn: ");
    } else {
      System.out.print(player2.getUsername() + "'s helpers.Turn: ");
    }
    printToPlayerWithCurrTurn("Your helpers.Turn: ");
  }

  public abstract void tick();

  public abstract String getMove() throws IOException;

  public abstract boolean hasPlayerWon();

  public abstract boolean hasPlayerLost();

  public void printToAllPlayers(String s) {
    printToPlayer(s, player1);
    printToPlayer(s, player2);
  }

  public void printlnToAllPlayers(String s) {
    printlnToPlayer(s, player1);
    printlnToPlayer(s, player2);
  }

  public void printToPlayerWithCurrTurn(String s) {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      player1.getOutFromPlayer().print(s);
    } else {
      player2.getOutFromPlayer().print(s);
    }
  }

  public void printlnToPlayerWithCurrTurn(String s) {
    if (getCurrTurn().getPlayer().getPlayerID() == 1) {
      printlnToPlayer(s, player1);
    } else {
      printlnToPlayer(s, player2);
    }
  }

  public void printToPlayer(String s, Player p) {
    p.getOutFromPlayer().print(s);
  }
  public void printlnToPlayer(String s, Player p) {
    if(p.getPlayerID() == 1) {
      player1.getOutFromPlayer().println(s);
    } else {
      player2.getOutFromPlayer().println(s);
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

  public String promptUserPlayAgain() {
    System.out.println("Would you like to play again? Enter 'y' to play again or 'n' to exit.");
    return getUserInput();
  }
}
