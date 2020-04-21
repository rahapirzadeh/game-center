import java.util.Random;
import java.util.Scanner;

public class Hangman {
  public static void main(String[] args) {
    playGame();
  }

  private String wordOrPhraseToGuess;
  private int winStreak;
  private int numGamesPlayed;
  private int numGamesWon;
  private int numGamesLost;

  public Hangman() {
    wordOrPhraseToGuess = selectWordOrPhrase();
    winStreak = numGamesPlayed = numGamesWon = numGamesLost = 0;
  }

  public static void playGame() {
    //TODO refactor
    Hangman hm = new Hangman();
    hm.printWelcomeMessage();
    hm.printInstructions();
  }

  public String selectWordOrPhrase() {
    int indexOfWordOrPhrase = new Random().nextInt(34); //34 is the number of words or phrases listed in ./hangmanWordsAndPhrases.txt
    return FileIO.getLineFromFile("hangmanWordsAndPhrases.txt", indexOfWordOrPhrase);
  }

  public static String convertWordToUnderscores(String word) {
    // Adds two extra spaces to whitespace characters and replaces all non-whitespace characters with underscore and space '_ '
    // Extra spaces are for readability purposes and to distinguish different underscores
    return word.replaceAll("\\s", "   ").replaceAll("\\S", "_ ");
  }

  public void incrementWinStreak() {
    winStreak++;
  }

  public void resetWinStreak() {
    winStreak = 0;
  }

  public void incrementNumGamesPlayed() {
    numGamesPlayed++;
  }

  public void incrementNumGamesWon() {
    numGamesWon++;
  }

  public void incrementNumGamesLost() {
    numGamesLost++;
  }

  public setNewWordOrPhraseToGuess() {
    wordOrPhraseToGuess = selectWordOrPhrase();
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
