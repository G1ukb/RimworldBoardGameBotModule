package game.service;

import game.model.Bot;
import org.springframework.stereotype.Service;

@Service
public class BotService {

  private final ActionService actionService;

  public BotService(ActionService actionService) {
    this.actionService = actionService;
  }

  public void takeAction(Bot bot) {
    if (bot.actionsRemaining > 0) {
      bot.strategy.executeStep(bot, actionService);
    }
  }

  public void resetActions(Bot bot) {
    bot.actionsRemaining = bot.maximumActions;
  }
}
