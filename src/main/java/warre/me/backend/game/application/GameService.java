package warre.me.backend.game.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.game.api.dto.GameDataDto;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.game.domain.gamePlayer.PlayerId;

@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game rollDice(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.trowDicesByPlayer(playerId);

        gameRepository.save(game);
        return game;
    }

    public GameDataDto getGameData(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.isInGame(playerId);

        return GameDataDto.fromDomain(game);
    }

    public Game buyProperty(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.buyProperty(playerId);

        gameRepository.save(game);
        return game;
    }

    public Game payLandOnOtherProperty(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.payLandOnOtherProperty(playerId);

        gameRepository.save(game);

        return game;
    }

    public Game move(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.move(playerId);
        return game;
    }
}
