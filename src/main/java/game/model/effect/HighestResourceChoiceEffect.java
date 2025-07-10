package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.List;
import java.util.Map;

public class HighestResourceChoiceEffect implements Effect {
  private final List<TileEffect> options;
  private TileEffect chosen;

  public HighestResourceChoiceEffect(List<TileEffect> options) {
    this.options = options;
  }

  public HighestResourceChoiceEffect(int howMuch) {
    this.options =
        List.of(
            new TileEffect(Map.of(ResourceType.FOOD, howMuch), b -> {}),
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, -howMuch), b -> {}),
            new TileEffect(Map.of(ResourceType.MEDICINE, howMuch), b -> {}),
            new TileEffect(Map.of(ResourceType.SPECIAL, howMuch), b -> {}));
  }

  @Override
  public Map<ResourceType, Integer> resources() {
    if (chosen != null) {
      return chosen.resources();
    }
    return options.isEmpty() ? Map.of() : options.getFirst().resources();
  }

  @Override
  public void applyTo(Bot bot) {
    if (options.isEmpty()) {
      return;
    }
    chosen = options.getFirst();
    int maxAmount = -1;
    for (TileEffect effect : options) {
      if (effect.resources().size() != 1) continue;
      Map.Entry<ResourceType, Integer> entry = effect.resources().entrySet().iterator().next();
      int amount = bot.resources.get(entry.getKey());
      if (amount > maxAmount) {
        maxAmount = amount;
        chosen = effect;
      }
    }
    chosen.applyTo(bot);
  }
}
