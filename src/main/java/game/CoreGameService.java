package game;

import game.model.Bot;
import game.model.ResourceType;
import game.service.BotService;
import game.service.MapService;
import game.strategies.RandomWalkStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class CoreGameService {

  private final MapService mapService;
  private final BotService botService;
  private final GameConfig gameConfig;

  @Autowired RandomWalkStrategy randomWalkStrategy;

  List<Bot> activePlayers = new ArrayList<>();

  public CoreGameService(MapService mapService, BotService botService, GameConfig gameConfig) {
    this.botService = botService;
    this.mapService = mapService;
    this.gameConfig = gameConfig;
  }

  public void generateStart() {
    generateStartMap();
    activePlayers.add(generatePlayer());
  }

  private void generateStartMap() {
    for (int x = gameConfig.minX(); x <= gameConfig.maxX(); x++) {
      for (int y = gameConfig.minY(); y <= gameConfig.maxY(); y++) {
        if (x == 0 && y == 0) {
          mapService.generateStartTile();
        }
        mapService.generateTileAt(x, y);
      }
    }
  }

  private Bot generatePlayer() {
    return new Bot(
        randomWalkStrategy,
        mapService.getStartTile(),
        gameConfig.botMaxActions(),
        gameConfig.botStartHp(),
        gameConfig.botStartMood());
  }

  public void printLogInfo(Bot bot, int turn) {
    mapService.print(bot);
    System.out.println("У бота осталось действий: " + bot.actionsRemaining);
    System.out.println("--------------------------------");
  }

  public void printEndTurnInfo(int turn) {
    System.out.println("--------------------------------");

    System.out.println("Конец Хода: " + turn);
    mapService.print(null);
    printGroupResources();

    System.out.println("--------------------------------");
  }

  public void startGame() {
    for (int turn = 1; turn <= gameConfig.maxTurns(); turn++) {
      System.out.println("Начало Хода: " + turn);

      for (Bot bot : activePlayers) {
        botService.resetActions(bot);
        while (bot.actionsRemaining > 0) {
          botService.takeAction(bot);
          printLogInfo(bot, turn);
        }
      }

      printEndTurnInfo(turn);
    }
  }

  private void printGroupResources() {
    EnumMap<ResourceType, Integer> totals = new EnumMap<>(ResourceType.class);
    for (ResourceType type : ResourceType.values()) {
      totals.put(type, 0);
    }
    for (Bot bot : activePlayers) {
      for (Map.Entry<ResourceType, Integer> entry : bot.resources.entrySet()) {
        totals.put(entry.getKey(), totals.get(entry.getKey()) + entry.getValue());
      }
    }

    System.out.println("Общие ресурсы группы: " + totals);
  }
}
