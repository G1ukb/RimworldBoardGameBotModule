package game.model.tile.start;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.TileCategory;
import game.model.tile.BasicTile;

import java.util.List;
import java.util.Map;

public class CommonStartTile extends BasicTile {

  public CommonStartTile() {
    super(
        TileCategory.START,
        " (s)",
        "Стартовая позиция",
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
            new TileEffect(Map.of(ResourceType.FOOD, 2), b -> {})),
        List.of(ResourceType.FOOD));
  }
}
