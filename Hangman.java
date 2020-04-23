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
  private int numTotalGuesses;
  private int numCorrectGuesses;
  private int numIncorrectGuesses;
  private int winStreak;
  private int numGamesPlayed;
  private int numGamesWon;
  private int numGamesLost;
  private char[] correctlyGuessedLetters;

  public Hangman() {
    wordToGuess = selectWordToGuess();
    currGuess = "";
    winStreak = numCorrectGuesses = numIncorrectGuesses = numGamesPlayed = numGamesWon = numGamesLost = 0;
    correctlyGuessedLetters = new char[wordToGuess.length()];
    Arrays.fill(correctlyGuessedLetters, '_'); // Sets all characters in correctlyGuessedLetters to underscores, indicating that no letters have been correctly guessed yet
  }

  public void playGame() {
    printWelcomeMessage();
    printInstructions();
    promptEnterKey();
    printGuessWord(); //TODO delete after debugging complete
    while(!hasWon() && !hasLost()) {
      this.printGuessWordPrompt();
      this.setCurrGuess(this.getUserInput());
      this.checkIfGuessCorrect(this.getCurrGuess());
    } gameFinish();
    //TODO ask if user wants to play again
  }

  public String charArrayToString() {
    String s = "";
    for(char c : correctlyGuessedLetters) {
      s += c;
    }
    return s;
  }

  public String selectWordToGuess() {
    int indexOfWord = new Random().nextInt(19); //19 is the number of words or phrases listed in ./hangmanWordsAndPhrases.txt
    return FileIO.getLineFromFile("hangmanWords.txt", indexOfWord);
  }

  public static String convertStringToUnderscores(String s) {
    // Replaces all underscores to underscore and space '_ '
    // Extra space is for readability purposes and to distinguish different underscores
    return s.replaceAll("[_]", "_ ");
  }

  public void resetWinStreak() {
    winStreak = 0;
  }

  public void resetWordToGuess() {
    wordToGuess = selectWordToGuess();
  }

  public void incrementWinStreak() {
    winStreak++;
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

  public void incrementNumIncorrectGuesses() {
    numIncorrectGuesses++;
  }

  public void incrementNumCorrectGuesses() {
    numCorrectGuesses++;
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
    return convertStringToUnderscores(charArrayToString()).replaceAll("\\B", " ");
  }

  public int getNumTotalGuesses() {
    return numTotalGuesses;
  }

  public int getNumCorrectGuesses() {
    return numCorrectGuesses;
  }

  public int getNumIncorrectGuesses() {
    return numIncorrectGuesses;
  }

  public int getNumTotalGamesPlayed() {
    return numGamesPlayed;
  }

  public int getNumGamesWon() {
    return numGamesWon;
  }

  public int getNumGamesLost() {
    return numGamesLost;
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
    if(numIncorrectGuesses == NUM_ALLOWED_INCORRECT_GUESSES) {
      return true;
    }
    return false;
  }

  public void playerWin() {
    incrementWinStreak();
    incrementNumGamesWon();
    printWinMessage();
  }

  public void playerLoss() {
    resetWinStreak();
    incrementNumGamesLost();
    printLossMessage();
  }

  public void gameFinish() {
    if(hasWon()) {
      playerWin();
    } else {
      playerLoss();
    }
    incrementNumGamesPlayed();
    printStats();
  }

  public void correctGuess(char guess) {
    replaceUnderscoresWithCorrectlyGuessedLetter(guess);
    incrementNumCorrectGuesses();
  }

  public void incorrectGuess(String guess) {
    incrementNumIncorrectGuesses();
    //TODO add guess to previous guesses
  }

  public void incorrectGuess(char guess) {
    incrementNumIncorrectGuesses();
    //TODO add guess to previous guesses
  }

  public void printStats() {
    System.out.println("=== ROUND STATS ===" +
    "\nThe word or phrase was: " + getWordToGuess() +
    "\nTotal # of Guesses: " + getNumTotalGuesses() +
    "\nTotal Correct Guesses: " + getNumCorrectGuesses() +
    "\nTotal Incorrect Guesses: " + getNumIncorrectGuesses() +
    "\n=== GAME STATS ===" +
    "\nCurrent Win Streak: " + getWinStreak() +
    "\nTotal # of Games Played: " + getNumTotalGamesPlayed() +
    "\nTotal # of Games Won: " + getNumGamesWon() +
    "\nTotal # of Games Lost: " + getNumGamesLost());
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
    System.out.println("The word was " + getWordToGuess());
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
    System.out.println("Would you like to play again?");
    return getUserInput();
  }

  //TODO hint command "would you like a hint?"
}
