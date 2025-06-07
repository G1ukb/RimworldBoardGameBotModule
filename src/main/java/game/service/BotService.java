package game.service;

import game.model.Bot;
import org.springframework.stereotype.Service;

@Service
public class BotService {

  public void takeAction(Bot bot) {
    if (bot.actionsRemaining > 0) {
      bot.strategy.executeStep(bot);
      bot.actionsRemaining--;
    }
  }

  public void resetActions(Bot bot) {
    bot.actionsRemaining = bot.maximumActions;
  }
}
