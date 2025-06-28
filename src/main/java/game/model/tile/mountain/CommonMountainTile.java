package game.model.tile.mountain;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.Map;

public class CommonMountainTile extends BasicTile {

  public CommonMountainTile() {
    super(
        TileCategory.MOUNTAIN,
        " M1 ",
        "Обычная Горная Местность",
        TileEffect.NONE,
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
