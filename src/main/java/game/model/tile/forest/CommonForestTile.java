package game.model.tile.forest;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class CommonForestTile extends BasicTile {

  public CommonForestTile() {
    super(
        TileCategory.FOREST,
        " F1 ",
        "Обычный Лес",
        TileEffect.NONE,
        Map.of(
            1,
            TileEffect.NONE,
            2,
            TileEffect.NONE,
            3,
            TileEffect.NONE,
            4,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 2), b -> {})),
        List.of(ResourceType.BUILDING_MATERIAL));
  }
}
