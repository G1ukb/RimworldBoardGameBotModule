package game.strategies;

import game.model.Bot;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.PathfindingService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;

@Service
public class ExplorerStrategy implements BotStrategy {

  private final PathfindingService pathfindingService;

  public ExplorerStrategy(PathfindingService pathfindingService) {
    this.pathfindingService = pathfindingService;
  }

  @Override
  public void executeStep(Bot bot, ActionService actionService) {
    Tile next = pathfindingService.nextStep(bot.currentTile, t -> !t.isExplored);

    if (next != null && next != bot.currentTile) {
      actionService.execute(ActionType.MOVE, bot, next);
    }
    actionService.execute(ActionType.COLLECT, bot, null);
  }

}
