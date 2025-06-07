package game.strategies;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.PathfindingService;
import game.service.ResourceService;
import org.springframework.stereotype.Service;

@Service
public class FarmerStrategy implements BotStrategy {

  private final PathfindingService pathfindingService;
  private final ResourceService resourceService;

  public FarmerStrategy(PathfindingService pathfindingService, ResourceService resourceService) {
    this.pathfindingService = pathfindingService;
    this.resourceService = resourceService;
  }

  @Override
  public void executeStep(Bot bot, ActionService actionService) {
    ResourceType need = findNeededResource();
    if (need == null) {
      actionService.execute(ActionType.WAIT, bot, null);
      return;
    }

    if (bot.currentTile.type.resources().containsKey(need)) {
      actionService.execute(ActionType.COLLECT, bot, null);
    } else {
      Tile next = pathfindingService.nextStep(bot.currentTile, t -> t.type.resources().containsKey(need));
      if (next != null && next != bot.currentTile) {
        actionService.execute(ActionType.MOVE, bot, next);
      } else {
        actionService.execute(ActionType.COLLECT, bot, null);
      }
    }
  }

  private ResourceType findNeededResource() {
    for (ResourceType type : ResourceType.values()) {
      if (resourceService.get(type) < type.cap()) return type;
    }
    return null;
  }

}
