package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.DiceRollEffect;
import game.model.effect.TileEffect;
import game.model.effect.HighestResourceChoiceEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G9GrassWhirls extends BasicTile {
  /*
    - 9: Травяные вихри
    Описание: Странные воронки из травы и пыльцы движутся по полю, сбивая ориентиры.
    Действие при первом открытии: Игрок бросает кубик (1d6): 1–3 — потеря хода, 4–6 — ничего
    Событие: Успех: +1 провиант, добавьте хорошее событие под низ колоды; Провал: потеря 1 ресурса
  */

  public G9GrassWhirls() {
    super(
        TileCategory.GRASS,
        " G9 ",
        "Травяные вихри",
        new DiceRollEffect(
            Map.of(
                1, TileEffect.SKIP_TURN,
                2, TileEffect.SKIP_TURN,
                3, TileEffect.SKIP_TURN,
                4, TileEffect.NONE,
                5, TileEffect.NONE,
                6, TileEffect.NONE)),
        Map.of(
            1, new HighestResourceChoiceEffect(-1),
            2, new HighestResourceChoiceEffect(-1),
            3, new HighestResourceChoiceEffect(-1),
            4, new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {}),
            5, new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {}),
            6, new TileEffect(Map.of(ResourceType.FOOD, 1), b -> {})),
        List.of(ResourceType.FOOD));
  }
}
