package game.strategies;

import game.model.Bot;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.MapService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Random;

@Service
public class RandomWalkStrategy implements BotStrategy {

  private final Random random = new Random();
  private final MapService mapService;

  public RandomWalkStrategy(MapService mapService) {
    this.mapService = mapService;
  }

  private static final List<Point> DIRECTIONS =
      List.of(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0));

  public void executeStep(Bot bot, ActionService actionService) {
    Point step;
    int newX;
    int newY;
    do {
      step = DIRECTIONS.get(random.nextInt(DIRECTIONS.size()));
      newX = bot.currentTile.x + step.x;
      newY = bot.currentTile.y + step.y;
    } while (!mapService.isWithinBounds(newX, newY));

    newX = bot.currentTile.x + step.x;
    newY = bot.currentTile.y + step.y;
    Tile next = mapService.getTileAt(newX, newY);

    if (next != null) {
      actionService.execute(ActionType.MOVE, bot, next);
      actionService.execute(ActionType.COLLECT, bot, null);
    }
  }
}
