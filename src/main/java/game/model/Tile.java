package game.model;

public class Tile {
  // расположение в мапе
  public int x;
  public int y;

  // пока что только имя
  public TileJson type;
  public Boolean isExplored;

  public Tile(int x, int y, TileJson type, Boolean isExplored) {
    this.x = x;
    this.y = y;
    this.type = type;
    this.isExplored = isExplored;
  }
}
