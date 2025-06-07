package game.strategies;

import game.model.Bot;
import game.service.ActionService;

public interface BotStrategy {
  void executeStep(Bot bot, ActionService actionService);
}
