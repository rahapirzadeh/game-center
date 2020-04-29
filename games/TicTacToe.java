package games;//Next sprint will include working win conditions and proper networking features

import java.util.Scanner;
public class TicTacToe
{


    public static void main(String[] args){
      TicTacToe();
    }

    public static void TicTacToe(){
      boolean gameOver = false;
      int turns = 0;
      String[][] board = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
      boolean[][] isPlaced = {{false, false, false}, {false, false, false}, {false, false, false}};
      int [] move = new int[2];

      printBoard(board);
      while(gameOver == false){
        move = makeMove(1);
        while(isPlaced[move[0]-1][move[1]-1] == true){
          System.out.println("Someone already took that square, enter a different move");
          move = makeMove(1);
        }
        board[move[0]-1][move[1]-1] = "X";
        isPlaced[move[0]-1][move[1]-1] = true;
        printBoard(board);
        gameOver = checkWin(board);

        if(gameOver == false){
          move = makeMove(2);
          while(isPlaced[move[0]-1][move[1]-1] == true){
            System.out.println("Someone already took that square, enter a different move");
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

    public static void printBoard(String[][] board){
      System.out.println("   1 " + " 2 " + " 3 ");
      for(int i = 0; i < 3; i++){
          System.out.print((i+1) + " ");
        for(int j = 0; j < 3; j++){
          System.out.print("[" + board[i][j] + "]");
        }
        System.out.print("\n\r");
      }
    }

    public static int[] makeMove(int player){
      Scanner in = new Scanner(System.in);
      System.out.println("helpers.Player " + player + " Enter the row:");
      String input = in.nextLine();
      while(checkInput(input) == false){
        System.out.println("Error: Invalid Input. Must be 1, 2 or 3");
        input = in.nextLine();
      }
      int row = Integer.parseInt(input);
      System.out.println("helpers.Player " + player + " Enter the col:");
      input = in.nextLine();
      while(checkInput(input) == false){
        System.out.println("Error: Invalid Input. Must be 1, 2 or 3");
        input = in.nextLine();
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

    public static boolean checkWin(String[][] board){
      //Checks the
      //[-][-][-]
      //[ ][ ][ ]
      //[ ][ ][ ]
      //Win condition
      if(board[0][0].equals("X") && board[0][1].equals("X") && board[0][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[0][1].equals("O") && board[0][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][ ]
      //[-][-][-]
      //[ ][ ][ ]
      //Win condition
      if(board[1][0].equals("X") && board[1][1].equals("X") && board[1][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[1][0].equals("O") && board[1][1].equals("O") && board[1][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][ ]
      //[ ][ ][ ]
      //[-][-][-]
      //Win condition
      if(board[2][0].equals("X") && board[2][1].equals("X") && board[2][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[2][0].equals("O") && board[2][1].equals("O") && board[2][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[-][ ][ ]
      //[-][ ][ ]
      //[-][ ][ ]
      //Win condition
      if(board[0][0].equals("X") && board[1][0].equals("X") && board[2][0].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[1][0].equals("O") && board[2][0].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][-][ ]
      //[ ][-][ ]
      //[ ][-][ ]
      //Win condition
      if(board[0][1].equals("X") && board[1][1].equals("X") && board[2][1].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[0][1].equals("O") && board[1][1].equals("O") && board[2][1].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][-]
      //[ ][ ][-]
      //[ ][ ][-]
      //Win condition
      if(board[0][2].equals("X") && board[1][2].equals("X") && board[2][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[0][2].equals("O") && board[1][2].equals("O") && board[2][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[-][ ][ ]
      //[ ][-][ ]
      //[ ][ ][-]
      //Win condition
      if(board[0][0].equals("X") && board[1][1].equals("X") && board[2][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[0][0].equals("O") && board[1][1].equals("O") && board[2][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      //Checks the
      //[ ][ ][-]
      //[ ][-][ ]
      //[-][ ][ ]
      //Win condition
      if(board[2][0].equals("X") && board[1][1].equals("X") && board[0][2].equals("X")){
        System.out.println("helpers.Game Over! helpers.Player 1 wins!");
        return true;
      }
      if(board[2][0].equals("O") && board[1][1].equals("O") && board[0][2].equals("O")){
        System.out.println("helpers.Game Over! helpers.Player 2 wins!");
        return true;
      }
      return false;

    }



}
