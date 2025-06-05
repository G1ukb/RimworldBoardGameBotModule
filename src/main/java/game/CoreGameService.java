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

  @Autowired RandomWalkStrategy randomWalkStrategy;

  List<Bot> activePlayers = new ArrayList<>();

  public CoreGameService(MapService mapService, BotService botService) {
    this.botService = botService;
    this.mapService = mapService;
  }

  public void generateStart() {
    generateStartMap();
    activePlayers.add(generatePlayer());
  }

  private void generateStartMap() {
    for (int x = -2; x <= 2; x++) {
      for (int y = -2; y <= 2; y++) {
        if (x == 0 && y == 0) {
          mapService.generateStartTile();
        }
        mapService.generateTileAt(x, y);
      }
    }
  }

  private Bot generatePlayer() {
    return new Bot(randomWalkStrategy, mapService.getStartTile(), 5, 4, 4);
  }

  public void printLogInfo(Bot bot) {
    mapService.print(bot);
  }

  public void startGame() {
    activePlayers.forEach(
        bot -> {
          while (!bot.isDone) {
            botService.takeTurn(bot);
            printLogInfo(bot);
          }
        });
  }
}
