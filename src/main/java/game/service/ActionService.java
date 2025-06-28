package game.service;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

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

  public String execute(ActionType action, Bot bot, Tile target) {
    if (!isActionRemain(bot)) return "Ход бота окончен";

    String result =
        switch (action) {
          case MOVE -> move(bot, target);
          case COLLECT -> collect(bot);
          case WAIT -> waitAction();
        };

    bot.actionsRemaining--;
    return result;
  }

  private String move(Bot bot, Tile destination) {
    if (destination == null) return "";

    bot.currentTile = destination;
    mapService.exploreTile(destination, bot);
    return "Бот переместился на тайл: " + destination.x + ":" + destination.y;
  }

  private String waitAction() {
    return "Бот ждет";
  }

  private String collect(Bot bot) {
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

    return "Бот собрал ресурсы: " + log.toString().trim();
  }

  private boolean isActionRemain(Bot bot) {
    return bot.actionsRemaining > 0;
  }
}
