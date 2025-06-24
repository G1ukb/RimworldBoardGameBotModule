package game.service;

import game.model.ResourceType;
import game.model.tile.Tile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

  private static final Logger log = LoggerFactory.getLogger(GoalService.class);

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
    log.info("Цель: открыть тайлы: открыто {} из {}", explored, total);

    int totalCollected = 0;
    int totalPossible = 0;
    for (ResourceType type : ResourceType.values()) {
      totalCollected += resourceService.get(type);
      totalPossible += type.cap();
    }
    log.info("Цель: ресурсы {} собраны из {}", totalCollected, totalPossible);
  }

}
