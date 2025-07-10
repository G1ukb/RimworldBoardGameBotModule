package game.model.tile.grass;

import game.model.effect.TileEffect;
import game.model.tile.BasicTile;
import game.model.tile.TileCategory;

import java.util.Map;

public class G2SunnyPlains extends BasicTile {

  public G2SunnyPlains() {
    super(
      TileCategory.GRASS,
      " G2 ",
      "Солнечные равнины",
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
        new TileEffect(Map.of(), b -> b.psyche = Math.min(b.psyche + 2, b.psycheCap))));
  }
}
