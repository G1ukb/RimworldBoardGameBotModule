package game.strategies;

import game.model.Bot;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.MapService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class ExplorerStrategy implements BotStrategy {

  private final MapService mapService;

  private static final List<Point> DIRECTIONS =
      List.of(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0));

  public ExplorerStrategy(MapService mapService) {
    this.mapService = mapService;
  }

  @Override
  public void executeStep(Bot bot, ActionService actionService) {
    Tile next = nextStepToUnexplored(bot.currentTile);

    if (next != null && next != bot.currentTile) {
      actionService.execute(ActionType.MOVE, bot, next);
    }
    actionService.execute(ActionType.COLLECT, bot, null);
  }

  private Tile nextStepToUnexplored(Tile start) {
    if (!start.isExplored) return start;

    Map<Tile, Tile> parent = new HashMap<>();
    Queue<Tile> queue = new ArrayDeque<>();
    Set<Point> visited = new HashSet<>();

    queue.add(start);
    parent.put(start, null);
    visited.add(new Point(start.x, start.y));

    while (!queue.isEmpty()) {
      Tile current = queue.poll();
      if (!current.isExplored) {
        Tile step = current;
        while (parent.get(step) != null && parent.get(step) != start) {
          step = parent.get(step);
        }
        return step;
      }

      for (Point dir : DIRECTIONS) {
        int nx = current.x + dir.x;
        int ny = current.y + dir.y;
        if (!mapService.isWithinBounds(nx, ny)) continue;
        Tile neighbor = mapService.getTileAt(nx, ny);
        Point p = new Point(nx, ny);
        if (neighbor != null && !visited.contains(p)) {
          visited.add(p);
          parent.put(neighbor, current);
          queue.add(neighbor);
        }
      }
    }
    return start;
  }
}
