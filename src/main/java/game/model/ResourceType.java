package game.model;

/** Different resources that a bot can store. */
public enum ResourceType {
  FOOD(50),
  BUILDING_MATERIAL(50),
  MEDICINE(30),
  SPECIAL(10);

  private final int cap;

  ResourceType(int cap) {
    this.cap = cap;
  }

  public int cap() {
    return cap;
  }
}
