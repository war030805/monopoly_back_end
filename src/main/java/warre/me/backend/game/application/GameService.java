package warre.me.backend.game.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.chared.domain.cards.cardTypes.CardSpecificType;
import warre.me.backend.game.api.dto.GameDataDto;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.game.GameRepository;
import warre.me.backend.game.domain.gamePlayer.DoAction;
import warre.me.backend.player.domain.PlayerId;

@Service
@Transactional
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game rollDice(GameId gameId, PlayerId playerId, DoAction doAction) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.trowDicesByPlayer(playerId, doAction);

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

        gameRepository.save(game);
        return game;
    }

    public Game endMove(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.endMove(playerId);

        gameRepository.save(game);
        return game;
    }

    public void payTax(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.payTax(playerId);

        gameRepository.save(game);
    }

    public void pullCard(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.pullCard(playerId);

        gameRepository.save(game);
    }

    public void saveCard(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.saveCard(playerId);

        gameRepository.save(game);
    }

    public void useCard(GameId gameId, PlayerId playerId) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.useCard(playerId);

        gameRepository.save(game);
    }

    public void useSavedCard(GameId gameId, PlayerId playerId, CardSpecificType cardSpecificType) {
        var game= gameRepository.findByGameId(gameId)
                .orElseThrow(gameId::notFound);

        game.useSavedCard(playerId, cardSpecificType);

        gameRepository.save(game);
    }
}
