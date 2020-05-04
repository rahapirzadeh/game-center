package games;

import helpers.FileIO;
import helpers.Game;
import helpers.Player;
import helpers.StringManipulation;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Hangman extends Game {

  public static final String welcomeMessage =
          "Hello, and welcome to Hangman! In this game, the computer will generate a word "
          + "or phrase, and you must guess it by selecting letters! "
          + "\nGuess the word or phrase within a certain number of guesses, or you lose!";
  public static final String instructions =
          "Guess multiple letters by separating them with commas (ex: 'a,s,d,f')."
          + "\nGuess the whole word by typing in your entire guess (ex: 'computer').";
  public static final String invalidGuess = "Sorry, that guess is invalid. Try again";
  public static final String lossMessage = "Sorry.. you lost!";
  public static final String winMessage = "Congratulations.. you won!";

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
    super();
    selectWordToGuess();
    currGuess = "";
    firstGame = true;
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  public Hangman(Player p1, Player p2) {
    super(p1, p2);
    selectWordToGuess();
    currGuess = "";
    firstGame = true;
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  public void run() {
    if (firstGame) {
      printWelcomeMessage();
      printInstructions();
    }
    printGuessWord(); //TODO delete after debugging complete
    while (!hasPlayerWon() && !hasPlayerLost()) {
      try {
        tick();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    roundFinish();
    if (promptUserPlayAgain().equalsIgnoreCase("y")) {
      newRound();
    } else {
      printGameStats();
    }
  }

  public void selectWordToGuess() {
    int wordIndex = new Random().nextInt(19); //19 is the # of words listed in ./hangmanWords.txt
    wordToGuess = FileIO.getLineFromFile("hangmanWords.txt", wordIndex);
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

  public char[] getCorrectlyGuessedLetters() {
    return correctlyGuessedLetters;
  }

  public String getGuessWordProgress() {
    //convertStringToUnderscores() converts unguessed letters in guessWord to underscores
    //charArrayToString() converts correct guesses to string form for display purposes
    //replaceAll("\\B", " ") adds spaces after each character
    return StringManipulation.convertStringToUnderscores(
            StringManipulation.charArrayToString(getCorrectlyGuessedLetters()))
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
        char[] charGuesses = StringManipulation.parseCommaSeparatedValue(guess);
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

  public void assertCorrectGuess(char c) {
    if (characterInGuessWord(c) && guessNotInPreviousGuesses(c)) {
      correctGuess(c);
    } else {
      incorrectGuess();
    }
  }

  public void assertCorrectGuess(String s) {
    if (getWordToGuess().equalsIgnoreCase(s)) {
      correctGuess(s);
    } else {
      incorrectGuess();
    }
  }

  public boolean characterInGuessWord(char c) {
    return getWordToGuess().indexOf(c) != -1;
  }

  public boolean guessNotInPreviousGuesses(char c) {
    for (char character : correctlyGuessedLetters) {
      if (character == c) {
        return false;
      }
    }
    return true;
  }

  public void insertCorrectLetterGuess(char c) {
    for (int pos = 0; pos < getWordToGuess().length(); pos++) {
      if (wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
  }

  public boolean hasPlayerWon() {
    return getWordToGuess().equalsIgnoreCase(currGuess)
            || getWordToGuess().equalsIgnoreCase(
                    StringManipulation.charArrayToString(getCorrectlyGuessedLetters()));
  }

  public boolean hasPlayerLost() {
    return numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES;
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
      printGuessWord();
    }
    incrementNumRoundsPlayed();
    printRoundStats();
  }

  public void newRound() {
    selectWordToGuess();
    resetGuesses();
    resetCorrectlyGuessedLetters();
    firstGame = false;
    run();
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

  public void incorrectGuess() {
    incrementNumTotalGuesses();
    incrementNumIncorrectGuesses();
  }

  public void printNumIncorrectGuessesRemaining() {
    printlnToAllPlayers("You have " + (NUM_ALLOWED_INCORRECT_GUESSES - numRoundIncorrectGuesses)
        + " incorrect guesses remaining.");
  }

  public void printRoundStats() {
    printlnToAllPlayers("===== ROUND STATS ====="
        + "\nPoints: " + getPoints()
        + "\nCurrent Win Streak: " + getWinStreak()
        + "\nTotal # of Guesses: " + getNumRoundTotalGuesses()
        + "\nTotal Correct Guesses: " + getNumRoundCorrectGuesses()
        + "\nTotal Incorrect Guesses: " + getNumRoundIncorrectGuesses());
  }

  public void printGameStats() {
    printlnToAllPlayers("===== GAME STATS ====="
        + "\nTotal # of Rounds Played: " + getNumRoundsPlayed()
        + "\nTotal # of Rounds Won: " + getNumRoundsWon()
        + "\nTotal # of Rounds Lost: " + getNumRoundsLost());
  }

  public void printWinMessage() {
    printlnToAllPlayers(winMessage);
  }

  public void printLossMessage() {
    printlnToAllPlayers(lossMessage);
  }

  public void printGuessWord() {
    printlnToAllPlayers("The word was: " + getWordToGuess());
  }

  public void printInvalidGuess() {
    System.out.println(invalidGuess);
  }

  public void printInstructions() {
    printlnToAllPlayers(instructions);

    // TODO "\nFor a hint, type 'hint'." +
    // TODO "\nTo print these instructions again, type 'help'." +
    // TODO "\nTo exit the game, type 'exit'.");
  }

  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  public void printGuessWordPrompt() {
    printlnToAllPlayers("Word: " + getGuessWordProgress());
  }

  public void tick() throws IOException {
    printNumIncorrectGuessesRemaining();
    printGuessWordPrompt();
    printWhoseTurn();
    setCurrGuess(getInputFromPlayerWithCurrTurn());
    printlnToAllPlayers("Guess: " + getCurrGuess());
    checkIfGuessCorrect(getCurrGuess());
    switchTurn();
  }

  //TODO hint command "would you like a hint?"
}
