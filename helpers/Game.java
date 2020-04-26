package helpers;

import java.util.Scanner;

public abstract class Game {

  protected static final int WAITING = 0;
  protected static final int PLAYER1_RESPONDED = 1;
  protected static final int PLAYER2_RESPONDED = 2;

  protected int state = WAITING;
  protected Turn currTurn;
  protected Player player1;
  protected Player player2;
  protected String outputLine;

  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.currTurn = new Turn(player1);
  }

  public abstract void play();

  public void switchTurn() {
    if (getCurrTurn().getWhoseTurn().getPlayerID() == 1) {
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

  public String getOutputLine() {
    return outputLine;
  }

  public void setOutputLine(String outputLine) {
    this.outputLine = outputLine;
  }

  public void printWhoseTurn() {
    if (getCurrTurn().getWhoseTurn().getPlayerID() == 1) {
      System.out.print(player1.getUsername() + "'s helpers.Turn: ");
    } else {
      System.out.print(player2.getUsername() + "'s helpers.Turn: ");
    }
  }

  public abstract String tick();

  public abstract boolean hasPlayerWon();

  public abstract boolean hasPlayerLost();

  public void returnToLobby() {
    new Lobby().startLobby();
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
