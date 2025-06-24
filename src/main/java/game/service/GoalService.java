package game.service;

import game.model.tile.Tile;
import org.springframework.stereotype.Service;

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

}
