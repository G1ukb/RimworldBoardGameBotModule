package game.service;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActionService {

  private static final Logger log = LoggerFactory.getLogger(ActionService.class);

  private final MapService mapService;
  private final ResourceService resourceService;

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
    log.info(result);
    return result;
  }

  private String move(Bot bot, Tile destination) {
    if (destination == null) return "";

    bot.currentTile = destination;
    mapService.exploreTile(destination);
    return "Бот переместился на тайл: " + destination.x + ":" + destination.y;
  }

  private String waitAction() {
    return "Бот ждет";
  }

  private String collect(Bot bot) {
    Map<ResourceType, Integer> tileResources = bot.currentTile.type.resources();
    StringBuilder log = new StringBuilder();

    for (Map.Entry<ResourceType, Integer> entry : tileResources.entrySet()) {
      ResourceType type = entry.getKey();
      int gained = entry.getValue();
      resourceService.add(type, gained);
      log.append(type.name()).append("=").append(gained).append(" ");
    }

    return "Бот собрал ресурсы: " + log.toString().trim();
  }

  private boolean isActionRemain(Bot bot) {
    return bot.actionsRemaining > 0;
  }
}
