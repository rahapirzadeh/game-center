import java.util.ArrayList;
import java.util.Scanner;

public abstract class Game {

  private static final String PLAYER1 = "Player 1";
  private static final String PLAYER2 = "Player 2";

  private Turn currTurn;
  private Player player1;
  private Player player2;
  private ArrayList<Turn> turnsList;

  public Game() {
    this.player1 = new Player();
    this.player2 = new Player();
    this.turnsList = new ArrayList<Turn>();
    this.currTurn = new Turn(player1);
  }

  public abstract void play();

  public void switchTurn() {
    if(getCurrTurn().getWhoseTurn().getPlayerID() == 1) {
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

  public void printWhoseTurn() {
    if(getCurrTurn().getWhoseTurn().getPlayerID() == 1) {
      System.out.print("Player 1's Turn: ");
    } else {
      System.out.print("Player 2's Turn: ");
    }
  }

  public abstract void promptNextMove();

  public abstract boolean hasPlayerWon();

  public abstract boolean hasPlayerLost();

  public void returnToLobby() {
    new Lobby().startLobby();
  }

  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input.strip().toLowerCase();
  }

  public String promptUserPlayAgain() {
    System.out.println("Would you like to play again? Enter 'y' to play again or 'n' to exit.");
    return getUserInput().toLowerCase();
  }
}
