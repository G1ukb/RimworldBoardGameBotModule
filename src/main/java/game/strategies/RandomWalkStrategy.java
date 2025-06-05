package game.strategies;

import game.model.Bot;
import game.model.Tile;
import game.service.MapService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomWalkStrategy implements BotStrategy {

    private final Random random = new Random();
    private final MapService mapService;

    public RandomWalkStrategy(MapService mapService) {
        this.mapService = mapService;
    }

    public void executeStep(Bot bot) {
        List<Tile> neighbors = mapService.getAdjacentTiles(bot.currentTile);

        if (!neighbors.isEmpty()) {
            Tile next = neighbors.get(random.nextInt(neighbors.size()));
            bot.currentTile = next;
            bot.actionsRemaining--;

            System.out.println("Бот переместился на тайл: " + next.x() + ":" + next.y());
            mapService.generateSurroundingTiles(next);
        }
    }

}
