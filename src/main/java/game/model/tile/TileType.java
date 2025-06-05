package game.model.tile;

public enum TileType {
  // Стартовые тайлы
  START(TileCategory.START, "(s)", "Стартовая позиция"),

  // Тайлы Полей
  COMMON_GRASS(TileCategory.GRASS, "...", "Обычное Поле"),
  SMOG_GRASS(TileCategory.GRASS, ":::", "Задымленное Поле"),

  // Тайлы Леса
  COMMON_FOREST(TileCategory.FOREST, "III", "Обычный Лес"),

  // Тайлы Воды
  COMMON_LAKE(TileCategory.WATER, "~~~", "Обычное Озеро"),

  // Тайлы Гор
  COMMON_MOUNTAIN(TileCategory.MOUNTAIN, "^^^", "Обычная Горная Местность"),

  // Тайлы Пустыни
  DESERT(TileCategory.DESERT, "*~*", "Обычная Пустыня");

  private final TileCategory category;
  private final String symbol;
  private final String tileName;

  TileType(TileCategory category, String symbol, String tileName) {
    this.category = category;
    this.symbol = symbol;
    this.tileName = tileName;
  }

  public TileCategory category() {
    return category;
  }

  public String symbol() {
    return symbol;
  }

  public String tileName() {
    return tileName;
  }
}
