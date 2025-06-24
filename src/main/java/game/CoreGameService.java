package game;

import game.model.Bot;
import game.service.*;
import game.strategies.BotStrategy;
import game.strategies.ExplorerStrategy;
import game.strategies.FarmerStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CoreGameService {

  private final MapService mapService;
  private final BotService botService;
  private final GameConfig gameConfig;
  private final GoalService goalService;
  private final LogService logService;

  @Autowired ExplorerStrategy explorerStrategy;
  @Autowired FarmerStrategy farmerStrategy;

  List<Bot> activePlayers = new ArrayList<>();

  public CoreGameService(
      MapService mapService,
      BotService botService,
      GameConfig gameConfig,
      GoalService goalService,
      LogService logService) {
    this.botService = botService;
    this.mapService = mapService;
    this.gameConfig = gameConfig;
    this.goalService = goalService;
    this.logService = logService;
  }

  public void generateStart() {
    generateStartMap();
    activePlayers.add(generatePlayer(explorerStrategy, "explorer", "*E*"));
    activePlayers.add(generatePlayer(farmerStrategy, "farmer", "*F*"));
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


  public void startGame() {
    int turn = 1; int game = 1;

    while (!goalService.isAllGoalsCompleted()) {
      Map<Bot, List<String>> actions = new LinkedHashMap<>();

      for (Bot bot : activePlayers) {
        botService.resetActions(bot);
        List<String> botActions = new ArrayList<>();
        while (bot.actionsRemaining > 0) {
          botActions.addAll(botService.takeAction(bot));
        }
        actions.put(bot, botActions);
      }

      logService.printEndTurnInfo(game, turn++, actions, activePlayers);
    }
  }

}
