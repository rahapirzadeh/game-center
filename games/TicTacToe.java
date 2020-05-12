package games;//Next sprint will include working win conditions and proper networking features

import java.util.Scanner;
import helpers.game;
public class TicTacToe extends Game{


    public static void main(String[] args){
      run();
    }

    public static void run(){
      boolean gameOver = false;
      int turns = 0;
      String[][] board = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
      boolean[][] isPlaced = {{false, false, false}, {false, false, false}, {false, false, false}};
      int [] move = new int[2];

      printBoard(board);
      while(gameOver == false){
        move = makeMove(1);
        while(isPlaced[move[0]-1][move[1]-1] == true){
          printlnToAllPlayers("Someone already took that square, enter a different move");
          move = makeMove(1);
        }
        board[move[0]-1][move[1]-1] = "X";
        isPlaced[move[0]-1][move[1]-1] = true;
        printBoard(board);
        gameOver = checkWin(board);

        if(gameOver == false){
          move = makeMove(2);
          while(isPlaced[move[0]-1][move[1]-1] == true){
            printlnToAllPlayers("Someone already took that square, enter a different move");
            move = makeMove(2);
          }
          board[move[0]-1][move[1]-1] = "O";
          isPlaced[move[0]-1][move[1]-1] = true;
          printBoard(board);
          turns++;
          gameOver = checkWin(board);
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
      printlnToAllPlayers("Player " + player + " Enter the row:");
      if(player == 1){
        input = getInputFromPlayer1();
      }else if(player == 2){
        input = getInputFromPlayer2();
      }
      while(checkInput(input) == false){
        printlnToAllPlayers("Error: Invalid Input. Must be 1, 2 or 3", );
        if(player == 1){
          input = getInputFromPlayer1();
        }else if(player == 2){
          input = getInputFromPlayer2();
        }
      }
      int row = Integer.parseInt(input);
      printlnToAllPlayers("Player " + player + " Enter the col:");
      if(player == 1){
        input = getInputFromPlayer1();
      }else if(player == 2){
        input = getInputFromPlayer2();
      }
      while(checkInput(input) == false){
        printlnToAllPlayers("Error: Invalid Input. Must be 1, 2 or 3");
        if(player == 1){
          input = getInputFromPlayer1();
        }else if(player == 2){
          input = getInputFromPlayer2();
        }
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

    public boolean hasPlayerWon(String[][] board){
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
      return false;

    }
  public void tick() throws IOException {
    TicTacToe();
  }



}
