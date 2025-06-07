package game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record GameConfig(
    @Value("${game.min-x:-2}") int minX,
    @Value("${game.min-y:-2}") int minY,
    @Value("${game.max-x:2}") int maxX,
    @Value("${game.max-y:2}") int maxY,
    @Value("${game.bot.max-actions:5}") int botMaxActions,
    @Value("${game.bot.start-hp:4}") int botStartHp,
    @Value("${game.bot.start-mood:4}") int botStartMood,
    @Value("${game.max-turns:3}") int maxTurns) {}
