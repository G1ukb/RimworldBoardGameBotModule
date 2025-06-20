package game.service;

import game.GameConfig;
import game.model.Bot;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;

@Service
public class MapService {

  public final Map<Point, Tile> tiles = new HashMap<>();
  private final TileService tileService;
  private final GameConfig config;

  public MapService(TileService tileService, GameConfig config) {
    this.tileService = tileService;
    this.config = config;
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

  public void exploreTile(Tile tile) {
    if (!tile.isExplored) tile.isExplored = true;
  }

  public void print(Bot bot) {
    if (tiles.isEmpty()) return;

    for (int y = config.maxY(); y >= config.minY(); y--) {
      for (int x = config.minX(); x <= config.maxX(); x++) {

        Tile tile = tiles.get(new Point(x, y));

        if ((tile != null && bot != null) && tile.equals(bot.currentTile)) {
          System.out.print("[  B  ]");
        } else if (tile != null) {
          if (tile.isExplored) System.out.print("[|" + tile.type.symbol() + "|]");
          else System.out.print("[ " + tile.type.symbol() + " ]");
        }
      }
      System.out.println();
    }
  }
}
