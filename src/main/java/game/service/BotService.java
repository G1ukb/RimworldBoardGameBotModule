package game.service;

import game.model.Bot;
import org.springframework.stereotype.Service;

@Service
public class BotService {

  public void takeTurn(Bot bot) {
    if (bot.actionsRemaining > 0) {
      bot.strategy.executeStep(bot);
      bot.actionsRemaining--;
    } else {
      bot.isDone = true;
      System.out.println("Бот завершил действия.");
    }
  }
}
