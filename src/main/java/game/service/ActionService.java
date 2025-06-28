package game.service;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
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
  private final Random rnd = new Random();

  public ActionService(MapService mapService, ResourceService resourceService) {
    this.mapService = mapService;
    this.resourceService = resourceService;
  }

  public List<String> execute(ActionType action, Bot bot, Tile target) {
    if (!isActionRemain(bot)) return List.of("Ход бота окончен");

    List<String> result =
        switch (action) {
          case MOVE -> move(bot, target);
          case COLLECT -> collect(bot);
          case WAIT -> waitAction();
        };

    bot.actionsRemaining--;
    return result;
  }

  private List<String> move(Bot bot, Tile destination) {
    if (destination == null) return List.of();
    List<String> logs = new ArrayList<>();

    bot.currentTile = destination;
    String effect = mapService.exploreTile(destination, bot);
    logs.add("Бот переместился на тайл: " + destination.x + ":" + destination.y + " :" + destination.type.tileName());

    if (!effect.isBlank()) {
      logs.add("Эффект открытия тайла: " + effect);
    }

    return logs;
  }

  private List<String> waitAction() {
    return List.of("Бот ждет");
  }

  private List<String> collect(Bot bot) {
    int roll = rnd.nextInt(6) + 1;
    Map<ResourceType, Integer> tileResources = bot.currentTile.type.resourcesForRoll(roll);
    StringBuilder log = new StringBuilder("Бросок d6 = " + roll + ". ");

    if (tileResources.isEmpty()) {
      log.append("Ничего не найдено");
    } else {
      for (Map.Entry<ResourceType, Integer> entry : tileResources.entrySet()) {
        ResourceType type = entry.getKey();
        int gained = entry.getValue();
        resourceService.add(type, gained);
        log.append(type.name()).append("=").append(gained).append(" ");
      }
    }

    return List.of("Бот собрал ресурсы: " + log.toString().trim());
  }

  private boolean isActionRemain(Bot bot) {
    return bot.actionsRemaining > 0;
  }
}
