package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G7RainPlain extends BasicTile {

  /*
    - 7: Равнина дождя
    Описание: Здесь постоянно идёт дождь, который никогда не прекращается, хотя вокруг местность сухая и солнечная.
    Действие при первом открытии: Игрок сразу получает +1 провиант, но теряет 1 моральное состояние
    Событие: Успех: восстановите моральное состояние и откройте ещё одну карту ресурсов; Провал: потеря 1 здоровья
  */

  public G7RainPlain() {
    super(
        TileCategory.GRASS,
        " G7 ",
        "Равнина дождя",
        new TileEffect(Map.of(ResourceType.FOOD, 1), b -> b.psyche = Math.max(b.psyche - 1, 0)),
        Map.of(
            1,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            2,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            3,
            new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            4,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            5,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap)),
            6,
            new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 1, b.psycheCap))),
        List.of());
  }
}
