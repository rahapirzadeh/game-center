package games;

import helpers.FileIO;
import helpers.Game;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends Game {
  public static void main(String[] args) {
    Hangman hm = new Hangman();
    hm.play();
  }

  private final PrintWriter out = new PrintWriter(System.out, true);

  private static final int NUM_ALLOWED_INCORRECT_GUESSES = 8;

  private String wordToGuess;
  private String currGuess;
  private int points;
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
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  public void play() {
    if (firstGame) {
      printWelcomeMessage();
      printInstructions();
      promptEnterKey();
    }
    printGuessWord(); //TODO delete after debugging complete
    while (!hasPlayerWon() && !hasPlayerLost()) {
      tick();
      checkIfGuessCorrect(getCurrGuess());
      switchTurn();
    }
    roundFinish();
    if (promptUserPlayAgain().equalsIgnoreCase("y")) {
      newRound();
    } else {
      printGameStats();
      returnToLobby();
    }
  }

  public String charArrayToString(char[] arr) {
    String s = "";
    for (char c : arr) {
      s += c;
    }
    return s;
  }

  public void selectWordToGuess() {
    int wordIndex = new Random().nextInt(19); //19 is the # of words listed in ./hangmanWords.txt
    wordToGuess = FileIO.getLineFromFile("hangmanWords.txt", wordIndex);
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

  public void incrementPoints() {
    points++;
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
    //convertStringToUnderscores() converts unguessed letters in guessWord to underscores
    //charArrayToString() converts correct guesses to string form for display purposes
    //replaceAll("\\B", " ") adds spaces after each character
    return convertStringToUnderscores(charArrayToString(correctlyGuessedLetters))
        .replaceAll("\\B", " ");
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

  public int getPoints() {
    return points;
  }

  public void checkIfGuessCorrect(String guess) {
    if (guess.length() > 1) {
      if (guess.contains(",")) {
        char[] charGuesses = parseCommaSeparatedValue(guess);
        for (char character : charGuesses) {
          assertCorrectGuess(character);
        }
      } else {
        assertCorrectGuess(getCurrGuess());
      }
    } else if (guess.length() == 1) {
      char charGuess = guess.charAt(0);
      assertCorrectGuess(charGuess);
    } else {
      printInvalidGuess();
    }
  }

  public boolean assertCorrectGuess(char c) {
    if (characterInGuessWord(c) && guessNotInPreviousGuesses(c)) {
      correctGuess(c);
      return true;
    }
    incorrectGuess(c);
    return false;
  }

  public boolean assertCorrectGuess(String s) {
    if (getWordToGuess().equalsIgnoreCase(s)) {
      correctGuess(s);
      return true;
    }
    incorrectGuess(s);
    return false;
  }

  public boolean characterInGuessWord(char c) {
    if (getWordToGuess().indexOf(c) != -1) {
      return true;
    }
    return false;
  }

  public boolean guessNotInPreviousGuesses(char c) {
    for (char character : correctlyGuessedLetters) {
      if (character == c) {
        return false;
      }
    }
    return true;
  }

  public static char[] parseCommaSeparatedValue(String s) {
    s = s.replaceAll(",", "").replaceAll("\\s+", ""); //reference: https://programming.guide/java/remove-trailing-comma-from-comma-separated-string.html
    char[] parsedArray = new char[s.length()];
    for (int pos = 0; pos < s.length(); pos++) {
      parsedArray[pos] = s.charAt(pos); //adds characters in s to indexes in parsedArray
    }
    return parsedArray;
  }

  public void insertCorrectLetterGuess(char c) {
    for (int pos = 0; pos < getWordToGuess().length(); pos++) {
      if (wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
  }

  public boolean hasPlayerWon() {
    if (getWordToGuess().equalsIgnoreCase(currGuess)
        || getWordToGuess().equalsIgnoreCase(charArrayToString(correctlyGuessedLetters))) {
      return true;
    }
    return false;
  }

  public boolean hasPlayerLost() {
    if (numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES) {
      return true;
    }
    return false;
  }

  public void playerWin() {
    incrementWinStreak();
    incrementNumRoundsWon();
    printWinMessage();
    points += 3; //bonus for correctly guessing word
  }

  public void playerLoss() {
    resetWinStreak();
    incrementNumRoundsLost();
    printLossMessage();
  }

  public void roundFinish() {
    if (hasPlayerWon()) {
      playerWin();
    } else {
      playerLoss();
    }
    printGuessWord();
    incrementNumRoundsPlayed();
    printRoundStats();
  }

  public void newRound() {
    selectWordToGuess();
    resetGuesses();
    resetCorrectlyGuessedLetters();
    firstGame = false;
    play();
  }

  public void correctGuess(char guess) {
    incrementNumTotalGuesses();
    insertCorrectLetterGuess(guess);
    incrementNumCorrectGuesses();
    incrementPoints();
  }

  public void correctGuess(String guess) {
    incrementNumTotalGuesses();
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
    System.out.println("You have " + (NUM_ALLOWED_INCORRECT_GUESSES - numRoundIncorrectGuesses)
        + " incorrect guesses remaining.");
  }

  public void printRoundStats() {
    outToSystemAndPW("===== ROUND STATS ====="
        + "\nPoints: " + getPoints()
        + "\nCurrent Win Streak: " + getWinStreak()
        + "\nTotal # of Guesses: " + getNumRoundTotalGuesses()
        + "\nTotal Correct Guesses: " + getNumRoundCorrectGuesses()
        + "\nTotal Incorrect Guesses: " + getNumRoundIncorrectGuesses());
  }

  public void printGameStats() {
    outToSystemAndPW("===== GAME STATS ====="
        + "\nTotal # of Rounds Played: " + getNumRoundsPlayed()
        + "\nTotal # of Rounds Won: " + getNumRoundsWon()
        + "\nTotal # of Rounds Lost: " + getNumRoundsLost());
  }

  public void printWinMessage() {
    outToSystemAndPW("Congratulations.. you won!");
  }

  public void printLossMessage() {
    outToSystemAndPW("Sorry.. you lost!");
  }

  public void printGuessWord() {
    outToSystemAndPW("The word was: " + getWordToGuess());
  }

  public void printInvalidGuess() {
    outToSystemAndPW("Sorry, that guess is invalid. Try again");
  }

  public void printInstructions() {
    outToSystemAndPW("Guess multiple letters by separating them with commas (ex: 'a,s,d,f')."
        + "\nGuess the whole word by typing in your entire guess (ex: 'computer').");

    // TODO "\nFor a hint, type 'hint'." +
    // TODO "\nTo print these instructions again, type 'help'." +
    // TODO "\nTo exit the game, type 'exit'.");
  }

  public void printWelcomeMessage() {
    outToSystemAndPW("Hello, and welcome to games.Hangman! In this game, the computer will generate "
        + "a word or phrase, and you must guess it by selecting letters! "
        + "\nGuess the word or phrase within a certain number of guesses, or you lose!");
  }

  public void printGuessWordPrompt() {
    outToSystemAndPW("Word: " + getGuessWordProgress());
  }

  public void outToSystemAndPW(String data) {
    //System.out.println(data);
    out.println(data);
  }

  public String tick() {
    String output = null;
    printNumIncorrectGuessesRemaining();
    printGuessWordPrompt();
    printWhoseTurn();
    setCurrGuess(getUserInput());
    return "TEST OUTPUT";
  }

  public static void promptEnterKey() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  //TODO hint command "would you like a hint?"
}
