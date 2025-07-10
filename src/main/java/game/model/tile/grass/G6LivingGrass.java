package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G6LivingGrass extends BasicTile {

  /*
  - 6: Живая трава
  	Описание: Трава, покрывающая равнину, реагирует на касания, пытаясь опутать ноги и замедлить шаг.
  	Действие при первом открытии: Игрок теряет следующий ход, если не пройдет проверку
  	Событие: Успех: +1 строительный материал и добавьте хорошее событие под низ колоды; Провал: потеря следующего хода и плохое событие наверх колоды
   */

  public G6LivingGrass() {
    super(
        TileCategory.GRASS,
        " G6 ",
        "Живая трава",
        // TODO implement turn skipping check
        TileEffect.SKIP_TURN,
        Map.of(
            1,
            TileEffect.SKIP_TURN,
            2,
            TileEffect.SKIP_TURN,
            3,
            TileEffect.SKIP_TURN,
            4,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {})),
        List.of(ResourceType.BUILDING_MATERIAL));
  }
}
