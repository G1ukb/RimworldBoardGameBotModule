package game;

import game.model.Bot;
import game.model.ResourceType;
import game.service.BotService;
import game.service.GoalService;
import game.service.MapService;
import game.service.ResourceService;
import game.strategies.BotStrategy;
import game.strategies.ExplorerStrategy;
import game.strategies.FarmerStrategy;
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
  private final ResourceService resourceService;
  private final GoalService goalService;

  @Autowired ExplorerStrategy explorerStrategy;
  @Autowired FarmerStrategy farmerStrategy;

  List<Bot> activePlayers = new ArrayList<>();

  public CoreGameService(
      MapService mapService,
      BotService botService,
      GameConfig gameConfig,
      ResourceService resourceService,
      GoalService goalService) {
    this.botService = botService;
    this.mapService = mapService;
    this.gameConfig = gameConfig;
    this.resourceService = resourceService;
    this.goalService = goalService;
  }

  public void generateStart() {
    generateStartMap();
    activePlayers.add(generatePlayer(explorerStrategy, "explorer"));
    activePlayers.add(generatePlayer(farmerStrategy, "farmer"));
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

  private Bot generatePlayer(BotStrategy botStrategy, String botName) {
    return new Bot(
        botStrategy,
        mapService.getStartTile(),
        gameConfig.botMaxActions(),
        gameConfig.botStartHp(),
        gameConfig.botStartMood(),
        botName);
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
    goalService.logProgress();

    System.out.println("--------------------------------");
  }

  public void startGame() {
    int turn = 1;
    while (!goalService.isAllGoalsCompleted()) {
      System.out.println("Начало Хода: " + turn);

      for (Bot bot : activePlayers) {
        botService.resetActions(bot);
        while (bot.actionsRemaining > 0) {
          System.out.println("BOT: " + bot.name);
          botService.takeAction(bot);
          printLogInfo(bot, turn);
        }
      }

      printEndTurnInfo(turn);
      turn++;
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

    System.out.println("Общие ресурсы группы: " + resourceService.getAll());
  }
}
