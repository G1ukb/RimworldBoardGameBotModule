package game.strategies;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.PathfindingService;
import game.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FarmerStrategy implements BotStrategy {

  private final PathfindingService pathfindingService;
  private final ResourceService resourceService;

  public FarmerStrategy(PathfindingService pathfindingService, ResourceService resourceService) {
    this.pathfindingService = pathfindingService;
    this.resourceService = resourceService;
  }

  @Override
  public List<String> executeStep(Bot bot, ActionService actionService) {
    List<String> logs = new ArrayList<>();
    ResourceType need = findNeededResource();
    if (need == null) {
      logs.addAll(actionService.execute(ActionType.WAIT, bot, null));
      return logs;
    }

    if (bot.currentTile.type.hasResource(need)) {
      logs.addAll(actionService.execute(ActionType.COLLECT, bot, null));
    } else {
      Tile next = pathfindingService.nextStep(bot.currentTile, t -> t.type.hasResource(need));
      if (next != null && next != bot.currentTile) {
        logs.addAll(actionService.execute(ActionType.MOVE, bot, next));
      } else {
        logs.addAll(actionService.execute(ActionType.COLLECT, bot, null));
      }
    }
    return logs;
  }

  private ResourceType findNeededResource() {
    for (ResourceType type : ResourceType.values()) {
      if (resourceService.get(type) < type.cap()) return type;
    }
    return null;
  }
}
