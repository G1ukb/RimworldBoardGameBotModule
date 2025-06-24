package game.service;

import game.GameConfig;
import game.model.Bot;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

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

  private String padContent(String content) {
    if (content.length() >= 5) return content.substring(0, 5);
    int spaces = 5 - content.length();
    int left = spaces / 2;
    int right = spaces - left;
    return " ".repeat(left) + content + " ".repeat(right);
  }

  public String render(List<Bot> bots) {
    if (tiles.isEmpty()) return "";

    StringBuilder sb = new StringBuilder();
    for (int y = config.maxY(); y >= config.minY(); y--) {
      for (int x = config.minX(); x <= config.maxX(); x++) {
        Tile tile = tiles.get(new Point(x, y));
        List<Bot> here = new ArrayList<>();
        if (bots != null) {
          for (Bot b : bots) {
            if (b.currentTile.x == x && b.currentTile.y == y) {
              here.add(b);
            }
          }
        }

        if (tile != null) {
          if (!here.isEmpty()) {
            String mark = here.size() == 1 ? here.getFirst().initial : here.size() + "B";
            sb.append("[").append(padContent(mark)).append("]");
          } else if (tile.isExplored) {
            sb.append("[|").append(tile.type.symbol()).append("|]");
          } else {
            sb.append("[ ").append(tile.type.symbol()).append(" ]");
          }
        }
      }
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }
}
