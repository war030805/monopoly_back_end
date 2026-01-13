package warre.me.backend.lobby.infrastructure;

import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import warre.me.backend.lobby.domain.lobby.LobbyRepository;

@Transactional
@Configuration
public class LobbyDataConfig {
    private final LobbyRepository lobbyRepository;

    public LobbyDataConfig(LobbyRepository lobbyRepository) {
        this.lobbyRepository = lobbyRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {

    }
}
