package games;
import java.util.Scanner;
import helpers.FileIO;
import helpers.Game;
import helpers.Player;
import helpers.StringManipulation;
import java.io.IOException;


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
  static int player1wins = 0;
  static int player2wins = 0;
  private boolean firstGame;
  private String currPlay;
  private int numRoundsPlayed;
  private int numRoundsWon;
  private int numRoundsLost;

  /** Main method.
  public static void main(String[] args) {
    rps();
  }*/

  /** Get the game Result.   if (promptUserPlayAgain().equalsIgnoreCase("y")) {
  public static boolean hasPlayerWon(String user1Move, String user2Move) {

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
  }*/

  /** User 1 move.
  public static String getUser1Move() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("\nPlayer 1 choice: ");
    String input = scanner.next().toUpperCase();
    while (checkInput(input) == false) {
      System.out.println("Incorrect input player 1, try again: ");
      input = scanner.next().toUpperCase();
    }
    return input;
  }*/

  /** User 2 move.
  public static String getUser2Move() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Player 2 choice: ");
    String input = scanner.next().toUpperCase();
    while (checkInput(input) == false) {
      System.out.println("Incorrect input player 2, try again: ");
      input = scanner.next().toUpperCase();
    }
    return input;
  }*/



  /** rps menu.
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
  }*/
  /*public static void getResult(String getPlayer1, String getPlayer2) {

    if (getPlayer1.equals(getPlayer2)) {
      System.out.println("It's a tie!\n");

    } else if (getPlayer1.equals(ROCK)) {
      if (getPlayer2.equals(SCISSORS)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Rock crushes scissors.\n");
        player1wins++;
      } else if (getPlayer2.equals(PAPER)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Paper eats rock.\n");
        player2wins++;
      }
    } else if (getPlayer1.equals(PAPER)) {
      if (getPlayer2.equals(ROCK)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Paper eats rock.\n");
        player1wins++;
      } else if (getPlayer2.equals(SCISSORS)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Scissor cuts paper.\n");
        player2wins++;
      }
    } else if (getPlayer1.equals(SCISSORS)) {
      if (getPlayer2.equals(PAPER)) {
        System.out.println("Player 1 Wins!!\n" + "Player 2 Looses...\n"
            + "Scissor cuts paper.\n");
        player1wins++;
      } else if (getPlayer2.equals(ROCK)) {
        System.out.println("Player 2 Wins!!\n" + "Player 1 Looses...\n"
            + "Rock crushes scissors.\n");
        player2wins++;
      }
    }
  }*/


  public RPS(Player p1, Player p2){
    super(p1, p2);
    currPlay ="";
    firstGame = true;
  }

  public void newRound() {
    firstGame = false;
    run();
  }

  public void run() {
    if (firstGame) {
      printWelcomeMessage();
    }
    try {
      tick();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //roundFinish();
    if (promptUserPlayAgain().equalsIgnoreCase("y")) {
      System.out.println("ok");
      //newRound();
    } else {
      //printGameStats();
      System.out.println("bye");
    }
  }

  /*public boolean hasPlayerWon() {
    return
  }

  public boolean hasPlayerLost() {
    return numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES;
  }

  public void roundFinish() {
    if (hasPlayerWon()) {
      playerWin();
    } else {
      //printGuessWord();
    }
    //incrementNumRoundsPlayed();
    //printRoundStats();
  }

  public void playerWin() {
    incrementWinStreak();
    incrementNumRoundsWon();
    //printWinMessage();
  }

  public void incrementWinStreak() {
    winStreak++;
  }

  public void incrementNumRoundsPlayed() {
    numRoundsPlayed++;
  }

  public void incrementNumRoundsWon() {
    numRoundsWon++;
  }

  public void incrementNumRoundsLost() {
    numRoundsLost++;
  }*/
  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  /*public void setCurrPlay1(String user1Move) {
    currPlay1 = user1Move;
  }

  public String getCurrPlay1() {
    return currPlay1;
    public static String getUser1Move()
  }*/
  public void getUser1Move() {
    //Scanner scanner = new Scanner(System.in);
    printlnToPlayer("Player 1 choice: ", player1);
    try {
      getInputFromPlayer1().toUpperCase();
      //return getInputFromPlayer1();
      printlnToPlayer("you picked "+ getInputFromPlayer1(), player1);
    } catch(Exception e) {
      //e.printStackTrace();
    }
    //getUserInput();
    //String input = scanner.next().toUpperCase();
    //printlnToPlayer("you picked ", player1);
    /*while (checkInput(getInputFromPlayer1()) == false) {
      printlnToPlayer("Incorrect input player 1, try again: ", player1);
      getInputFromPlayer1().equalsUpperCase();
    }
    return input;*/
  }
  /** check user inputs.
  public static boolean checkInput(getPlayer1()) {
    if (getInputFromPlayer1().equals(ROCK) || getInputFromPlayer1().equals(PAPER) || getInputFromPlayer1().equals(SCISSORS)) {
      return true;
    }
    return false;
  }*/

  public void getUser2Move() {
    //Scanner scanner = new Scanner(System.in);
    printlnToPlayer("\nPlayer 2 choice: ", player2);
    try {
      getInputFromPlayer2().toUpperCase();
      printlnToPlayer("you picked "+ getInputFromPlayer2(), player2);
    } catch(Exception e) {
      //e.printStackTrace();
    }


    //getInputFromPlayer2();
    //String user2Move = scanner.next().toUpperCase();

    /*while (checkInput(user2Move) == false) {
      printlnToPlayer("Incorrect input player 2, try again: ", player2);
      user2Move = scanner.next().toUpperCase();
    }*/
    //return user2Move;
  }
  /*public void getUser2Move(String getPlayer2) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Player 2 choice: ");
    getInputFromPlayer2();
    String input = scanner.next().toUpperCase();
    /*while (checkInput(input) == false) {
      System.out.println("Incorrect input player 2, try again: ");
      input = scanner.next().toUpperCase();
    }
    return input;*/
  //}

  public void tick() throws IOException {
    //printNumIncorrectGuessesRemaining();
    //printGuessWordPrompt();
    printlnToAllPlayers("hiii");
    //printWhoseTurn();
    //setCurrPlay(getInputFromPlayerWithCurrTurn());
    //getUserInput();
    //getUser1Move(getInputFromPlayer1());
    //getUser2Move(getInputFromPlayer2());
    getUser1Move();
    getUser2Move();
    //getResult(getUser1Move(), getUser2Move());

    //printlnToAllPlayers("Guess: " + getCurrPlay());
    //getResult(getCurrPlay());
    //switchTurn();
  }
}
