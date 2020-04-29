package helpers;

import java.util.concurrent.atomic.AtomicInteger;

public class Turn {

  private static final AtomicInteger count = new AtomicInteger(0); //reference: https://stackoverflow.com/questions/24305830/java-auto-increment-id
  private final int turnID;
  private final Player player;
  private String move;

  public Turn() {
    this.turnID = count.incrementAndGet();
    this.player = new Player();
    this.move = "";
  }

  public Turn(Player player) {
    this.turnID = count.incrementAndGet();
    this.player = player;
    this.move = "";
  }

  public Turn(Player player, String move) {
    this.turnID = count.incrementAndGet();
    this.player = player;
    this.move = move;
  }

  public void setMove(String move) {
    this.move = move;
  }

  public Player getPlayer() {
    return this.player;
  }

  public int getTurnID() {
    return this.turnID;
  }

  public String getMove() {
    return this.move;
  }
}
