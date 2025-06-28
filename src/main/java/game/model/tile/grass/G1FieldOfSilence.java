package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.Map;

public class G1FieldOfSilence extends BasicTile {

  public G1FieldOfSilence() {
    super(
        TileCategory.GRASS,
        " G1 ",
        "Поле тишины",
        new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
        Map.of(
            1,
            TileEffect.NONE,
            2,
            TileEffect.NONE,
            3,
            TileEffect.NONE,
            4,
            new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.FOOD, 2), b -> {})));
  }
}
