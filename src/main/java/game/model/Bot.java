package game.model;

import game.model.tile.Tile;
import game.model.ResourceType;
import game.strategies.BotStrategy;
import java.util.EnumMap;
import java.util.Map;

/** Bot that moves around the map and stores resources and state. */

public class Bot {
  public BotStrategy strategy;
  public Tile currentTile;
  public Integer maximumActions;
  // map of resources currently held by the bot
  public Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);

  // health and psychological state
  public Integer healthCap;
  public Integer health;
  public Integer psycheCap;
  public Integer psyche;

  public Bot(BotStrategy botStrategy, Tile startTile, Integer maximumActions) {
    this.strategy = botStrategy;
    this.currentTile = startTile;
    this.maximumActions = maximumActions;

    actionsRemaining = maximumActions;
    isDone = false;

    // initialize resources to zero
    for (ResourceType type : ResourceType.values()) {
      resources.put(type, 0);
    }

    // default state capacities
    healthCap = 100;
    health = healthCap;
    psycheCap = 100;
    psyche = psycheCap;
  }

  public Integer actionsRemaining;
  public Boolean isDone;
}
