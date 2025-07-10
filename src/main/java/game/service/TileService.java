package game.service;

import game.model.tile.BasicTile;
import game.model.tile.desert.CommonDesertTile;
import game.model.tile.forest.CommonForestTile;
import game.model.tile.grass.*;
import game.model.tile.lake.CommonLakeTile;
import game.model.tile.mountain.CommonMountainTile;
import game.model.tile.start.CommonStartTile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TileService {

  private final List<BasicTile> randomTiles;
  private final Random rnd = new Random();

  public TileService() {
    randomTiles =
        List.of(
            //            new G1FieldOfSilence(),
            //            new G2SunnyPlains(),
            //            new G3WanderingStones(),
            //            new G4HoneyFields(),
            //            new G5DreamSteppe(),
            //            new G6LivingGrass(),
            //            new G7RainPlain(),
            //            new G8SaltFlats(),
            //            new G9GrassWhirls(),
            new G10BarrenPlain(),
            new CommonForestTile(),
            new CommonDesertTile(),
            new CommonLakeTile(),
            new CommonMountainTile());
  }

  public BasicTile addStarter() {
    return new CommonStartTile();
  }

  public BasicTile rand() {
    return randomTiles.get(rnd.nextInt(randomTiles.size()));
  }
}
