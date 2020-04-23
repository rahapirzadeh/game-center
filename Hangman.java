import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
  public static void main(String[] args) {
    Hangman hm = new Hangman();
    hm.playGame();
  }

  private static final int NUM_ALLOWED_INCORRECT_GUESSES = 8;

  private String wordToGuess;
  private String currGuess;
  private int numRoundTotalGuesses;
  private int numRoundCorrectGuesses;
  private int numRoundIncorrectGuesses;
  private int winStreak;
  private int numRoundsPlayed;
  private int numRoundsWon;
  private int numRoundsLost;
  private char[] correctlyGuessedLetters;
  private boolean firstGame;

  public Hangman() {
    selectWordToGuess();
    currGuess = "";
    firstGame = true;
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Sets all characters in correctlyGuessedLetters to underscores, indicating that no letters have been correctly guessed yet
  }

  public void playGame() {
    if(firstGame) {
      printWelcomeMessage();
      printInstructions();
      promptEnterKey();
    }
    printGuessWord(); //TODO delete after debugging complete
    while(!hasWon() && !hasLost()) {
      printNumIncorrectGuessesRemaining();
      printGuessWordPrompt();
      setCurrGuess(getUserInput());
      checkIfGuessCorrect(getCurrGuess());
    } roundFinish();
    if(promptUserPlayAgain().equals("y")) {
      newRound();
    } else {
      printGameStats();
    }
  }

  public String charArrayToString() {
    String s = "";
    for(char c : correctlyGuessedLetters) {
      s += c;
    }
    return s;
  }

  public void selectWordToGuess() {
    int indexOfWord = new Random().nextInt(19); //19 is the number of words or phrases listed in ./hangmanWordsAndPhrases.txt
    wordToGuess = FileIO.getLineFromFile("hangmanWords.txt", indexOfWord);
  }

  public static String convertStringToUnderscores(String s) {
    // Replaces all underscores to underscore and space '_ '
    // Extra space is for readability purposes and to distinguish different underscores
    return s.replaceAll("[_]", "_ ");
  }

  public void resetWinStreak() {
    winStreak = 0;
  }

  public void resetGuesses() {
    numRoundTotalGuesses = numRoundIncorrectGuesses = numRoundCorrectGuesses = 0;
  }

  public void resetCorrectlyGuessedLetters() {
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_');
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
  }

  public void incrementNumTotalGuesses() {
    numRoundTotalGuesses++;
  }

  public void incrementNumIncorrectGuesses() {
    numRoundIncorrectGuesses++;
  }

  public void incrementNumCorrectGuesses() {
    numRoundCorrectGuesses++;
  }

  public void setCurrGuess(String newGuess) {
    currGuess = newGuess;
  }

  public String getCurrGuess() {
    return currGuess;
  }

  public int getWinStreak() {
    return winStreak;
  }

  public String getWordToGuess() {
    return wordToGuess;
  }

  public String getGuessWordProgress() {
    return convertStringToUnderscores(charArrayToString()).replaceAll("\\B", " "); //.replaceAll() adds spaces after each character
  }

  public int getNumRoundTotalGuesses() {
    return numRoundTotalGuesses;
  }

  public int getNumRoundCorrectGuesses() {
    return numRoundCorrectGuesses;
  }

  public int getNumRoundIncorrectGuesses() {
    return numRoundIncorrectGuesses;
  }

  public int getNumRoundsPlayed() {
    return numRoundsPlayed;
  }

  public int getNumRoundsWon() {
    return numRoundsWon;
  }

  public int getNumRoundsLost() {
    return numRoundsLost;
  }

  public void printGuessWordPrompt() {
    System.out.print("Word or Phrase: " + getGuessWordProgress() +
    "\nYour Guess: ");
  }

  public String getUserInput() {
    String input = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      input = scanner.nextLine();
    }
    return input.strip().toLowerCase();
  }

  public void checkIfGuessCorrect(String guess) {
    //TODO refactor
    if(guess.length() > 1) {
      if(guess.contains(",")) {
        char[] characterGuesses = parseCommaSeparatedValue(guess);
        for (char character : characterGuesses) {
          if(isCharacterInWord(character)) {
            correctGuess(character);
          } else {
            incorrectGuess(character);
          }
        }
      }
    } else if(guess.length() == 1) {
      char charGuess = guess.charAt(0);
      if(isCharacterInWord(charGuess)) {
        correctGuess(charGuess);
      } else {
        incorrectGuess(charGuess);
      }
    } else {
      incorrectGuess(guess);
    }
  }

  public boolean isCharacterInWord(char c) {
    if(getWordToGuess().contains(Character.toString(c))) {
      return true;
    }
    return false;
  }

  public static char[] parseCommaSeparatedValue(String s) {
    s.replaceAll("\\s", "").replaceAll("[,]", "");
    char[] parsedArray = new char[s.length()];
    for (int pos = 0; pos < s.length(); pos++) {
      parsedArray[pos] = s.charAt(pos);
    }
    return parsedArray;
  }

  public void replaceUnderscoresWithCorrectlyGuessedLetter(char c) {
    for(int pos = 0; pos < getWordToGuess().length(); pos++) {
      if(wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
  }

  public boolean hasWon() {
    if (getWordToGuess().equals(currGuess) || getWordToGuess().equals(charArrayToString())) {
      return true;
    }
    return false;
  }

  public boolean hasLost() {
    if(numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES) {
      return true;
    }
    return false;
  }

  public void playerWin() {
    incrementWinStreak();
    incrementNumRoundsWon();
    printWinMessage();
  }

  public void playerLoss() {
    resetWinStreak();
    incrementNumRoundsLost();
    printLossMessage();
  }

  public void roundFinish() {
    if(hasWon()) {
      playerWin();
    } else {
      playerLoss();
    }
    incrementNumRoundsPlayed();
    printRoundStats();
  }

  public void newRound() {
    selectWordToGuess();
    resetGuesses();
    resetCorrectlyGuessedLetters();
    firstGame = false;
    playGame();
  }

  public void correctGuess(char guess) {
    replaceUnderscoresWithCorrectlyGuessedLetter(guess);
    incrementNumCorrectGuesses();
  }

  public void incorrectGuess(String guess) {
    incrementNumTotalGuesses();
    incrementNumIncorrectGuesses();
  }

  public void incorrectGuess(char guess) {
    incrementNumTotalGuesses();
    incrementNumIncorrectGuesses();
  }

  public void printNumIncorrectGuessesRemaining() {
    System.out.println("You have " + (NUM_ALLOWED_INCORRECT_GUESSES - numRoundIncorrectGuesses) + " incorrect guesses remaining.");
  }

  public void printRoundStats() {
    System.out.println("===== ROUND STATS =====" +
    "\nCurrent Win Streak: " + getWinStreak() +
    "\nTotal # of Guesses: " + getNumRoundTotalGuesses() +
    "\nTotal Correct Guesses: " + getNumRoundCorrectGuesses() +
    "\nTotal Incorrect Guesses: " + getNumRoundIncorrectGuesses());
  }

  public void printGameStats() {
    System.out.println("===== GAME STATS =====" +
    "\nTotal # of Rounds Played: " + getNumRoundsPlayed() +
    "\nTotal # of Rounds Won: " + getNumRoundsWon() +
    "\nTotal # of Rounds Lost: " + getNumRoundsLost());
  }

  public void printWinMessage() {
    System.out.println("Congratulations.. you won!");
    printGuessWord();
  }

  public void printLossMessage() {
    System.out.println("Sorry.. you lost!");
    printGuessWord();
  }

  public void printGuessWord() {
    System.out.println("The word was: " + getWordToGuess());
  }

  public static void printInstructions() {
    System.out.println("Guess multiple letters by separating them with commas (ex: 'a,s,d,f')." +
    "\nGuess the whole word by typing in your entire guess (ex: 'computer').");
    // TODO "\nFor a hint, type 'hint'." +
    // TODO "\nTo print these instructions again, type 'help'." +
    // TODO "\nTo exit the game, type 'exit'.");
  }

  public static void printWelcomeMessage() {
    System.out.println("Hello, and welcome to Hangman! In this game, the computer will generate " +
    "a word or phrase, and you must guess it by selecting letters! \nGuess the word or phrase " +
    "within a certain number of guesses, or you lose!");
  }

  public static void promptEnterKey() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  public String promptUserPlayAgain() {
    System.out.println("Would you like to play again? Enter 'y' to play again or 'n' to exit.");
    return getUserInput().toLowerCase();
  }

  //TODO hint command "would you like a hint?"
}
