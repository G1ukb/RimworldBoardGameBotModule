package game.service;

import game.model.action.ResourceType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class ResourceService {

  private final EnumMap<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);

  public ResourceService() {
    for (ResourceType type : ResourceType.values()) {
      resources.put(type, 0);
    }
  }

  public void add(ResourceType type, int amount) {
    int current = resources.get(type);
    int newAmount = Math.min(current + amount, type.cap());
    newAmount = Math.max(newAmount, 0);

    resources.put(type, newAmount);
  }

  public int get(ResourceType type) {
    return resources.get(type);
  }

  public Map<ResourceType, Integer> getAll() {
    return resources;
  }

  public boolean isAllAtCap() {
    for (ResourceType type : ResourceType.values()) {
      if (resources.get(type) < type.cap()) {
        return false;
      }
    }
    return true;
  }
}
