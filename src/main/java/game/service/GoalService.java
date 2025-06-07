package game.service;

import game.model.Bot;
import game.model.ResourceType;
import game.model.tile.Tile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

  private final MapService mapService;
  private final ResourceService resourceService;

  public GoalService(MapService mapService, ResourceService resourceService) {
    this.mapService = mapService;
    this.resourceService = resourceService;
  }

  public boolean isAllTilesExplored() {
    for (Tile tile : mapService.tiles.values()) {
      if (!tile.isExplored) {
        return false;
      }
    }
    return true;
  }

  public boolean isResourcesMaxed() {
    return resourceService.isAllAtCap();
  }

  public boolean isAllGoalsCompleted() {
    return isAllTilesExplored() && isResourcesMaxed();
  }

  public void logProgress() {
    long explored = mapService.tiles.values().stream().filter(t -> t.isExplored).count();
    long total = mapService.tiles.size();
    System.out.println("Цель: открыть тайлы: открыто " + explored + " из " + total);

    int totalCollected = 0;
    int totalPossible = 0;
    for (ResourceType type : ResourceType.values()) {
      totalCollected += resourceService.get(type);
      totalPossible += type.cap();
    }
    System.out.println("Цель: ресурсы " + totalCollected + " собраны из " + totalPossible);
  }

}
