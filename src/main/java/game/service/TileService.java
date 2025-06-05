package game.service;

import game.model.tile.TileType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class TileService {

  private final List<TileType> randomTiles;
  private final Random rnd = new Random();

  public TileService() {
    randomTiles = Arrays.stream(TileType.values())
                      .filter(t -> t != TileType.START)
                      .toList();
  }

  public TileType addStarter() {
    return TileType.START;
  }

  public TileType rand() {
    return randomTiles.get(rnd.nextInt(randomTiles.size()));
  }
}
