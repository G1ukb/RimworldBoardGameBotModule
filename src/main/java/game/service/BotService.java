package game.service;

import game.model.Bot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BotService {

  private final ActionService actionService;

  public BotService(ActionService actionService) {
    this.actionService = actionService;
  }

  public List<String> takeAction(Bot bot) {
    if (bot.actionsRemaining > 0) {
      return bot.strategy.executeStep(bot, actionService);
    }
    return List.of();
  }

  public void resetActions(Bot bot) {
    bot.actionsRemaining = bot.maximumActions;
  }
}
