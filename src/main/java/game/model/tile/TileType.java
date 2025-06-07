package game.model.tile;

import game.model.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public enum TileType {
  // Стартовые тайлы
  START(TileCategory.START, "(s)", "Стартовая позиция", Map.of(ResourceType.FOOD, 2)),

  // Тайлы Полей
  COMMON_GRASS(TileCategory.GRASS, "...", "Обычное Поле", Map.of(ResourceType.FOOD, 1)),
  SMOG_GRASS(TileCategory.GRASS, ":::", "Задымленное Поле", Map.of(ResourceType.FOOD, 1)),

  // Тайлы Леса
  COMMON_FOREST(
      TileCategory.FOREST, "III", "Обычный Лес", Map.of(ResourceType.BUILDING_MATERIAL, 2)),

  // Тайлы Воды
  COMMON_LAKE(TileCategory.WATER, "~~~", "Обычное Озеро", Map.of(ResourceType.FOOD, 1)),

  // Тайлы Гор
  COMMON_MOUNTAIN(
      TileCategory.MOUNTAIN,
      "^^^",
      "Обычная Горная Местность",
      Map.of(ResourceType.BUILDING_MATERIAL, 3)),

  // Тайлы Пустыни
  DESERT(TileCategory.DESERT, "*~*", "Обычная Пустыня", Map.of());

  private final TileCategory category;
  private final String symbol;
  private final String tileName;
  private final Map<ResourceType, Integer> resources;

  TileType(
      TileCategory category, String symbol, String tileName, Map<ResourceType, Integer> resources) {
    this.category = category;
    this.symbol = symbol;
    this.tileName = tileName;

    this.resources = new EnumMap<>(ResourceType.class);
    this.resources.putAll(resources);
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

  public Map<ResourceType, Integer> resources() {
    return resources;
  }
}
