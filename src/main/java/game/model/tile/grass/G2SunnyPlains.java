package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.ChoiceEffect;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G2SunnyPlains extends BasicTile {

  /*
  - 2: Солнечные равнины
  Описание: Яркий свет здесь настолько интенсивен, что местность кажется белой и лишённой теней.
  Действие при первом открытии: Игрок теряет 1 здоровье или 1 провиант (выбор игроков)
  Событие: Успех: +2 провианта и добавьте хорошее событие под низ колоды; Провал: потеря 1 здоровья и плохое событие наверх колоды
   */

  public G2SunnyPlains() {
    super(
        TileCategory.GRASS,
        " G2 ",
        "Солнечные равнины",
        new ChoiceEffect(
            List.of(
                new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
                new TileEffect(Map.of(ResourceType.FOOD, -1), b -> {}))),
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            2,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            4,
            new TileEffect(Map.of(ResourceType.FOOD, 2), b -> {}),
            5,
            new TileEffect(Map.of(ResourceType.FOOD, 2), b -> {}),
            6,
            new TileEffect(Map.of(ResourceType.FOOD, 2), b -> {})));
  }
}
