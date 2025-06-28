package game.model.tile;

import game.model.Bot;
import game.model.ResourceType;

import java.util.Map;
import java.util.function.Consumer;

public enum TileType {
  // Стартовые тайлы
  START(TileCategory.START,
          " (s)",
          "Стартовая позиция",
          bot -> bot.psyche = Math.max(bot.psyche - 1, 0),
          Map.of(
                  1, Map.of(),
                  2, Map.of(),
                  3, Map.of(),
                  4, Map.of(ResourceType.FOOD, 1),
                  5, Map.of(ResourceType.FOOD, 1),
                  6, Map.of(ResourceType.FOOD, 2))),

  // Тайлы Полей
  COMMON_GRASS(
      TileCategory.GRASS,
      " G1 ",
      "Поле тишины",
      bot -> bot.psyche = Math.max(bot.psyche - 1, 0),
      Map.of(
          1, Map.of(),
          2, Map.of(),
          3, Map.of(),
          4, Map.of(ResourceType.FOOD, 1),
          5, Map.of(ResourceType.FOOD, 1),
          6, Map.of(ResourceType.FOOD, 2))),

  // Тайлы Леса
  COMMON_FOREST(
      TileCategory.FOREST,
      " F1 ",
      "Обычный Лес",
      bot -> {},
      Map.of(
          1, Map.of(),
          2, Map.of(),
          3, Map.of(),
          4, Map.of(ResourceType.BUILDING_MATERIAL, 1),
          5, Map.of(ResourceType.BUILDING_MATERIAL, 1),
          6, Map.of(ResourceType.BUILDING_MATERIAL, 2))),

  // Тайлы Воды
  COMMON_LAKE(
      TileCategory.WATER,
      " W1 ",
      "Обычное Озеро",
      bot -> {},
      Map.of(
          1, Map.of(),
          2, Map.of(),
          3, Map.of(),
          4, Map.of(ResourceType.MEDICINE, 1),
          5, Map.of(ResourceType.MEDICINE, 1),
          6, Map.of(ResourceType.MEDICINE, 2))),

  // Тайлы Гор
  COMMON_MOUNTAIN(
      TileCategory.MOUNTAIN,
      " M1 ",
      "Обычная Горная Местность",
      bot -> {},
      Map.of(
          1, Map.of(),
          2, Map.of(),
          3, Map.of(),
          4, Map.of(ResourceType.BUILDING_MATERIAL, 1),
          5, Map.of(ResourceType.BUILDING_MATERIAL, 1),
          6, Map.of(ResourceType.BUILDING_MATERIAL, 2))),

  // Тайлы Пустыни
  DESERT(TileCategory.DESERT,
          " D1 ",
          "Обычная Пустыня",
          bot -> {},
          Map.of(
                  1, Map.of(),
                  2, Map.of(),
                  3, Map.of(),
                  4, Map.of(ResourceType.SPECIAL, 1),
                  5, Map.of(ResourceType.SPECIAL, 1),
                  6, Map.of(ResourceType.SPECIAL, 2)));

  private final TileCategory category;
  private final String symbol;
  private final String tileName;
  private final Map<Integer, Map<ResourceType, Integer>> resourceRollTable;
  private final Consumer<Bot> onDiscover;

  TileType(
      TileCategory category,
      String symbol,
      String tileName,
      Consumer<Bot> onDiscover,
      Map<Integer, Map<ResourceType, Integer>> resourceRollTable) {
    this.category = category;
    this.symbol = symbol;
    this.tileName = tileName;

    this.onDiscover = onDiscover;
    this.resourceRollTable = resourceRollTable;
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

  public boolean hasResource(ResourceType type) {
    return resourceRollTable.values().stream().anyMatch(m -> m.containsKey(type));
  }

  public Map<ResourceType, Integer> resourcesForRoll(int roll) {
    return resourceRollTable.getOrDefault(roll, Map.of());
  }

  public void applyDiscoverEffect(Bot bot) {
    onDiscover.accept(bot);
  }
}
