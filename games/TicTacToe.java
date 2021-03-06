package games;

import helpers.Game;
import helpers.Player;

/**
* This is a multiplayer Tic-Tac-Toe program.
*/
public class TicTacToe extends Game {
  private boolean gameOver = false;
  private int turns = 0;
  private String[][] board = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
  private boolean[][] isPlaced = {{false, false, false},
      {false, false, false},
      {false, false, false}};
  private int [] move = new int[2];

  /**
   * Default constructor.
   */
  public TicTacToe() {
    super();
  }

  /**
   * Constructs a new dual-player game instance of Tic-Tac-Toe using {@code p1} and {@code p2}.
   * @param p1
   *        Player 1
   * @param p2
   *        Player 2
   */
  public TicTacToe(Player p1, Player p2) {
    super(p1, p2);
  }

  /**
   * Starts instance of Tic Tac Toe.
   */
  public void run() {
    printBoard(board);
    while (gameOver == false) {
      move = makeMove(1);
      while (isPlaced[move[0] - 1][move[1] - 1] == true) {
        printlnToPlayerWithCurrTurn("Someone already took that square, enter a different move");
        move = makeMove(1);
      }
      board[move[0] - 1][move[1] - 1] = "X";
      isPlaced[move[0] - 1][move[1] - 1] = true;
      printBoard(board);
      gameOver = hasPlayerWon(board, isPlaced);
      switchTurn();

      if (gameOver == false) {
        move = makeMove(2);
        while (isPlaced[move[0] - 1][move[1] - 1] == true) {
          printlnToPlayerWithCurrTurn("Someone already took that square, enter a different move");
          move = makeMove(2);
        }
        board[move[0] - 1][move[1] - 1] = "O";
        isPlaced[move[0] - 1][move[1] - 1] = true;
        printBoard(board);
        turns++;
        gameOver = hasPlayerWon(board, isPlaced);
        switchTurn();
      }
    }
    String choice1 = "";
    printlnToPlayerWithCurrTurn("Do you want to play again? Enter 1(YES) or 2(NO)");
    while (vote(choice1) == false) {
      printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1 or 2");
      choice1 = getInputFromPlayerWithCurrTurn();
    }
    switchTurn();
    String choice2 = "";
    printlnToPlayerWithCurrTurn("Do you want to play again? Enter 1(YES) or 2(NO)");
    while (vote(choice1) == false) {
      printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1 or 2");
      choice2 = getInputFromPlayerWithCurrTurn();
    }
    if (choice1.equals(choice2)) {
      if (choice1.equals("1")) {
        printlnToAllPlayers("Both players voted YES! Restarting Game...");
        tick();
      } else {
        printlnToAllPlayers("Both players voted No! Exiting...");
        System.exit(0);
      }
    } else {
      printlnToAllPlayers("One player voted Yes and one voted No... Exiting...");
      System.exit(0);
    }

  }

  /**
   * Prints out the board for the player.
   * @param board
   *        Game Board
   */
  public void printBoard(String[][] board) {
    printlnToAllPlayers("   1 " + " 2 " + " 3 ");
    String newLine = " ";
    for (int i = 0; i < 3; i++) {
      newLine = " ";
      for (int j = 0; j < 3; j++) {
        newLine = newLine + "[" + board[i][j] + "]";
      }
      printlnToAllPlayers(newLine);
    }
  }

  /**
   * Takes the player's input to make a move.
   * @param player
   *        Gets the number of the player making the move
   * @return  move for the player's current turn
   */
  public int[] makeMove(int player) {
    String input = "";
    printWhoseTurn();
    printlnToPlayerWithCurrTurn("Enter the row: ");
    input = getInputFromPlayerWithCurrTurn();
    while (checkInput(input) == false) {
      printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1, 2 or 3");
      input = getInputFromPlayerWithCurrTurn();
    }
    int[] move = new int[2];
    move[0] = Integer.parseInt(input);
    printlnToPlayerWithCurrTurn("Enter the col:");
    input = getInputFromPlayerWithCurrTurn();
    while (checkInput(input) == false) {
      printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1, 2 or 3");
      input = getInputFromPlayerWithCurrTurn();
    }
    int col = Integer.parseInt(input);
    move[1] = col;
    return move;
  }

  /**
   * Validates the players input for a move.
   * @param input
   *        the move provided by the player
   * @return  whether input was either 1, 2, 3, or not
   */
  public static boolean checkInput(String input) {
    if (input.equals("1") || input.equals("2") || input.equals("3")) {
      return true;
    }
    return false;
  }

  /**
   * Validates the players input for voting to play another game.
   * @param input
   *        the vote provided by the player
   * @return whether the players voted to play again or not
   */
  public static boolean vote(String input) {
    if (input.equals("1") || input.equals("2")) {
      return true;
    }
    return false;
  }

  /**
   * Compares the board to valid Tic-Tac-Toe win conditions.
   * @param board
   *        The game board
   * @param isPlaced
   *        Booleans representing if a move has been made at a given location on the board
   * @return  whether the player has won or not
   */
  public boolean hasPlayerWon(String[][] board, boolean[][] isPlaced) {

    //Checks the
    //[-][-][-]
    //[ ][ ][ ]
    //[ ][ ][ ]
    //Win condition
    if (board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[ ][ ][ ]
    //[-][-][-]
    //[ ][ ][ ]
    //Win condition
    if (board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[ ][ ][ ]
    //[ ][ ][ ]
    //[-][-][-]
    //Win condition
    if (board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[-][ ][ ]
    //[-][ ][ ]
    //[-][ ][ ]
    //Win condition
    if (board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[ ][-][ ]
    //[ ][-][ ]
    //[ ][-][ ]
    //Win condition
    if (board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[ ][ ][-]
    //[ ][ ][-]
    //[ ][ ][-]
    //Win condition
    if (board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[-][ ][ ]
    //[ ][-][ ]
    //[ ][ ][-]
    //Win condition
    if (board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }
    //Checks the
    //[ ][ ][-]
    //[ ][-][ ]
    //[-][ ][ ]
    //Win condition
    if (board[2][0].equals("X") && board[1][1].equals("X") && board[0][2].equals("X")) {
      printlnToAllPlayers("Game Over! Player 1 wins!");
      return true;
    }
    if (board[2][0].equals("O") && board[1][1].equals("O") && board[0][2].equals("O")) {
      printlnToAllPlayers("Game Over! Player 2 wins!");
      return true;
    }

    if ((isPlaced[0][0] == true) && (isPlaced[0][1] == true) && (isPlaced[0][2] == true)
        && (isPlaced[1][0] == true) && (isPlaced[1][1] == true) && (isPlaced[1][2] == true)
        && (isPlaced[2][0] == true) && (isPlaced[2][1] == true) && (isPlaced[2][2] == true)) {
      printlnToAllPlayers("All spaces have been filled with no winner! Game Over!");
      return true;
    }
    return false;
  }

  /**
   * Resets all game variables back to default state.
   */
  public void restartGame() {
    gameOver = false;
    turns = 0;
    board = new String[][]{{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
    isPlaced = new boolean[][]{{false, false, false}, {false, false, false}, {false, false, false}};
    move = new int[2];
  }

  /**
   * Method for playing a full game.
   */
  public void tick() {
    restartGame();
    run();
  }



}
