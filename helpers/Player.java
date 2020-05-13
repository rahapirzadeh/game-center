package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The player class deals with naming the player and getting and sending input.
 */
public class Player {

  private static final AtomicInteger count = new AtomicInteger(0); //reference: https://stackoverflow.com/questions/24305830/java-auto-increment-id
  private final int playerID;
  private String username;
  private PrintWriter outToPlayer;
  private BufferedReader inFromPlayer;

  /**
  * Constructs a new instance of a player.
  */
  public Player() {
    this.playerID = count.incrementAndGet();
    this.username = "Player " + this.playerID;
    setNewPlayerIO();
  }

  /**
  * Constructs a new instance of a player with an ID.
  * @param playerID
  *        player id for both players
  */
  public Player(int playerID) {
    this.playerID = playerID;
    this.username = "Player " + playerID;
    setNewPlayerIO();
  }

  /**
  * Constructs a new instance of a player with an ID and username.
  * @param playerID
  *        player id for both players
  * @param username
  *        username of players
  */
  public Player(int playerID, String username) {
    this.playerID = playerID;
    this.username = username;
    setNewPlayerIO();
  }

  /**
  * Constructs a new instance of a player with a username.
  * @param username
  *        username of players
  */
  public Player(String username) {
    this.playerID = count.incrementAndGet();
    this.username = username;
    setNewPlayerIO();
  }

  /**
  * Prints output to player and gets input from player.
  */
  public void setNewPlayerIO() {
    this.setOutToPlayer(new PrintWriter(System.out, true));
    this.setInFromPlayer(new BufferedReader(new InputStreamReader(System.in)));
  }

  /**
  * Sets the username of the player.
  * @param username
  *        player username
  */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
  * Gets the username of a player.
  * @return the username of the player
  */
  public String getUsername() {
    return this.username;
  }

  /**
  * Gets the ID of a player.
  * @return the ID of the player
  */
  public int getPlayerID() {
    return this.playerID;
  }

  /**
   * Returns {@code PrintWriter} object from player
   * @return this player's {@code PrintWriter} object
   */
  public PrintWriter getOutToPlayer() {
    return outToPlayer;
  }

  /**
  * Sets the {@code PrintWriter} of this player
  * @param outToPlayer
  *        player's new {@code PrintWriter}
  */
  public void setOutToPlayer(PrintWriter outToPlayer) {
    this.outToPlayer = outToPlayer;
  }

  /**
   * Gets {@code BufferedReader} object from player
   * @return this player's {@code BufferedReader} object
   */
  public BufferedReader getInFromPlayer() {
    return inFromPlayer;
  }

  /**
  * Sets the input from the player.
  * @param inFromPlayer
  *        string from player
  */
  public void setInFromPlayer(BufferedReader inFromPlayer) {
    this.inFromPlayer = inFromPlayer;
  }
}
