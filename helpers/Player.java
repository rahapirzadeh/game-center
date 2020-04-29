package helpers;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {

  private static final AtomicInteger count = new AtomicInteger(0); //reference: https://stackoverflow.com/questions/24305830/java-auto-increment-id
  private final int playerID;
  private String username;
  private PrintWriter outFromPlayer;
  private BufferedReader inFromPlayer;

  public Player() {
    this.playerID = count.incrementAndGet();
    this.username = "helpers.Player " + this.playerID;
  }

  public Player(int playerID) {
    this.playerID = playerID;
    this.username = "helpers.Player " + playerID;
  }

  public Player(int playerID, String username) {
    this.playerID = playerID;
    this.username = username;
  }

  public Player(String username) {
    this.playerID = count.incrementAndGet();
    this.username = username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  public int getPlayerID() {
    return this.playerID;
  }

  public PrintWriter getOutFromPlayer() {
    return outFromPlayer;
  }

  public void setOutFromPlayer(PrintWriter outFromPlayer) {
    this.outFromPlayer = outFromPlayer;
  }

  public BufferedReader getInFromPlayer() {
    return inFromPlayer;
  }

  public void setInFromPlayer(BufferedReader inFromPlayer) {
    this.inFromPlayer = inFromPlayer;
  }
}
