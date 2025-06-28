package game.service;

import game.GameConfig;
import game.model.Bot;
import game.model.tile.Tile;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;

@Service
public class MapService {

  public final Map<Point, Tile> tiles = new HashMap<>();
  private final TileService tileService;
  private final GameConfig config;
  private final LogService logService;

  public MapService(TileService tileService, GameConfig config, @Lazy LogService logService) {
    this.tileService = tileService;
    this.config = config;
    this.logService = logService;
  }

  public void generateStartTile() {
    tiles.putIfAbsent(new Point(0, 0), new Tile(0, 0, tileService.addStarter(), true));
  }

  public Tile getStartTile() {
    return tiles.get(new Point(0, 0));
  }

  public void generateTileAt(int x, int y) {
    tiles.putIfAbsent(new Point(x, y), new Tile(x, y, tileService.rand(), false));
  }

  public Tile getTileAt(int x, int y) {
    if (!isWithinBounds(x, y)) {
      return null;
    }
    return tiles.get(new Point(x, y));
  }

  public boolean isWithinBounds(int x, int y) {
    return x >= config.minX() && x <= config.maxX() && y >= config.minY() && y <= config.maxY();
  }

  public String exploreTile(Tile tile, Bot bot) {
    if (!tile.isExplored) {
      tile.isExplored = true;
      int oldHealth = bot.health;
      int oldPsyche = bot.psyche;

      tile.type.applyDiscoverEffect(bot);
      return logService.createLogDiscoverEffect(oldHealth, oldPsyche, bot);
    }
    return "";
  }
}
