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

  /**
   * Constructs a new single-player game instance of Hangman.
   */
  public Hangman() {
    super();
    selectWordToGuess();
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  /**
   * Constructs a new dual-player game instance of Hangman using {@code p1} and {@code p2}.
   * @param p1
   *        Player 1
   * @param p2
   *        Player 2
   */
  public Hangman(Player p1, Player p2) {
    super(p1, p2);
    selectWordToGuess();
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Indicates no correctly guessed letters
  }

  /**
   * Starts instance of Hangman game.
   */
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

  /**
   * Sets guess word to word selected by getting random line from hangmanWords.txt.
   */
  public void selectWordToGuess() {
    int wordIndex = new Random().nextInt(19); //19 is the # of words listed in ./hangmanWords.txt
    wordToGuess = FileIO.getLineFromFile("hangmanWords.txt", wordIndex);
  }

  /**
   * Resets win streak of player to 0.
   */
  public void resetWinStreak() {
    winStreak = 0;
  }

  /**
   * Resets number of total, incorrect, and correct guesses when round finishes.
   */
  public void resetGuesses() {
    numRoundTotalGuesses = numRoundIncorrectGuesses = numRoundCorrectGuesses = 0;
  }

  /**
   * Resets {@code correctlyGuessedLetters} to all underscores, indicating no letters have been correctly guessed.
   */
  public void resetCorrectlyGuessedLetters() {
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_');
  }

  /**
   * Increments both players' win streaks by 1.
   */
  public void incrementWinStreak() {
    winStreak++;
  }

  /**
   * Increments number of rounds played in the current game by 1.
   */
  public void incrementNumRoundsPlayed() {
    numRoundsPlayed++;
  }

  /**
   * Increments number of rounds won in the current game by 1.
   */
  public void incrementNumRoundsWon() {
    numRoundsWon++;
  }

  /**
   * Increments number of rounds lost in the current game by 1.
   */
  public void incrementNumRoundsLost() {
    numRoundsLost++;
  }

  /**
   * Increments number of total guesses in the current game by 1.
   */
  public void incrementNumTotalGuesses() {
    numRoundTotalGuesses++;
  }

  /**
   * Increments number of incorrect guesses in the current game by 1.
   */
  public void incrementNumIncorrectGuesses() {
    numRoundIncorrectGuesses++;
  }

  /**
   * Increments number of correct guesses in the current game by 1.
   */
  public void incrementNumCorrectGuesses() {
    numRoundCorrectGuesses++;
  }

  /**
   * Increments both players' points by 1.
   */
  public void incrementPoints() {
    points++;
  }

  /**
   * Sets current guess.
   * @param guess
   *        Player's current guess
   */
  public void setCurrGuess(String guess) {
    this.currGuess = guess;
  }

  /**
   * Gets current guess.
   * @return  current guess
   */
  public String getCurrGuess() {
    return currGuess;
  }

  /**
   * Returns number of consecutive wins.
   * @return  number of consecutive wins
   */
  public int getWinStreak() {
    return winStreak;
  }

  /**
   * Returns current guess word.
   * @return  current guess word
   */
  public String getWordToGuess() {
    return wordToGuess;
  }

  /**
   * Returns character array of letters that have already been guessed which appear in the guess word.
   * @return  the array of characters correctly guessed by the player(s)
   */
  public char[] getCorrectlyGuessedLetters() {
    return correctlyGuessedLetters;
  }

  /**
   * Returns progress of guess word, with underscores indicating unguessed letters. Displays progress of guess word to player(s)
   *
   * Converts guesses to string format and adds space after each letter in string for improved readability.
   *
   * Examples of guess word progress print statements are in the following table.
   *
   * <table class="plain">
   * <thead>
   * <tr>
   *   <th scope="col">Print Result</th>
   *   <th scope="col">Guess Word</th>
   *   <th scope="col">Letters Guessed</th>
   *   <th scope="col">Outstanding Letters</th>
   * </tr>
   * </thead>
   * <tbody>
   * <tr>
   *   <td>d _ _ _</td>
   *   <td>data</td>
   *   <td>d</td>
   *   <td>a,t</td>
   * </tr>
   * <tr>
   *   <td>p o l y m o r p h _ _ m</td>
   *   <td>polymorphism</td>
   *   <td>p,o,l,y,m,h</td>
   *   <td>i,s</td>
   * </tr>
   * <tr>
   *   <td>j a v a</td>
   *   <td>java</td>
   *   <td>j,v,a</td>
   *   <td>(none)</td>
   * </tr>
   * <tr>
   *   <td>b _ o _ k _ h _ i n</td>
   *   <td>blockchain</td>
   *   <td>b,o,k,h,i</td>
   *   <td>l,c,a</td>
   * </tr>
   * </tbody>
   * </table>
   *
   * @return  progress of guess word in string format, with underscores indicating unguessed letters
   */
  public String getGuessWordProgress() {
    return StringManipulation.convertStringToUnderscores(
            StringManipulation.charArrayToString(getCorrectlyGuessedLetters())) // Converts guesses to string form
            .replaceAll("\\B", " "); // Adds space after each letter for improved readability
  }

  /**
   * Returns number of total guesses.
   * @return  number of total guesses
   */
  public int getNumRoundTotalGuesses() {
    return numRoundTotalGuesses;
  }

  /**
   * Returns number of correct guesses.
   * @return  number of correct guesses
   */
  public int getNumRoundCorrectGuesses() {
    return numRoundCorrectGuesses;
  }

  /**
   * Returns number of incorrect guesses.
   * @return  number of incorrect guesses
   */
  public int getNumRoundIncorrectGuesses() {
    return numRoundIncorrectGuesses;
  }

  /**
   * Returns number of rounds played.
   * @return  number of rounds played
   */
  public int getNumRoundsPlayed() {
    return numRoundsPlayed;
  }

  /**
   * Returns number of rounds won.
   * @return  number of rounds won
   */
  public int getNumRoundsWon() {
    return numRoundsWon;
  }

  /**
   * Returns number of rounds lost.
   * @return  number of rounds lost
   */
  public int getNumRoundsLost() {
    return numRoundsLost;
  }

  /**
   * Returns number points accumulated by player(s).
   * @return  number of points accumulated by player(s)
   */
  public int getPoints() {
    return points;
  }

  /**
   * Determines whether {@code guess} is a correct guess
   *
   * A correct guess can be a character that appears in the guess word, multiple characters
   * separated by commas that appear in the guess word, or the entire guess word itself
   * @param guess
   *        the player's current guess
   */
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

  /**
   * Assert whether a character guess is correct.
   *
   * If the guess is correct, {@link Hangman#correctGuess(char)} is called. Else, {@link Hangman#incorrectGuess()} is called.
   *
   * A character guess is considered correct if it appears in the guess word and has not
   * already been guessed.
   * @param c
   *        the character guessed
   */
  public void assertCorrectGuess(char c) {
    if (characterInGuessWord(c) && guessNotInPreviousGuesses(c)) {
      correctGuess(c);
    } else {
      incorrectGuess();
    }
  }

  /**
   * Assert whether a string guess is correct.
   *
   * If the guess is correct, {@link Hangman#correctGuess(String)} is called. Else, {@link Hangman#incorrectGuess()} is called.
   *
   * A character guess is considered correct if it is equal to the guess word, ignoring case.
   * @param s
   *        the string guessed
   */
  public void assertCorrectGuess(String s) {
    if (getWordToGuess().equalsIgnoreCase(s)) {
      correctGuess(s);
    } else {
      incorrectGuess();
    }
  }

  /**
   * Returns whether a character guessed appears in the guess word.
   * @param c
   *        the character guessed
   * @return  {@code true} if the character appears in guess word; {@code false} otherwise
   */
  public boolean characterInGuessWord(char c) {
    return getWordToGuess().indexOf(c) != -1;
  }

  /**
   * Returns whether a character guessed has already been guessed.
   * @param c
   *        the character guessed
   * @return  {@code true} if character was not previously guessed; {@code false} otherwise
   */
  public boolean guessNotInPreviousGuesses(char c) {
    for (char character : correctlyGuessedLetters) {
      if (character == c) {
        return false;
      }
    }
    return true;
  }

  /**
   * Adds correctly guessed letter to character array of correctly guess letters
   * @param c
   *        the correctly guessed letter
   */
  public void insertCorrectLetterGuess(char c) {
    for (int pos = 0; pos < getWordToGuess().length(); pos++) {
      if (wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
  }

  /**
   * Returns whether the player(s) has/have won.
   *
   * A player or players win(s) if their current guess is equal to the guess word, ignoring case,
   * or if the character array of correctly guessed letters in string form is equal to the
   * guess word, ignoring case.
   * @return  {@code true} if player(s) has/have met win conditions; {@code false} otherwise
   */
  public boolean hasPlayerWon() {
    return getWordToGuess().equalsIgnoreCase(currGuess)
            || getWordToGuess().equalsIgnoreCase(
                    StringManipulation.charArrayToString(getCorrectlyGuessedLetters()));
  }

  /**
   * Returns whether the player(s) has/have won.
   *
   * A player or players lose(s) if the number of incorrect guesses in the current round is
   * equal to the number of allowed incorrect guesses.
   * @return  {@code true} if player(s) has/have lost; {@code false} otherwise
   */
  public boolean hasPlayerLost() {
    return numRoundIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES;
  }

  /**
   * Increments win streak by 1, increments number of rounds won by 1, prints win message,
   * and adds 3 points to player's or players' score(s).
   */
  public void playerWin() {
    incrementWinStreak();
    incrementNumRoundsWon();
    printWinMessage();
    points += 3;
  }

  /**
   * Resets win streak, increments number of rounds lost by 1, and prints loss message.
   */
  public void playerLoss() {
    resetWinStreak();
    incrementNumRoundsLost();
    printLossMessage();
  }

  /**
   * Finishes the round and runs {@code playerWin} or {@code playerLoss} method, depending on
   * whether player(s) won or lost.
   *
   * Prints the guess word, increments the number of rounds
   * played by 1, and prints round statistics.
   */
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

  /**
   * Starts a new round.
   *
   * Sets new random guess word, resets number of previous total, correct, and incorrect guesses
   * and correctly guessedLetters, sets {@code firstGame} to false, indicating that this is at
   * least the second game played, and starts a new round.
   */
  public void newRound() {
    selectWordToGuess();
    resetGuesses();
    resetCorrectlyGuessedLetters();
    firstGame = false;
    run();
  }

  /**
   * Indicates that a letter has been correctly guessed.
   *
   * Increments number of total and correct guesses by 1, adds letter to correct letter guesses character
   * array, and increments the player's or players' points by 1.
   * @param guess
   *        the character guessed
   */
  public void correctGuess(char guess) {
    incrementNumTotalGuesses();
    incrementNumCorrectGuesses();
    incrementPoints();
    insertCorrectLetterGuess(guess);
  }

  /**
   * Indicates that the guess word has been correctly guessed.
   *
   * Increments number of total guesses and correct guesses by 1.
   * @param guess
   *        the string guessed
   */
  public void correctGuess(String guess) {
    incrementNumTotalGuesses();
    incrementNumCorrectGuesses();
  }

  /**
   * Indicates that a guess was incorrect.
   *
   * Increments number of total and incorrect guesses by 1.
   */
  public void incorrectGuess() {
    incrementNumTotalGuesses();
    incrementNumIncorrectGuesses();
  }

  /**
   * Prints to player(s) how many incorrect guesses remain.
   */
  public void printNumIncorrectGuessesRemaining() {
    printlnToAllPlayers("You have " + (NUM_ALLOWED_INCORRECT_GUESSES - numRoundIncorrectGuesses)
        + " incorrect guesses remaining.");
  }

  /**
   * Prints round-specific statistics including points, current win streak, and the number of total,
   * correct, and incorrect guesses.
   */
  public void printRoundStats() {
    printlnToAllPlayers("===== ROUND STATS ====="
        + "\nPoints: " + getPoints()
        + "\nCurrent Win Streak: " + getWinStreak()
        + "\nTotal # of Guesses: " + getNumRoundTotalGuesses()
        + "\nTotal Correct Guesses: " + getNumRoundCorrectGuesses()
        + "\nTotal Incorrect Guesses: " + getNumRoundIncorrectGuesses());
  }

  /**
   * Prints game-specific statistics including number of total rounds played, won, and lost.
   */
  public void printGameStats() {
    printlnToAllPlayers("===== GAME STATS ====="
        + "\nTotal # of Rounds Played: " + getNumRoundsPlayed()
        + "\nTotal # of Rounds Won: " + getNumRoundsWon()
        + "\nTotal # of Rounds Lost: " + getNumRoundsLost());
  }

  /**
   * Prints win message to player(s)
   */
  public void printWinMessage() {
    printlnToAllPlayers(winMessage);
  }

  /**
   * Prints loss message to player(s)
   */
  public void printLossMessage() {
    printlnToAllPlayers(lossMessage);
  }

  /**
   * Prints guess word to player(s)
   */
  public void printGuessWord() {
    printlnToAllPlayers("The word was: " + getWordToGuess());
  }

  /**
   * Prints invalid guess message to player(s)
   */
  public void printInvalidGuess() {
    System.out.println(invalidGuess);
  }

  /**
   * Prints instructions to player(s)
   */
  public void printInstructions() {
    printlnToAllPlayers(instructions);
  }

  /**
   * Prints welcome message to player(s)
   */
  public void printWelcomeMessage() {
    printlnToAllPlayers(welcomeMessage);
  }

  /**
   * Displays progress of guess word to player(s)
   */
  public void printGuessWordPrompt() {
    printlnToAllPlayers("Word: " + getGuessWordProgress());
  }

  /**
   * Plays through one round of Hangman game.
   * @throws IOException
   */
  public void tick() throws IOException {
    printNumIncorrectGuessesRemaining();
    printGuessWordPrompt();
    printWhoseTurn();
    setCurrGuess(getInputFromPlayerWithCurrTurn());
    printlnToAllPlayers("Guess: " + getCurrGuess());
    checkIfGuessCorrect(getCurrGuess());
    switchTurn();
  }
}
