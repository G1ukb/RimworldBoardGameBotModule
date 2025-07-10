package game.model.tile;

import game.model.action.ResourceType;
import game.model.effect.Effect;
import game.model.effect.TileEffect;

import java.util.Map;

public abstract class BasicTile {

  private final TileCategory category;
  private final String symbol;
  private final String tileName;
  private final Effect onDiscover;
  private final Map<Integer, Effect> resourceRollTable;

  public BasicTile(
      TileCategory category,
      String symbol,
      String tileName,
      Effect onDiscover,
      Map<Integer, Effect> resourceRollTable) {
    this.category = category;
    this.symbol = symbol;
    this.tileName = tileName;

    this.onDiscover = onDiscover;
    this.resourceRollTable = resourceRollTable;
  }

  public TileCategory category() {
    return category;
  }

  public String symbol() {
    return symbol;
  }

  public String tileName() {
    return tileName;
  }

  public boolean hasResource(ResourceType type) {
    return resourceRollTable.values().stream().anyMatch(e -> e.resources().containsKey(type));
  }

  public Effect effectForRoll(int roll) {
    return resourceRollTable.getOrDefault(roll, TileEffect.NONE);
  }

  public Effect discoverEffect() {
    return onDiscover;
  }
}
