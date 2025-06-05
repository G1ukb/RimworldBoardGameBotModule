package game.strategies;

import game.model.Bot;
import game.model.Tile;
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

  public void executeStep(Bot bot) {
    Point step = DIRECTIONS.get(random.nextInt(DIRECTIONS.size()));
    int newX = bot.currentTile.x + step.x;
    int newY = bot.currentTile.y + step.y;

    Tile next = mapService.getTileAt(newX, newY);
    bot.currentTile = next;

    System.out.println("Бот переместился на тайл: " + next.x + ":" + next.y);
    mapService.exploreTile(next);
  }
}
