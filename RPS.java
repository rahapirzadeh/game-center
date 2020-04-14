import java.util.Scanner;

public class RPS {

	public static final String ROCK = "R";
	public static final String PAPER = "P";
	public static final String SCISSORS = "S";
	public static final String NO ="N";

	//Get the game Result
	public static void getResult(String user1Move, String user2Move) {

        if (user1Move.equals(user2Move))
	        System.out.println("It's a tie!\n");

        else if (user1Move.equals(ROCK))
        {
	        if (user2Move.equals(SCISSORS))
		        System.out.println("Player 1 Wins!!\n"+"Player 2 Looses...\n"+"Rock crushes scissors.\n");
	        else if (user2Move.equals(PAPER))
		        System.out.println("Player 2 Wins!!\n"+"Player 1 Looses...\n"+"Paper eats rock.\n");
					else
						System.out.println("Invalid input player 2.\n");
        }
        else if (user1Move.equals(PAPER))
        {
            if (user2Move.equals(ROCK))
		        System.out.println("Player 1 Wins!!\n"+"Player 2 Looses...\n"+"Paper eats rock.\n");
	        else if (user2Move.equals(SCISSORS))
		        System.out.println("Player 2 Wins!!\n"+"Player 1 Looses...\n"+"Scissor cuts paper.\n");
					else
						System.out.println("Invalid input player 2.\n");
        }
        else if (user1Move.equals(SCISSORS))
        {
	        if (user2Move.equals(PAPER))
		        System.out.println("Player 1 Wins!!\n"+"Player 2 Looses...\n"+"Scissor cuts paper.\n");
	        else if (user2Move.equals(ROCK))
		        System.out.println("Player 2 Wins!!\n"+"Player 1 Looses...\n"+"Rock crushes scissors.\n");
					else
						System.out.println("Invalid input player 2.\n");
        }
        else
    	    System.out.println("Invalid input player 1.\n");
    }

	// User's move
	public static String getUser1Move(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nPlayer 1 choice: ");
	String input = scanner.next().toUpperCase();
			return input;

	}

	public static String getUser2Move(){
	    Scanner scanner = new Scanner(System.in);
	    System.out.println("Player 2 choice: ");
		String input = scanner.next().toUpperCase();
        return input;
    }

    // Main method
	public static void main(String[] args) {
		System.out.println( "Rock, Paper, Scissors!\n"
				+ "Please enter a move.\n"
				+"Rock = R, Paper= P, and Scissors = S\n");
			do {
				getResult(getUser1Move(), getUser2Move());
				System.out.println("---Want to play again? Y/N---");
				Scanner scanner = new Scanner(System.in);
				String play = scanner.next().toUpperCase();
				if(play.equals(NO)){
					break;
				}

			} while (true);
	}
}
