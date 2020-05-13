package helpers;

import java.util.concurrent.atomic.AtomicInteger;

public class Turn {

  private static final AtomicInteger count = new AtomicInteger(0); //reference: https://stackoverflow.com/questions/24305830/java-auto-increment-id
  private final int turnID;
  private final Player player;
  private String move;

  /** method to pass in a turn.*/
  public Turn() {
    this.turnID = count.incrementAndGet();
    this.player = new Player();
    this.move = "";
  }

  /** method to pass in a turn to a player.*/
  public Turn(Player player) {
    this.turnID = count.incrementAndGet();
    this.player = player;
    this.move = "";
  }

  /** method to play a turn for a player.*/
  public Turn(Player player, String move) {
    this.turnID = count.incrementAndGet();
    this.player = player;
    this.move = move;
  }
  /** method to set player move.*/
  public void setMove(String move) {
    this.move = move;
  }
  /** method to get player.*/
  public Player getPlayer() {
    return this.player;
  }
  /** method to turn id.*/
  public int getTurnID() {
    return this.turnID;
  }
  /** method to get player move.*/
  public String getMove() {
    return this.move;
  }
}
