package game.model.tile.grass;

import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G1FieldOfSilence extends BasicTile {

  /*
    - 1: Поле тишины
    Описание: Пространство, где звук полностью отсутствует, создавая тревожное чувство пустоты и изоляции.
    Действие при первом открытии: Игрок немедленно теряет 1 психологическое состояние
    Событие: Успех: восстановите потерянное состояние и положите хорошее событие под низ колоды; Провал: игрок теряет ещё 1 психологическое состояние
   */

  public G1FieldOfSilence() {
    super(
        TileCategory.GRASS,
        " G1 ",
        "Поле тишины",
        new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 2, 0)),
            2,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
            4,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            5,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            6,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 2, b.psycheCap))),
        List.of());
  }
}
