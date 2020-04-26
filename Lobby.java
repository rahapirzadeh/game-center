import java.util.ArrayList;
import java.util.Scanner;

public class Lobby {
  public static void main(String[] args) {
    Lobby l = new Lobby();
    l.startGame();
  }

  private static final String RPS = "rps";
  private static final String HM = "hm";
  private static final String TTT = "ttt";
  private static final String NOT_CHOSEN = "nc";

  private String gameMode;

  public Lobby() {
    gameMode = NOT_CHOSEN;
  }

  public void startLobby() {
    this.gameMode = promptGameToPlay();
    startGame();
  }

  public void startGame() {
    setGameMode(this.gameMode);
  }

  public void setGameMode(String selectedMode) {
    if(!selectedMode.equals(RPS) && !selectedMode.equals(TTT) && !selectedMode.equals(HM)) {
      setGameMode(promptGameToPlay());
    } else {
      gameMode = selectedMode;
      if(selectedMode.equals(RPS)) {
        RPS rps = new RPS();
        rps.rps();
      } else if(selectedMode.equals(TTT)) {
        TicTacToe ttt = new TicTacToe();
        ttt.TicTacToe();
      } else {
        Hangman hm = new Hangman();
        hm.play();
      }
    }
  }

  public String promptGameToPlay() {
    System.out.println("What game mode would you like to play?" +
    "\nEnter 'rps' for rock, paper, scissors, 'ttt' for tic tac toe, or 'hm' for hangman.");
    return getUserInput().toLowerCase();
  }

  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return scanner.next().toLowerCase();
  }
}
