package game.model.tile;

public class Tile {
  // расположение в мапе
  public int x;
  public int y;

  // пока что только имя
  public BasicTile type;
  public Boolean isExplored;

  public Tile(int x, int y, BasicTile type, Boolean isExplored) {
    this.x = x;
    this.y = y;
    this.type = type;
    this.isExplored = isExplored;
  }
}
