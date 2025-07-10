package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G8SaltFlats extends BasicTile {

  /*
    - 8: Солончаковые поля
    Описание: Потрескавшаяся белая земля, покрытая слоем кристаллизованных минеральных отложений.
    Действие при первом открытии: Игрок получает +1 строительный материал, но теряет 1 провиант
    Событие: Успех: +1 медикаменты; Провал: -1 здоровье, добавьте плохое событие наверх колоды
  */

  public G8SaltFlats() {
    super(
        TileCategory.GRASS,
        " G8 ",
        "Солончаковые поля",
        new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1, ResourceType.FOOD, -1), b -> {}),
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            2,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            4,
            new TileEffect(Map.of(ResourceType.MEDICINE, 1), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.MEDICINE, 1), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.MEDICINE, 1), b -> {})),
        List.of(ResourceType.MEDICINE));
  }
}
