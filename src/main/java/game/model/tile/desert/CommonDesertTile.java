package game.model.tile.desert;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class CommonDesertTile extends BasicTile {

  public CommonDesertTile() {
    super(
        TileCategory.DESERT,
        " D1 ",
        "Обычная Пустыня",
        TileEffect.NONE,
        Map.of(
            1,
            TileEffect.NONE,
            2,
            TileEffect.NONE,
            3,
            TileEffect.NONE,
            4,
            new TileEffect(Map.of(ResourceType.SPECIAL, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.SPECIAL, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.SPECIAL, 2), b -> {})),
        List.of(ResourceType.SPECIAL));
  }
}
