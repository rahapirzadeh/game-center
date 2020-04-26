package helpers;

import games.*;
import java.util.Scanner;

public class Lobby {
  public static void main(String[] args) {
    Lobby l = new Lobby();
    l.startLobby();
  }

  private static final String RPS = "rps";
  private static final String HM = "hm";
  private static final String TTT = "ttt";
  private static final String NC = "nc";
  private static final String EXIT = "exit";

  private String gameMode;

  public Lobby() {
    gameMode = NC;
  }

  public Lobby(String gameMode) {
    this.gameMode = gameMode;
  }

  public void startLobby() {
    String gameMode = promptGameToPlay();
    startGame(gameMode);
  }

  public String getGameMode() {
    return gameMode;
  }

  public void startGame(String gameMode) {
    if (gameMode.equalsIgnoreCase(RPS)) {
      gameMode = RPS;
      System.out.println("networking.Host chose Rock Paper Scissors.");
      RPS rps = new RPS();
      rps.rps();
    } else if (gameMode.equalsIgnoreCase(TTT)) {
      gameMode = TTT;
      System.out.println("networking.Host chose TicTacToe.");
      TicTacToe ttt = new TicTacToe();
      ttt.TicTacToe();
    } else if(gameMode.equalsIgnoreCase(HM)) {
      gameMode = HM;
      System.out.println("networking.Host chose games.Hangman.");
      Hangman hm = new Hangman();
      hm.play();
    } else if(gameMode.equalsIgnoreCase(EXIT)) {
      System.exit(0);
    } else {
      System.out.println("Sorry, I don't understand that command. Try again.");
      startLobby();
    }
  }

  public String promptGameToPlay() {
    System.out.println("What game mode would you like to play?" +
    "\nEnter 'rps' for rock, paper, scissors, 'ttt' for tic tac toe, or 'hm' for hangman."
        + "\nType 'exit' to stop playing.");
    return getUserInput();
  }

  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input.strip();
  }
}
