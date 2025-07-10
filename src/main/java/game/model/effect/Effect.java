package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.Map;

public interface Effect {
  Map<ResourceType, Integer> resources();

  void applyTo(Bot bot);
}