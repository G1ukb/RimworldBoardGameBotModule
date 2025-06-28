package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.Map;
import java.util.function.Consumer;

public record TileEffect(Map<ResourceType, Integer> resources,
                         Consumer<Bot> botEffect) {

  public static final TileEffect NONE = new TileEffect(Map.of(), b -> {});

  public void applyTo(Bot bot) {
    botEffect.accept(bot);
  }

}
