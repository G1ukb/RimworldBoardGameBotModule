package game.strategies;

import game.model.Bot;
import game.service.ActionService;

import java.util.List;

public interface BotStrategy {
  List<String> executeStep(Bot bot, ActionService actionService);
}
