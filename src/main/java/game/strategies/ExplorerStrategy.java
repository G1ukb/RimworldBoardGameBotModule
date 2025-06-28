package game.strategies;

import game.model.Bot;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.PathfindingService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

@Service
public class ExplorerStrategy implements BotStrategy {

  private final PathfindingService pathfindingService;

  public ExplorerStrategy(PathfindingService pathfindingService) {
    this.pathfindingService = pathfindingService;
  }

  @Override
  public List<String> executeStep(Bot bot, ActionService actionService) {
    List<String> logs = new ArrayList<>();
    Tile next = pathfindingService.nextStep(bot.currentTile, t -> !t.isExplored);

    if (next != null && next != bot.currentTile) {
      logs.addAll(actionService.execute(ActionType.MOVE, bot, next));
    }
    logs.addAll(actionService.execute(ActionType.COLLECT, bot, null));
    return logs;
  }
}
