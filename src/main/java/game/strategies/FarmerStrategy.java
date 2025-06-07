package game.strategies;

import game.model.Bot;
import game.model.ResourceType;
import game.model.action.ActionType;
import game.model.tile.Tile;
import game.service.ActionService;
import game.service.MapService;
import game.service.ResourceService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

@Service
public class FarmerStrategy implements BotStrategy {

  private final MapService mapService;
  private final ResourceService resourceService;

  private static final List<Point> DIRECTIONS =
      List.of(new Point(0, 1), new Point(1, 0), new Point(0, -1), new Point(-1, 0));

  public FarmerStrategy(MapService mapService, ResourceService resourceService) {
    this.mapService = mapService;
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
      Tile next = nextStepToResource(bot.currentTile, need);
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

  private Tile nextStepToResource(Tile start, ResourceType type) {
    Map<Tile, Tile> parent = new HashMap<>();
    Queue<Tile> queue = new ArrayDeque<>();
    Set<Point> visited = new HashSet<>();

    queue.add(start);
    parent.put(start, null);
    visited.add(new Point(start.x, start.y));

    while (!queue.isEmpty()) {
      Tile current = queue.poll();
      if (current.type.resources().containsKey(type)) {
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
