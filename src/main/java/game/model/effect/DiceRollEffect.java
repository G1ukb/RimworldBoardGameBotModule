package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.Map;
import java.util.Random;

public class DiceRollEffect implements Effect {
  private final Map<Integer, Effect> effectTable;
  private final Random rnd = new Random();
  private Effect chosen;
  private int lastRoll;

  public DiceRollEffect(Map<Integer, Effect> effectTable) {
    this.effectTable = effectTable;
  }

  @Override
  public Map<ResourceType, Integer> resources() {
    if (chosen != null) {
      return chosen.resources();
    }
    return Map.of();
  }

  @Override
  public void applyTo(Bot bot) {
    lastRoll = rnd.nextInt(6) + 1;
    chosen = effectTable.getOrDefault(lastRoll, TileEffect.NONE);
    chosen.applyTo(bot);
  }

  public int lastRoll() {
    return lastRoll;
  }

  public Effect chosen() {
    return chosen;
  }
}