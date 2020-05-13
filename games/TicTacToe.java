package games;

import java.util.Scanner;
import helpers.*;

public class TicTacToe extends Game{
    private boolean gameOver = false;
    private int turns = 0;
    private String[][] board = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
    private boolean[][] isPlaced = {{false, false, false}, {false, false, false}, {false, false, false}};
    private int [] move = new int[2];

    public TicTacToe(){
      super();
    }
    public TicTacToe(Player p1, Player p2){
      super(p1, p2);
    }

    public void run(){
      printBoard(board);
      while(gameOver == false){
        move = makeMove(1);
        while(isPlaced[move[0]-1][move[1]-1] == true){
          printlnToPlayerWithCurrTurn("Someone already took that square, enter a different move");
          move = makeMove(1);
        }
        board[move[0]-1][move[1]-1] = "X";
        isPlaced[move[0]-1][move[1]-1] = true;
        printBoard(board);
        gameOver = hasPlayerWon(board, isPlaced);
        switchTurn();

        if(gameOver == false){
          move = makeMove(2);
          while(isPlaced[move[0]-1][move[1]-1] == true){
            printlnToPlayerWithCurrTurn("Someone already took that square, enter a different move");
            move = makeMove(2);
          }
          board[move[0]-1][move[1]-1] = "O";
          isPlaced[move[0]-1][move[1]-1] = true;
          printBoard(board);
          turns++;
          gameOver = hasPlayerWon(board, isPlaced);
          switchTurn();
        }

      }

    }

    public void printBoard(String[][] board){
      printlnToAllPlayers("   1 " + " 2 " + " 3 ");
      String newLine = " ";
      for(int i = 0; i < 3; i++){
          newLine = " ";
        for(int j = 0; j < 3; j++){
          newLine = newLine + "[" + board[i][j] + "]";
        }
        printlnToAllPlayers(newLine);
      }
    }

    public int[] makeMove(int player){
      String input = "";
      printWhoseTurn();
      printlnToPlayerWithCurrTurn("Enter the row: ");
      input = getInputFromPlayerWithCurrTurn();
      while(checkInput(input) == false){
        printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1, 2 or 3");
        input = getInputFromPlayerWithCurrTurn();
      }
      int row = Integer.parseInt(input);
      printlnToPlayerWithCurrTurn("Enter the col:");
      input = getInputFromPlayerWithCurrTurn();
      while(checkInput(input) == false){
        printlnToPlayerWithCurrTurn("Error: Invalid Input. Must be 1, 2 or 3");
        input = getInputFromPlayerWithCurrTurn();
      }
      int col = Integer.parseInt(input);
      int[] move = {row, col};
      return move;
    }

    public static boolean checkInput(String input){
      if(input.equals("1") || input.equals("2") || input.equals("3")){
        return true;
      }
      return false;
    }

    public boolean hasPlayerWon(String[][] board, boolean[][] isPlaced){

      //Checks the
      //[-][-][-]
      //[ ][ ][ ]
      //[ ][ ][ ]
      //Win condition
      if(board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][ ]
      //[-][-][-]
      //[ ][ ][ ]
      //Win condition
      if(board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][ ]
      //[ ][ ][ ]
      //[-][-][-]
      //Win condition
      if(board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[-][ ][ ]
      //[-][ ][ ]
      //[-][ ][ ]
      //Win condition
      if(board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][-][ ]
      //[ ][-][ ]
      //[ ][-][ ]
      //Win condition
      if(board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][-]
      //[ ][ ][-]
      //[ ][ ][-]
      //Win condition
      if(board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[-][ ][ ]
      //[ ][-][ ]
      //[ ][ ][-]
      //Win condition
      if(board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][-]
      //[ ][-][ ]
      //[-][ ][ ]
      //Win condition
      if(board[2][0].equals("X") && board[1][1].equals("X") && board[0][2].equals("X")){
        printlnToAllPlayers("Game Over! Player 1 wins!");
        return true;
      }
      if(board[2][0].equals("O") && board[1][1].equals("O") && board[0][2].equals("O")){
        printlnToAllPlayers("Game Over! Player 2 wins!");
        return true;
      }

      if((isPlaced[0][0] == true) && (isPlaced[0][1] == true) && (isPlaced[0][2] == true)
      && (isPlaced[1][0] == true) && (isPlaced[1][1] == true) && (isPlaced[1][2] == true)
      && (isPlaced[2][0] == true) && (isPlaced[2][1] == true) && (isPlaced[2][2] == true)){
        printlnToAllPlayers("All spaces have been filled with no winner! Game Over!");
        return true;
      }
      return false;
    }


  public void tick(){
    run();
  }



}
