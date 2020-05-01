package games;
import java.util.Scanner;

public class RPS {
  public static final String ROCK = "R";
  public static final String PAPER = "P";
  public static final String SCISSORS = "S";
  public static final String NO = "N";
  public static final String YES = "Y";
  static int player1wins = 0;
  static int player2wins = 0;

  /** Main method. */
  public static void main(String[] args) {
    rps();
  }

  /** Get the game Result. */
  public static void getResult(String user1Move, String user2Move) {

    if (user1Move.equals(user2Move)) {
      System.out.println("It's a tie!\n");

    } else if (user1Move.equals(ROCK)) {
      if (user2Move.equals(SCISSORS)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Rock crushes scissors.\n");
        player1wins++;
      } else if (user2Move.equals(PAPER)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Paper eats rock.\n");
        player2wins++;
      }
    } else if (user1Move.equals(PAPER)) {
      if (user2Move.equals(ROCK)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Paper eats rock.\n");
        player1wins++;
      } else if (user2Move.equals(SCISSORS)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Scissor cuts paper.\n");
        player2wins++;
      }
    } else if (user1Move.equals(SCISSORS)) {
      if (user2Move.equals(PAPER)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Scissor cuts paper.\n");
        player1wins++;
      } else if (user2Move.equals(ROCK)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Rock crushes scissors.\n");
        player2wins++;
      }
    }
  }

  /** User 1 move. */
  public static String getUser1Move() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nPlayer 1 choice: ");
    String input = scanner.next().toUpperCase();
    while (checkInput(input) == false) {
      System.out.println("Incorrect input player 1, try again: ");
      input = scanner.next().toUpperCase();
    }
    return input;
  }

  /** User 2 move. */
  public static String getUser2Move() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Player 2 choice: ");
    String input = scanner.next().toUpperCase();
    while (checkInput(input) == false) {
      System.out.println("Incorrect input player 2, try again: ");
      input = scanner.next().toUpperCase();
    }
    return input;
  }

  /** check user inputs. */
  public static boolean checkInput(String input) {
    if (input.equals(ROCK) || input.equals(PAPER) || input.equals(SCISSORS)) {
      return true;
    }
    return false;
  }

  /** rps menu. */
  public static void rps() {
    System.out.println("Rock, Paper, Scissors!\n"
        + "Please enter a move.\n"
        + "Rock = R, Paper= P, and Scissors = S\n");
    do {
      getResult(getUser1Move(), getUser2Move());
      System.out.println("Player 1 scores: " + player1wins);
      System.out.println("Player 2 scores: " + player2wins);
      System.out.println("---Want to play again? Y/N---");
      Scanner scanner = new Scanner(System.in);
      String play = scanner.next().toUpperCase();
      if (play.equals(NO)) {
        break;
      } else if (!play.equals(YES)) {
        System.out.println("Incorrect choice");
      }
    } while (true);
  }
}
