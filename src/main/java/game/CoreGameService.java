package game;

import game.model.Bot;
import game.service.BotService;
import game.service.MapService;
import game.strategies.RandomWalkStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    System.out.println("Ход: " + turn);
    mapService.print(bot);
  }

  public void startGame() {
    for (int turn = 1; turn <= gameConfig.maxTurns(); turn++) {
      for (Bot bot : activePlayers) {
        botService.resetActions(bot);
        while (bot.actionsRemaining > 0) {
          botService.takeAction(bot);
          printLogInfo(bot, turn);
        }
      }
    }
  }
}
