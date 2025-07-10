package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G4HoneyFields extends BasicTile {

  /*
    - 4: Медовые поля
    Описание: Поверхность земли покрыта густой золотистой жидкостью, источающей сладкий аромат, притягивающий странных существ.
    Действие при первом открытии: Игрок сразу получает +1 провиант, но добавляет плохое событие наверх колоды
    Событие: Успех: избежание встречи с существами; Провал: бой с существами, потеря 1 здоровья
   */

  public G4HoneyFields() {
    super(
        TileCategory.GRASS,
        " G4 ",
        "Медовые поля",
        new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {}),
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 2, 0)),
            2,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            4,
            TileEffect.NONE,
            5,
            new TileEffect(
                Map.of(ResourceType.FOOD, 1),
                b -> {
                  b.health = Math.min(b.health + 1, b.healthCap);
                  b.psyche = Math.min(b.psyche + 1, b.psycheCap);
                }),
            6,
            new TileEffect(
                Map.of(ResourceType.FOOD, 2),
                b -> {
                  b.health = Math.min(b.health + 2, b.healthCap);
                  b.psyche = Math.min(b.psyche + 2, b.psycheCap);
                })),
        List.of(ResourceType.FOOD));
  }
}
