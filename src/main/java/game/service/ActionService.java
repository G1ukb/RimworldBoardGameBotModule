package game.service;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActionService {
  private final MapService mapService;

  public ActionService(MapService mapService) {
    this.mapService = mapService;
  }

  public void execute(ActionType action, Bot bot, Tile target) {
    if (!isActionRemain(bot)) return;

    switch (action) {
      case MOVE -> move(bot, target);
      case COLLECT -> collect(bot);
    }

    bot.actionsRemaining--;
  }

  private void move(Bot bot, Tile destination) {
    if (destination == null) return;

    bot.currentTile = destination;
    System.out.println("Бот переместился на тайл: " + destination.x + ":" + destination.y);
    mapService.exploreTile(destination);
  }

  private void collect(Bot bot) {
    Map<ResourceType, Integer> tileResources = bot.currentTile.type.resources();
    StringBuilder log = new StringBuilder();

    for (Map.Entry<ResourceType, Integer> entry : tileResources.entrySet()) {
      ResourceType type = entry.getKey();
      int gained = entry.getValue();
      int current = bot.resources.getOrDefault(type, 0);
      int newAmount = Math.min(current + gained, type.cap());
      bot.resources.put(type, newAmount);
      log.append(type.name()).append("=").append(gained).append(" ");
    }

    System.out.println("Бот собрал ресурсы: " + log.toString().trim());
  }

  private boolean isActionRemain(Bot bot) {
    if (bot.actionsRemaining == 0) {
      System.out.println("Ход бота окончен");
      return false;
    }

    return true;
  }
}
