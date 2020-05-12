package games;

import helpers.FileIO;
import helpers.Game;
import helpers.Player;
import helpers.StringManipulation;
import java.io.IOException;
import java.util.Scanner;

public class RPS extends Game {

  public static final String welcomeMessage =
      "Welcome to Rock, Paper, Scissors!\n"
      + "Please enter a move.\n"
      + "Rock = R, Paper= P, and Scissors = S\n";
  public static final String ROCK = "R";
  public static final String PAPER = "P";
  public static final String SCISSORS = "S";
  public static final String NO = "N";
  public static final String YES = "Y";
  private boolean firstGame;
  static int player1wins = 0;
  static int player2wins = 0;
  private String currPlay;

  /** method to pass in both players.*/
  public RPS(Player p1, Player p2) {
    super(p1, p2);
    currPlay = "";
    firstGame = true;
  }

  /** runs rps.*/
  public void run() {
    if (firstGame) {
      printWelcomeMessage();
    }
    try {
      tick();
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (promptUserPlayAgain().equalsIgnoreCase("y")) {
      System.out.println("ok");
      newRound();
    } else {
      System.out.println("bye");
    }
  }

  /** prints welcome message.*/
  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  /** new round method.*/
  public void newRound() {
    firstGame = false;
    run();
  }

  /** gets user 1 inputs.*/
  public String getUser1Move() throws IOException {
    printlnToPlayer("\nPlayer 1 choice: ", player1);
    String input = getInputFromPlayer1().toUpperCase();
    printlnToPlayer("you picked " + input, player1);
    while (checkInput(input) == false) {
      printlnToPlayer("Incorrect input player 1, try again: ", player1);
      input = getInputFromPlayer1().toUpperCase();
    }
    return input;
  }

  /** gets user 2 inputs.*/
  public String getUser2Move() throws IOException {
    printlnToPlayer("\nPlayer 2 choice: ", player2);
    String input = getInputFromPlayer2().toUpperCase();
    printlnToPlayer("you picked " + input, player2);
    while (checkInput(input) == false) {
      printlnToPlayer("Incorrect input player 2, try again: ", player2);
      input = getInputFromPlayer2().toUpperCase();
    }
    return input;
  }

  /** check user inputs.*/
  public static boolean checkInput(String input) {
    if (input.equals(ROCK) || input.equals(PAPER) || input.equals(SCISSORS)) {
      return true;
    }
    return false;
  }

  /** Get the game Result.*/
  public void hasPlayerWon(String user1Move, String user2Move) {

    if (user1Move.equals(user2Move)) {
      printlnToAllPlayers("It's a tie!\n");

    } else if (user1Move.equals(ROCK)) {
      if (user2Move.equals(SCISSORS)) {
        printlnToAllPlayers("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Rock crushes scissors.\n");
        player1wins++;
      } else if (user2Move.equals(PAPER)) {
        printlnToAllPlayers("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Paper eats rock.\n");
        player2wins++;
      }
    } else if (user1Move.equals(PAPER)) {
      if (user2Move.equals(ROCK)) {
        printlnToAllPlayers("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Paper eats rock.\n");
        player1wins++;
      } else if (user2Move.equals(SCISSORS)) {
        printlnToAllPlayers("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Scissor cuts paper.\n");
        player2wins++;
      }
    } else if (user1Move.equals(SCISSORS)) {
      if (user2Move.equals(PAPER)) {
        printlnToAllPlayers("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Scissor cuts paper.\n");
        player1wins++;
      } else if (user2Move.equals(ROCK)) {
        printlnToAllPlayers("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Rock crushes scissors.\n");
        player2wins++;
      }
    }
  }

  /** method to play one round of the game.*/
  public void tick() throws IOException {
    hasPlayerWon(getUser1Move(), getUser2Move());
    printlnToAllPlayers("Player 1 scores: " + player1wins);
    printlnToAllPlayers("Player 2 scores: " + player2wins);
  }
}
