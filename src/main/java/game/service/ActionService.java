package game.service;

import game.model.Bot;
import game.model.action.ActionType;
import game.model.action.ResourceType;
import game.model.effect.Effect;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ActionService {

  private final MapService mapService;
  private final ResourceService resourceService;
  private final LogService logService;

  private final Random rnd = new Random();

  public ActionService(
      MapService mapService, ResourceService resourceService, LogService logService) {
    this.mapService = mapService;
    this.resourceService = resourceService;
    this.logService = logService;
  }

  public List<String> execute(ActionType action, Bot bot, Tile target) {
    if (!isActionRemain(bot)) return List.of("Ход бота окончен");

    List<String> result =
        switch (action) {
          case MOVE -> move(bot, target);
          case COLLECT -> collect(bot);
          case WAIT -> waitAction();
        };

    bot.actionsRemaining = Math.max(bot.actionsRemaining - 1, 0);
    return result;
  }

  private List<String> move(Bot bot, Tile destination) {
    if (destination == null) return List.of();
    List<String> logs = new ArrayList<>();

    bot.currentTile = destination;
    List<String> effects = mapService.exploreTile(destination, bot);
    logs.add(
        "Бот переместился на тайл: "
            + destination.x
            + ":"
            + destination.y
            + " : "
            + destination.type.tileName());

    if (!effects.isEmpty()) {
      for (String e : effects) {
        if (!e.isBlank()) {
          logs.add("Тайл открыт впервые: " + e);
        }
      }
    }

    return logs;
  }

  private List<String> waitAction() {
    return List.of("Бот ждет");
  }

  private List<String> collect(Bot bot) {
    int roll = rnd.nextInt(6) + 1;
    int oldHealth = bot.health;
    int oldPsyche = bot.psyche;
    int oldActions = bot.actionsRemaining;
    Effect effect = bot.currentTile.type.effectForRoll(roll);

    effect.applyTo(bot);
    for (Map.Entry<ResourceType, Integer> entry : effect.resources().entrySet()) {
      resourceService.add(entry.getKey(), entry.getValue());
    }

    return List.of(
        "Бот собрал ресурсы: "
            + ("Бросок d6 = "
                    + roll
                    + ". "
                    + logService.createCollectEffectLog(
                        effect, oldHealth, oldPsyche, oldActions, bot))
                .trim());
  }

  private boolean isActionRemain(Bot bot) {
    return bot.actionsRemaining > 0;
  }
}
