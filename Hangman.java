import java.util.Random;
import java.util.Scanner;

public class Hangman {
  public static void main(String[] args) {
    playGame();
  }

  private String wordToGuess;
  private int winStreak;
  private int gamesPlayed;
  private int gamesWon;
  private int gamesLost;

  public Hangman() {
    wordToGuess = selectWordOrPhrase();
    winStreak = gamesPlayed = gamesWon = gamesLost = 0;
  }

  public static void playGame() {
    //TODO refactor
    Hangman hm = new Hangman();
    hm.printWelcomeMessage();
    String wordOrPhrase = hm.selectWordOrPhrase();
    System.out.println("Word or phrase: " + convertWordToUnderscores(wordOrPhrase));
    hm.userPickLetter(wordOrPhrase);
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

  public void userPickLetter(String wordOrPhrase) {
    System.out.println("Go on, guess a letter! " +
    "\nGuess multiple letters by separating them with commas (ex: a,s,d,f)." +
    "\nTo give up, type 'quit'.");
    System.out.println("Your Guess: ");
  }

  public void printWelcomeMessage() {
    System.out.println("Hello, and welcome to Hangman! In this game, the computer will generate " +
    "a word or phrase, and you must guess it by selecting letters! \nGuess the word or phrase " +
    "within a certain number of guesses, or you lose!");
  }

  //TODO hint command "would you like a hint?"
}
