package game.model.action;

public enum ResourceType {
  FOOD(10),
  BUILDING_MATERIAL(10),
  MEDICINE(10),
  SPECIAL(10);

  private final int cap;

  ResourceType(int cap) {
    this.cap = cap;
  }

  public int cap() {
    return cap;
  }
}
