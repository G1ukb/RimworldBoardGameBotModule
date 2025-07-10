package game.model.tile.grass;

import game.model.action.ResourceType;
import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.List;
import java.util.Map;

public class G10BarrenPlain extends BasicTile {
  /*
    - 10: Обнажённая равнина
    Описание: Почва полностью лишена жизни, поверхность — стекловидное стекло после древнего энергетического удара.
    Действие при первом открытии: Игрок теряет 1 моральное состояние
    Событие: Успех: Успех: +1 строительный материал и откройте предмет; Провал: потеря 1 здоровья
  */

  public G10BarrenPlain() {
    super(
        TileCategory.GRASS,
        " G10 ",
        "Обнажённая равнина",
        new TileEffect(Map.of(), b -> b.psyche = Math.max(b.psyche - 1, 0)),
        Map.of(
            1, new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            2, new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            3, new TileEffect(Map.of(), b -> b.health = Math.max(b.health - 1, 0)),
            4, new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            5, new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {}),
            6, new TileEffect(Map.of(ResourceType.BUILDING_MATERIAL, 1), b -> {})),
        List.of(ResourceType.BUILDING_MATERIAL));
  }
}
