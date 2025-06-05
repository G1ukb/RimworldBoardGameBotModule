package game.model;

import game.model.tile.Tile;
import game.strategies.BotStrategy;

public class Bot {
  public BotStrategy strategy;
  public Tile currentTile;
  public Integer maximumActions;

  public Bot(BotStrategy botStrategy, Tile startTile, Integer maximumActions) {
    this.strategy = botStrategy;
    this.currentTile = startTile;
    this.maximumActions = maximumActions;

    actionsRemaining = maximumActions;
    isDone = false;
  }

  public Integer actionsRemaining;
  public Boolean isDone;
}
