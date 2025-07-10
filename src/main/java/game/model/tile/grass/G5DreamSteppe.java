package game.model.tile.grass;

import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G5DreamSteppe extends BasicTile {

  /*
    - 5: Степь снов
    Описание: Равнина, покрытая травой, которая вызывает у путешественников сонливость и яркие сновидения прямо наяву.
    Действие при первом открытии: Игрок теряет следующий ход, если не пройдет проверку
    Событие: Успех: +1 моральное состояние и добавьте хорошее событие под низ колоды; Провал: игрок теряет ход, -1 моральное состояние
  */

  public G5DreamSteppe() {
    super(
        TileCategory.GRASS,
        " G5 ",
        "Степь снов",
        // TODO implement turn skipping check
        TileEffect.SKIP_TURN,
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
            2,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
            4,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            5,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            6,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap))),
        List.of());
  }
}
