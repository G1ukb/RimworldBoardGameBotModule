package game.model.tile.lake;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class CommonLakeTile extends BasicTile {

  public CommonLakeTile() {
    super(
        TileCategory.WATER,
        " W1 ",
        "Обычное Озеро",
        TileEffect.NONE,
        Map.of(
            1,
            TileEffect.NONE,
            2,
            TileEffect.NONE,
            3,
            TileEffect.NONE,
            4,
            new TileEffect(Map.of(ResourceType.MEDICINE, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.MEDICINE, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.MEDICINE, 2), b -> {})),
      List.of(ResourceType.MEDICINE));
  }
}
