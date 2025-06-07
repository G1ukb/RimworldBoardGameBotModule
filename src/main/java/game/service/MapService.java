package game.service;

import game.model.Bot;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;

@Service
public class MapService {

  public final Map<Point, Tile> tiles = new HashMap<>();
  private final TileService tileService;

  public int minX = -2;
  public int minY = -2;
  public int maxX = 2;
  public int maxY = 2;

  public MapService(TileService tileService) {
    this.tileService = tileService;
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
    return x >= minX && x <= maxX && y >= minY && y <= maxY;
  }

  public void exploreTile(Tile tile) {
    if (!tile.isExplored) tile.isExplored = true;
  }

  public void print(Bot bot) {
    if (tiles.isEmpty()) return;

    for (int y = maxY; y >= minY; y--) {
      for (int x = minX; x <= maxX; x++) {

        Tile tile = tiles.get(new Point(x, y));

        if (tile != null && tile.equals(bot.currentTile)) {
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
