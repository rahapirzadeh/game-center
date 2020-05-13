package games;

import helpers.FileIO;
import helpers.Game;
import helpers.Player;
import helpers.StringManipulation;
import java.io.IOException;
import java.util.Scanner;

/**
* This is a multi player Rock Paper Scissors program.
*/
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

  /**
   * Constructs a new dual-player game instance of RPS using {@code p1} and {@code p2}.
   * @param p1
   *        Player 1
   * @param p2
   *        Player 2
   */
  public RPS(Player p1, Player p2) {
    super(p1, p2);
    currPlay = "";
    firstGame = true;
  }

  /**
   * Starts instance of Hangman game.
   */
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

  /**
  * prints welcome message to players.
  */
  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  /** new round method.*/
  public void newRound() {
    firstGame = false;
    run();
  }

  /**
   * Gets player 1 inputs.
   * @return player 1 input
   * @throws IOException when player input cannot be reached
   */
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

  /**
  * Gets player 2 inputs.
  * @return player 2 input
  * @throws IOException when player input cannot be reached
  */
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

  /**
  * Checks players input.
  * @param input
  *        the players input
  * @return {@code true} if string equals to one of the choices; {@code false} otherwise
  */
  public static boolean checkInput(String input) {
    if (input.equals(ROCK) || input.equals(PAPER) || input.equals(SCISSORS)) {
      return true;
    }
    return false;
  }

  /**
   * Returns whether the player has/have won.
   * A player wins if their play beats their opponents.
   * Player wins increments
   */
  /**
   * Returns whether the player has/have won. A player wins if their play beats their opponent's.
   * Increments player's wins by 1.
   * @param user1Move
   *        player 1's move (r, p, or s)
   * @param user2Move
   *        player 2's move (r, p, or s)
   */
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

  /**
   * Plays through one round of RPS game.
   * @throws IOException wwhen player input cannot be reached
   */
  public void tick() throws IOException {
    hasPlayerWon(getUser1Move(), getUser2Move());
    printlnToAllPlayers("Player 1 scores: " + player1wins);
    printlnToAllPlayers("Player 2 scores: " + player2wins);
  }
}
