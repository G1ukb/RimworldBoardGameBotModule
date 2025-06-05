package game;

import game.model.Bot;
import game.service.BotService;
import game.model.Tile;
import game.service.TileService;
import game.service.MapService;
import game.strategies.RandomWalkStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoreGameService {

  private MapService mapService;
  private TileService tileService;
  private BotService botService;

  @Autowired
  RandomWalkStrategy randomWalkStrategy;

  List<Bot> activePlayers = new ArrayList<>();

  public CoreGameService(MapService mapService, TileService tileService, BotService botService) {
    this.botService = botService;
    this.tileService = tileService;
    this.mapService = mapService;
  }

  public void generateStart() {
    generateStartMap();
    activePlayers.add(generatePlayer());
  }

  private void generateStartMap() {
    Tile startTile = new Tile(0, 0, tileService.addStarter());
    mapService.add(startTile);
    mapService.setStartTile(startTile);

    mapService.add(new Tile(0, 1, tileService.rand()));
    mapService.add(new Tile(1, 0, tileService.rand()));
    mapService.add(new Tile(0, -1, tileService.rand()));
    mapService.add(new Tile(-1, 0, tileService.rand()));
  }

  private Bot generatePlayer() {
    Bot startingBot = new Bot(randomWalkStrategy, mapService.getStartTile(), 5);
    return startingBot;
  }

  public void printLogInfo() {
    mapService.print();
  }

  public void startGame() {
    activePlayers.forEach(bot -> {
      while (!bot.isDone) {
        botService.takeTurn(bot);
        printLogInfo();
      }
    });
  }

}
