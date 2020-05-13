package helpers;

import java.util.concurrent.atomic.AtomicInteger;

/**
* Class that defines current turn for the player.
*/
public class Turn {
  private final Player player;
  private String move;

  /**
  * Constructs a new instance of a game turn.
  */
  public Turn() {
    this.player = new Player();
    this.move = "";
  }

  /**
  * Constructs a new instance of a game turn for a player.
  * @param player
  *        player
  */
  public Turn(Player player) {
    this.player = player;
    this.move = "";
  }

  /**
  * Constructs a new instance of a game turn for a player with a move.
  * @param player
  *        player
  * @param move
  *        player move
  */
  public Turn(Player player, String move) {
    this.player = player;
    this.move = move;
  }

  /**
  * Sets a players move.
  * @param move
  *        player move
  */
  public void setMove(String move) {
    this.move = move;
  }

  /**
  * Gets player.
  * @return player
  */
  public Player getPlayer() {
    return this.player;
  }

  /**
  * Gets player move.
  * @return move
  */
  public String getMove() {
    return this.move;
  }
}
