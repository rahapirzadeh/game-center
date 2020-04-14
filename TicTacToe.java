///Right now, this just displays the board, and allows player input
//Next sprint will include working win conditions and proper networking features

import java.util.Scanner;
class TicTacToe
{


    public static void main(String[] args){
      TicTacToe();
    }

    public static void TicTacToe(){
      boolean[] gameOver = {false, false};
      int turns = 0;
      String[][] board = {{"-", "-", "-"}, {"-", "-", "-"}, {"-", "-", "-"}};
      int [] move = new int[2];
      printBoard(board);
      while(turns < 3){
        move = makeMove(1);
        board[move[0]-1][move[1]-1] = "X";
        printBoard(board);
        //gameOver = checkWin(board);
        move = makeMove(2);
        board[move[0]-1][move[1]-1] = "O";
        printBoard(board);
        //gameOver = checkWin(board);
        turns++;
      }
      //if(gameOver[1] == true){
      //  System.out.println("Player 1 wins!");
    //  }
      //if(gameOver[1] == false){
      //  System.out.println("Player 2 wins!");
      //}
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
      System.out.println("Player " + player + " Enter the row:");
      int row = in.nextInt();
      System.out.println(row);
      System.out.println("Player " + player + " Enter the col:");
      int col = in.nextInt();
      System.out.println(col);
      int[] move = {row, col};
      return move;
    }

    public static boolean[] checkWin(String[][] board){
      boolean[] results = {false, false};
      return results;
    }


}
