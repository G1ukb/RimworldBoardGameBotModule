package game.model.tile;

import game.model.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public enum TileType {
  // Стартовые тайлы
  START(TileCategory.START, " (s)", "Стартовая позиция", Map.of(ResourceType.FOOD, 2)),

  // Тайлы Полей
  COMMON_GRASS(TileCategory.GRASS, " G1 ", "Обычное Поле", Map.of(ResourceType.FOOD, 1)),
  SMOG_GRASS(TileCategory.GRASS, " G2 ", "Задымленное Поле", Map.of(ResourceType.FOOD, 1)),

  // Тайлы Леса
  COMMON_FOREST(
      TileCategory.FOREST, " F1 ", "Обычный Лес", Map.of(ResourceType.BUILDING_MATERIAL, 2)),

  // Тайлы Воды
  COMMON_LAKE(TileCategory.WATER, " W1 ", "Обычное Озеро", Map.of(ResourceType.MEDICINE, 1)),

  // Тайлы Гор
  COMMON_MOUNTAIN(
      TileCategory.MOUNTAIN,
      " M1 ",
      "Обычная Горная Местность",
      Map.of(ResourceType.BUILDING_MATERIAL, 3)),

  // Тайлы Пустыни
  DESERT(TileCategory.DESERT, " D1 ", "Обычная Пустыня", Map.of(ResourceType.SPECIAL, 1));

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
