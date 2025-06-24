package game.service;

import game.GameConfig;
import game.model.Bot;
import game.model.ResourceType;
import game.model.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class LogService {
  private static final Logger log = LoggerFactory.getLogger(LogService.class);

  private final MapService mapService;
  private final ResourceService resourceService;
  private final GameConfig config;

  public LogService(MapService mapService, ResourceService resourceService, GameConfig config) {
    this.mapService = mapService;
    this.resourceService = resourceService;
    this.config = config;
  }

  public void printEndTurnInfo(int game, int turn, Map<Bot, List<String>> actions, List<Bot> activePlayers) {
    log.info("--------------------------------");
    log.info("Игра {} ход {}", game, turn);

    for (Bot bot : activePlayers) {
      log.info("Bot: {}", bot.name);
      List<String> acts = actions.get(bot);
      if (acts != null) {
        int idx = 1;
        for (String a : acts) {
          log.info("Действие {}: {}", idx++, a);
        }
      }
    }

    log.info("Карта:\n{}", logAllMapDetails(activePlayers));
    printGroupResources(activePlayers);
    logEndGameProgress();

    log.info("--------------------------------");
  }

  private void printGroupResources(List<Bot> activePlayers) {
    EnumMap<ResourceType, Integer> totals = new EnumMap<>(ResourceType.class);
    for (ResourceType type : ResourceType.values()) {
      totals.put(type, 0);
    }
    for (Bot bot : activePlayers) {
      for (Map.Entry<ResourceType, Integer> entry : bot.resources.entrySet()) {
        totals.put(entry.getKey(), totals.get(entry.getKey()) + entry.getValue());
      }
    }

    log.info("Общие ресурсы группы: {}", resourceService.getAll());
  }

  public void logEndGameProgress() {
    long explored = mapService.tiles.values().stream().filter(t -> t.isExplored).count();
    long total = mapService.tiles.size();
    log.info("Цель: открыть тайлы: открыто {} из {}", explored, total);

    int totalCollected = 0;
    int totalPossible = 0;
    for (ResourceType type : ResourceType.values()) {
      totalCollected += resourceService.get(type);
      totalPossible += type.cap();
    }
    log.info("Цель: ресурсы {} собраны из {}", totalCollected, totalPossible);
  }

  public String logAllMapDetails(List<Bot> bots) {
    if (mapService.tiles.isEmpty()) return "";

    StringBuilder sb = new StringBuilder();
    for (int y = config.maxY(); y >= config.minY(); y--) {
      for (int x = config.minX(); x <= config.maxX(); x++) {
        Tile tile = mapService.tiles.get(new Point(x, y));
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
            sb.append("[ ").append(padContent(mark)).append("]");
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

  private String padContent(String content) {
    if (content.length() >= 5) return content.substring(0, 5);
    int spaces = 5 - content.length();
    int left = spaces / 2;
    int right = spaces - left;
    return " ".repeat(left) + content + " ".repeat(right);
  }
}
