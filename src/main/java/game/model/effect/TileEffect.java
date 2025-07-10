package game.model.effect;

import game.model.Bot;
import game.model.action.ResourceType;

import java.util.Map;
import java.util.function.Consumer;

public record TileEffect(Map<ResourceType, Integer> resources, Consumer<Bot> botEffect)
    implements Effect {

  public static final TileEffect NONE = new TileEffect(Map.of(), b -> {});
  public static final TileEffect SKIP_TURN =
    new TileEffect(Map.of(),
      b -> {
        b.actionsRemaining = 0;
        b.isDone = true;
      });

  @Override
  public void applyTo(Bot bot) {
    botEffect.accept(bot);
  }
}
