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

  public Player() {
    this.playerID = count.incrementAndGet();
    this.username = "Player " + this.playerID;
    setNewPlayerIO();
  }

  public Player(int playerID) {
    this.playerID = playerID;
    this.username = "Player " + playerID;
    setNewPlayerIO();
  }

  public Player(int playerID, String username) {
    this.playerID = playerID;
    this.username = username;
    setNewPlayerIO();
  }

  public Player(String username) {
    this.playerID = count.incrementAndGet();
    this.username = username;
    setNewPlayerIO();
  }

  public void setNewPlayerIO() {
    this.setOutToPlayer(new PrintWriter(System.out, true));
    this.setInFromPlayer(new BufferedReader(new InputStreamReader(System.in)));
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

  public PrintWriter getOutToPlayer() {
    return outToPlayer;
  }

  public void setOutToPlayer(PrintWriter outToPlayer) {
    this.outToPlayer = outToPlayer;
  }

  public BufferedReader getInFromPlayer() {
    return inFromPlayer;
  }

  public void setInFromPlayer(BufferedReader inFromPlayer) {
    this.inFromPlayer = inFromPlayer;
  }

  public boolean isReady() {
    return isReady;
  }

  public void setReady(boolean ready) {
    isReady = ready;
  }
}
