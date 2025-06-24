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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoreGameService {

  private static final Logger log = LoggerFactory.getLogger(CoreGameService.class);

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
    activePlayers.add(generatePlayer(explorerStrategy, "explorer", "E"));
    activePlayers.add(generatePlayer(farmerStrategy, "farmer", "F"));
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

  private Bot generatePlayer(BotStrategy botStrategy, String botName, String botInitial) {
    return new Bot(
        botStrategy,
        mapService.getStartTile(),
        gameConfig.botMaxActions(),
        gameConfig.botStartHp(),
        gameConfig.botStartMood(),
        botName,
        botInitial);
  }

  public void printEndTurnInfo(int turn, Map<Bot, List<String>> actions) {
    log.info("--------------------------------");
    log.info("Конец Хода: {}", turn);

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

    log.info("Карта:\n{}", mapService.render(activePlayers));
    printGroupResources();
    goalService.logProgress();

    log.info("--------------------------------");
  }

  public void startGame() {
    int turn = 1;
    while (!goalService.isAllGoalsCompleted()) {
      log.info("Начало Хода: {}", turn);
      Map<Bot, List<String>> actions = new LinkedHashMap<>();

      for (Bot bot : activePlayers) {
        botService.resetActions(bot);
        List<String> botActions = new ArrayList<>();
        while (bot.actionsRemaining > 0) {
          botActions.addAll(botService.takeAction(bot));
        }
        actions.put(bot, botActions);
      }

      printEndTurnInfo(turn, actions);
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

    log.info("Общие ресурсы группы: {}", resourceService.getAll());
  }
}
