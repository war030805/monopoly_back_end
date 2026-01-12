package warre.me.backend.game.infrastructure;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import warre.me.backend.game.domain.game.GameRepository;

@Configuration
public class DataConfig {
    private final GameRepository gameRepository;

    public DataConfig(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {

    }
}
