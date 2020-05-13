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
  private String currGuess = "";
  private int points;
  private int numRoundTotalGuesses;
  private int numRoundCorrectGuesses;
  private int numRoundIncorrectGuesses;
  private int winStreak;
  private int numRoundsPlayed;
  private int numRoundsWon;
  private int numRoundsLost;
  private char[] correctlyGuessedLetters;
  private boolean firstGame = true;

  /** method to pass in one player.*/
  public Hangman() {
    super();
    selectWordToGuess();
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  /** method to pass in both players.*/
  public Hangman(Player p1, Player p2) {
    super(p1, p2);
    selectWordToGuess();
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  /** runs hangman.*/
  public void run() {
    if (firstGame) {
      printWelcomeMessage();
      printInstructions();
    }
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
      System.exit(0);
    }
  }

  /** selects random word from textfile.*/
  public void selectWordToGuess() {
    int wordIndex = new Random().nextInt(19); //19 is the # of words listed in ./hangmanWords.txt
    wordToGuess = FileIO.getLineFromFile("hangmanWords.txt", wordIndex);
  }

  /** resets winstreak when player looses.*/
  public void resetWinStreak() {
    winStreak = 0;
  }

  /** resets guesses when a round is finished.*/
  public void resetGuesses() {
    numRoundTotalGuesses = numRoundIncorrectGuesses = numRoundCorrectGuesses = 0;
  }

  /** resets the board to "-" when a round is finished.*/
  public void resetCorrectlyGuessedLetters() {
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_');
  }

  /** increments the win streak.*/
  public void incrementWinStreak() {
    winStreak++;
  }

  /** increments the number of rounds played.*/
  public void incrementNumRoundsPlayed() {
    numRoundsPlayed++;
  }

  /** increments the number of rounds won.*/
  public void incrementNumRoundsWon() {
    numRoundsWon++;
  }

  /** increments the number of rounds lost.*/
  public void incrementNumRoundsLost() {
    numRoundsLost++;
  }

  /** increments the number of guesses in the round.*/
  public void incrementNumTotalGuesses() {
    numRoundTotalGuesses++;
  }

  /** increments the number of incorrect guesses in the round.*/
  public void incrementNumIncorrectGuesses() {
    numRoundIncorrectGuesses++;
  }

  /** increments the number of correct guesses in the round.*/
  public void incrementNumCorrectGuesses() {
    numRoundCorrectGuesses++;
  }

  /** increments the number of points.*/
  public void incrementPoints() {
    points++;
  }

  /** method to get the guess from current player.*/
  public void setCurrGuess(String newGuess) {
    currGuess = newGuess;
  }

  /** method to return the guess from current player.*/
  public String getCurrGuess() {
    return currGuess;
  }

  /** method to return win streak.*/
  public int getWinStreak() {
    return winStreak;
  }

  /** method to return word to guess.*/
  public String getWordToGuess() {
    return wordToGuess;
  }

  /** method to return number of correctly guessed letters.*/
  public char[] getCorrectlyGuessedLetters() {
    return correctlyGuessedLetters;
  }

  /** convertStringToUnderscores() converts unguessed letters in guessWord to underscores
      charArrayToString() converts correct guesses to string form for display purposes
      replaceAll("\\B", " ") adds spaces after each character.*/
  public String getGuessWordProgress() {
    return StringManipulation.convertStringToUnderscores(
            StringManipulation.charArrayToString(getCorrectlyGuessedLetters()))
            .replaceAll("\\B", " ");
  }

  /** method to return number of guesses at end of round.*/
  public int getNumRoundTotalGuesses() {
    return numRoundTotalGuesses;
  }

  /** method to return number of correct guesses at end of round.*/
  public int getNumRoundCorrectGuesses() {
    return numRoundCorrectGuesses;
  }

  /** method to return number of incorrect guesses at end of round.*/
  public int getNumRoundIncorrectGuesses() {
    return numRoundIncorrectGuesses;
  }

  /** method to return number of rounds played.*/
  public int getNumRoundsPlayed() {
    return numRoundsPlayed;
  }

  /** method to return numberof rounds won at end of game.*/
  public int getNumRoundsWon() {
    return numRoundsWon;
  }

  /** method to return number of rounds lost at end of game.*/
  public int getNumRoundsLost() {
    return numRoundsLost;
  }

  /** method to return points at end of round.*/
  public int getPoints() {
    return points;
  }

  /** method to check if guess is correct.*/
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

  /**  method to place correct guess on board.*/
  public void assertCorrectGuess(char c) {
    if (characterInGuessWord(c) && guessNotInPreviousGuesses(c)) {
      correctGuess(c);
    } else {
      incorrectGuess();
    }
  }

  /**  method to place correct guess on board when given as string.*/
  public void assertCorrectGuess(String s) {
    if (getWordToGuess().equalsIgnoreCase(s)) {
      correctGuess(s);
    } else {
      incorrectGuess();
    }
  }

  /** method to get how many letters in word to place on board.*/
  public boolean characterInGuessWord(char c) {
    return getWordToGuess().indexOf(c) != -1;
  }

  /** method to check if letter was guessed before.*/
  public boolean guessNotInPreviousGuesses(char c) {
    for (char character : correctlyGuessedLetters) {
      if (character == c) {
        return false;
      }
    }
    return true;
  }

  /** method to insert correct letter in board.*/
  public void insertCorrectLetterGuess(char c) {
    for (int pos = 0; pos < getWordToGuess().length(); pos++) {
      if (wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
  }

  /** method to check if player has won.*/
  public boolean hasPlayerWon() {
    return getWordToGuess().equalsIgnoreCase(currGuess)
            || getWordToGuess().equalsIgnoreCase(
                    StringManipulation.charArrayToString(getCorrectlyGuessedLetters()));
  }

  /** method to check if player has lost.*/
  public boolean hasPlayerLost() {
    return numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES;
  }

  /** method called if player has won.*/
  public void playerWin() {
    incrementWinStreak();
    incrementNumRoundsWon();
    printWinMessage();
    points += 3; //bonus for correctly guessing word
  }

  /** method called if player has loss.*/
  public void playerLoss() {
    resetWinStreak();
    incrementNumRoundsLost();
    printLossMessage();
  }

  /** method to check if round is finished.*/
  public void roundFinish() {
    if (hasPlayerWon()) {
      playerWin();
    } else {
      playerLoss();
      printGuessWord();
    }
    incrementNumRoundsPlayed();
    printRoundStats();
  }

  /** new round method.*/
  public void newRound() {
    selectWordToGuess();
    resetGuesses();
    resetCorrectlyGuessedLetters();
    firstGame = false;
    run();
  }

  /** method to get the number of correct guesses.*/
  public void correctGuess(char guess) {
    incrementNumTotalGuesses();
    insertCorrectLetterGuess(guess);
    incrementNumCorrectGuesses();
    incrementPoints();
  }

  /** method to get the number of correct guesses when given as string.*/
  public void correctGuess(String guess) {
    incrementNumTotalGuesses();
    incrementNumCorrectGuesses();
  }

  /** method to get the number of incorrect guesses.*/
  public void incorrectGuess() {
    incrementNumTotalGuesses();
    incrementNumIncorrectGuesses();
  }

  /** method to print how many guesses remaining after each turn.*/
  public void printNumIncorrectGuessesRemaining() {
    printlnToAllPlayers("You have " + (NUM_ALLOWED_INCORRECT_GUESSES - numRoundIncorrectGuesses)
        + " incorrect guesses remaining.");
  }

  /** method to print stats at end of the round.*/
  public void printRoundStats() {
    printlnToAllPlayers("===== ROUND STATS ====="
        + "\nPoints: " + getPoints()
        + "\nCurrent Win Streak: " + getWinStreak()
        + "\nTotal # of Guesses: " + getNumRoundTotalGuesses()
        + "\nTotal Correct Guesses: " + getNumRoundCorrectGuesses()
        + "\nTotal Incorrect Guesses: " + getNumRoundIncorrectGuesses());
  }

  /** method to print stats when players finish game.*/
  public void printGameStats() {
    printlnToAllPlayers("===== GAME STATS ====="
        + "\nTotal # of Rounds Played: " + getNumRoundsPlayed()
        + "\nTotal # of Rounds Won: " + getNumRoundsWon()
        + "\nTotal # of Rounds Lost: " + getNumRoundsLost());
  }

  /** prints win message.*/
  public void printWinMessage() {
    printlnToAllPlayers(winMessage);
  }

  /** prints loss message.*/
  public void printLossMessage() {
    printlnToAllPlayers(lossMessage);
  }

  /** prints word when players loose.*/
  public void printGuessWord() {
    printlnToAllPlayers("The word was: " + getWordToGuess());
  }

  /** prints invalid guesss.*/
  public void printInvalidGuess() {
    System.out.println(invalidGuess);
  }

  /** prints game instructions.*/
  public void printInstructions() {
    printlnToAllPlayers(instructions);
  }

  /** prints welcome message.*/
  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  /** method to print the updated board at the start of each round.*/
  public void printGuessWordPrompt() {
    printlnToAllPlayers("Word: " + getGuessWordProgress());
  }

  /** method to play one round of the game.*/
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
