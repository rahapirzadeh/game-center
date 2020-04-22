import java.util.Random;
import java.util.Scanner;

public class Hangman {
  public static void main(String[] args) {
    playGame();
  }

  private String wordOrPhraseToGuess;
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
  private boolean hasWon;
  private boolean hasGuessesRemaining;

  public Hangman() {
    wordToGuess = selectWordToGuess();
    currGuess = "";
    winStreak = numCorrectGuesses = numIncorrectGuesses = numGamesPlayed = numGamesWon = numGamesLost = 0;
    correctlyGuessedLetters = new char[wordToGuess.length()];
    // Sets all characters in correctlyGuessedLetters to underscores, indicating that no letters have been correctly guessed yet
    Arrays.fill(correctlyGuessedLetters, '_');
    hasWon = false;
    hasGuessesRemaining = true;
  }

  public static void playGame() {
    Hangman hm = new Hangman();
    printWelcomeMessage();
    printInstructions();
    System.out.println("Word: " + hm.getWordToGuess()); //TODO delete after debugging complete
    // while win and input == exit are false && hasGuessesRemaining == true
    while(true) {
      hm.printGuessWordPrompt();
      hm.setCurrGuess(hm.getUserGuess());
      hm.checkIfGuessCorrect(hm.getCurrGuess());
      // hm.checkWinAndLossConditions();
    }
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
    System.out.print("Word or Phrase: " + correctlyGuessedLettersArrayToString() +
    "\nYour Guess: ");
  }

  public void printInstructions() {
  public String getUserGuess() {
    String guess = "";
    Scanner scanner = new Scanner(System.in);
    if(scanner.hasNextLine()) {
      guess = scanner.nextLine();
    }
    return guess.strip(); // Strips leading and trailing whitespace
  }

  public void checkIfGuessCorrect(String guess) {
    if(guess.equals("") || guess.equals("help")) {
      printInstructions();
    } else if(guess.length() > 1) {
      if(guess.contains(",")) {
        char[] characterGuesses = parseAndReturnCommaSeparatedGuess(guess);
        for (char character : characterGuesses) {
          if(isCharacterInWord(character)) {
            replaceUnderscoresWithCorrectlyGuessedLetter(character);
          } else {
            incrementNumIncorrectGuesses();
          }
        }
      } else if(getWordToGuess().equals(guess)) {
        playerWin();
      }
    } else if(guess.length() == 1) {
      if(isCharacterInWord(guess.charAt(0))) {
        replaceUnderscoresWithCorrectlyGuessedLetter(guess.charAt(0));
      } else {
        incrementNumIncorrectGuesses();
      }
    }
  }

  public boolean isCharacterInWord(char character) {
    if(getWordToGuess().contains(Character.toString(character))) {
      incrementNumCorrectGuesses();
      return true;
    } else {
      incrementNumIncorrectGuesses();
      return false;
    }
  }

  public static char[] parseAndReturnCommaSeparatedGuess(String guess) {
    guess.replaceAll("\\s", "").replaceAll("[,]", "");
    char[] characterGuesses = new char[guess.length()];
    for (int pos = 0; pos < guess.length(); pos++) {
      characterGuesses[pos] = guess.charAt(pos);
    }
    return characterGuesses;
  }

  public void replaceUnderscoresWithCorrectlyGuessedLetter(char c) {
    for(int pos = 0; pos < getWordToGuess().length(); pos++) {
      if(wordToGuess.charAt(pos) == c) {
        correctlyGuessedLetters[pos] = c;
      }
    }
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

  public void printStats() {
    System.out.println("=== ROUND STATS ===" +
    "\nThe word or phrase was: " + getWordToGuess() +
    "\nTotal # of Guesses: " + getNumTotalGuesses() +
    "\nTotal Correct Guesses: " + getNumCorrectGuesses() +
    "\nTotal Incorrect Guesses: " + getNumIncorrectGuesses() +
    "=== GAME STATS ===" +
    "\nCurrent Win Streak: " + getWinStreak() +
    "\nTotal # of Games Played: " + getNumTotalGamesPlayed() +
    "\nTotal # of Games Won: " + getNumGamesWon() +
    "\nTotal # of Games Lost: " + getNumGamesLost());
  }

  public void printWinMessage() {
    System.out.println("Congratulations.. you won!");
  }

  public void printLossMessage() {
    System.out.println("Sorry.. you lost!");
  }
  public static void printInstructions() {
    System.out.println("Guess multiple letters by separating them with commas (ex: 'a,s,d,f')." +
    "\nGuess the whole word by typing in your entire guess (ex: 'computer')." +
    "\nFor a hint, type 'hint'." +
    // TODO "\nTo print these instructions again, type 'help'." +
    "\nTo exit the game, type 'exit'.");
    promptEnterKey();
  }

  public void printWelcomeMessage() {
    System.out.println("Hello, and welcome to Hangman! In this game, the computer will generate " +
    "a word or phrase, and you must guess it by selecting letters! \nGuess the word or phrase " +
    "within a certain number of guesses, or you lose!");
  }

  //TODO hint command "would you like a hint?"
}
