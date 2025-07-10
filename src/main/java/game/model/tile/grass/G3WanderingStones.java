package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.HighestResourceChoiceEffect;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G3WanderingStones extends BasicTile {

  /*
  - 3: Пустошь блуждающих камней
  Описание: Камни медленно перемещаются по равнине, оставляя следы неизвестных знаков на земле.
  Действие при первом открытии: Игрок немедленно открывает карту врагов и размещает её на поле
  Событие: Успех: +1 строительный материал и уберите верхнее плохое событие из колоды; Провал: потеря 1 ресурса
   */

  public G3WanderingStones() {
    super(
        TileCategory.GRASS,
        " G3 ",
        "Пустошь блуждающих камней",
        // TODO
        TileEffect.NONE,
        Map.of(
            1,
            new HighestResourceChoiceEffect(-1),
            2,
            new HighestResourceChoiceEffect(-1),
            3,
            new HighestResourceChoiceEffect(-1),
            4,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {})),
        List.of(ResourceType.BUILDING_MATERIAL));
  }
}
