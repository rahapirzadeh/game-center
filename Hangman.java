import java.util.Random;
import java.util.Scanner;

public class Hangman {
  public static void main(String[] args) {
    playGame();
  }

  private String wordToGuess;
  private int winStreak;
  private int numGamesPlayed;
  private int numGamesWon;
  private int numGamesLost;

  public Hangman() {
    wordToGuess = selectWordOrPhrase();
    winStreak = numGamesPlayed = numGamesWon = numGamesLost = 0;
  }

  public static void playGame() {
    //TODO refactor
    Hangman hm = new Hangman();
    hm.printWelcomeMessage();
    hm.printInstructions();
  }

  public String selectWordOrPhrase() {
    Random rand = new Random();
    int indexOfWordOrPhrase = rand.nextInt(34); //34 is the number of words or phrases listed in ./hangmanWordsAndPhrases.txt
    FileIO io = new FileIO();
    String wordOrPhrase = io.getLineFromFile("hangmanWordsAndPhrases.txt", indexOfWordOrPhrase);
    return wordOrPhrase;
  }

  public static String convertWordToUnderscores(String word) {
    //TODO keep dashes as-is
    String convertedWord = word.replaceAll("\\s", "   ").replaceAll("\\S", "_ "); //adds two extra spaces to whitespace characters and replaces all non-whitespace characters with underscore and space '_ '
    return convertedWord;
  }

  public void printInstructions() {
    System.out.println("Guess multiple letters by separating them with commas (ex: 'a,s,d,f')." +
    "\nGuess the whole word by typing in your entire guess (ex: 'computer')." +
    "\nFor a hint, type 'hint'." +
    "\nTo give up, type 'quit'." +
    "\nGo on, guess a letter!");
  }

  public void printWelcomeMessage() {
    System.out.println("Hello, and welcome to Hangman! In this game, the computer will generate " +
    "a word or phrase, and you must guess it by selecting letters! \nGuess the word or phrase " +
    "within a certain number of guesses, or you lose!");
  }

  //TODO hint command "would you like a hint?"
}
