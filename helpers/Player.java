package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {

  private static final AtomicInteger count = new AtomicInteger(0); //reference: https://stackoverflow.com/questions/24305830/java-auto-increment-id
  private final int playerID;
  private String username;
  private PrintWriter outToPlayer;
  private BufferedReader inFromPlayer;
  private boolean isReady;

  /** method to pass in a player.*/
  public Player() {
    this.playerID = count.incrementAndGet();
    this.username = "Player " + this.playerID;
    setNewPlayerIO();
  }

  /** method to pass in both players.*/
  public Player(int playerID) {
    this.playerID = playerID;
    this.username = "Player " + playerID;
    setNewPlayerIO();
  }

  /** method to pass in both players.*/
  public Player(int playerID, String username) {
    this.playerID = playerID;
    this.username = username;
    setNewPlayerIO();
  }

  /** method to pass in both players.*/
  public Player(String username) {
    this.playerID = count.incrementAndGet();
    this.username = username;
    setNewPlayerIO();
  }

  /** method to set new player.*/
  public void setNewPlayerIO() {
    this.setOutToPlayer(new PrintWriter(System.out, true));
    this.setInFromPlayer(new BufferedReader(new InputStreamReader(System.in)));
  }

  /** method to set player username.*/
  public void setUsername(String username) {
    this.username = username;
  }

  /** method to get player username.*/
  public String getUsername() {
    return this.username;
  }

  /** method to get player id.*/
  public int getPlayerID() {
    return this.playerID;
  }

  /** method to print output to player.*/
  public PrintWriter getOutToPlayer() {
    return outToPlayer;
  }

  /** method to get output to player.*/
  public void setOutToPlayer(PrintWriter outToPlayer) {
    this.outToPlayer = outToPlayer;
  }

  /** method to read in from player.*/
  public BufferedReader getInFromPlayer() {
    return inFromPlayer;
  }

  /** method to set input from player.*/
  public void setInFromPlayer(BufferedReader inFromPlayer) {
    this.inFromPlayer = inFromPlayer;
  }

  /** method to return if player is ready.*/
  public boolean isReady() {
    return isReady;
  }

  /** method to get if player is ready.*/
  public void setReady(boolean ready) {
    isReady = ready;
  }
}
