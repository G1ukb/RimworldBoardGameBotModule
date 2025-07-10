package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.List;
import java.util.Map;

public class ChoiceEffect implements Effect {
  private final List<Effect> options;
  private Effect chosen;

  public ChoiceEffect(List<Effect> options) {
    this.options = options;
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
    // TODO use strategy decision; choose first for now
    chosen = options.getFirst();
    chosen.applyTo(bot);
  }
}
