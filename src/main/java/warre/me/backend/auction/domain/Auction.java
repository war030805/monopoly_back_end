package warre.me.backend.auction.domain;

import lombok.Getter;
import warre.me.backend.game.domain.game.Game;
import warre.me.backend.game.domain.game.GameId;
import warre.me.backend.game.domain.gamePlayer.GamePlayer;
import warre.me.backend.player.domain.PlayerId;
import warre.me.backend.shared.domain.NotFoundException;
import warre.me.backend.shared.domain.board.Board;
import warre.me.backend.shared.domain.board.property.Property;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Auction {
    @Getter
    private final GameId gameId;
    private final Map<PlayerId, AuctionPlayer> auctionPlayers;

    @Getter
    private final Property property;

    @Getter
    private final PlayerId starter;

    @Getter
    private LocalDateTime lastBetTime;

    private final boolean done;

    public Auction(GameId gameId, Map<PlayerId,AuctionPlayer> auctionPlayers, Property property, PlayerId starter) {
        this.gameId = gameId;
        this.auctionPlayers = auctionPlayers;
        this.property = property;
        this.starter = starter;
        lastBetTime= LocalDateTime.now();
        done=false;
    }


    public static Auction makeAuction(Game game, GamePlayer starter) {
        var property= Board.getPropertyFromPlace(starter.getPlace());

        var auctionPlayers=game.getPlayers().stream()
                .map(AuctionPlayer::makePLayer)
                .collect(Collectors.toMap(AuctionPlayer::getPlayerId, Function.identity()));

        return new Auction(game.getGameId(), auctionPlayers, property, starter.getPlayerId());
    }

    public void isMember(PlayerId playerId) {
        getAuctionPLayer(playerId)
                .orElseThrow(playerId::notFound);
    }

    public Optional<AuctionPlayer> getAuctionPLayer(PlayerId playerId) {
        return Optional.ofNullable(auctionPlayers.get(playerId));
    }

    public List<AuctionPlayer> getMembers() {
        return auctionPlayers.values().stream().toList();
    }

    public Optional<AuctionPlayer> getWinner() {
        if (!done) {
            return Optional.empty();
        }

        return Optional.of(getHighestBet());
    }

    public AuctionPlayer getHighestBet() {
        return getMembers().stream()
                .sorted()
                .filter(AuctionPlayer::isBetting)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("did not find a member of auction that is betting"));
    }

    private void doneCheck() {
        if (done) {
            throw new AuctionException("cannot do actions when is done");
        }
    }

    public void placeBet(PlayerId playerId, int bet) {
        doneCheck();

        var highestBet=getHighestBet().getBet();

        int betSetting=bet+highestBet;

        var player= getAuctionPLayer(playerId)
                .orElseThrow(playerId::notFound);



        player.betAmount(betSetting);

        lastBetTime=LocalDateTime.now();
    }

    public void passBet(PlayerId playerId) {
        doneCheck();
        var player= getAuctionPLayer(playerId)
                .orElseThrow(playerId::notFound);

        player.passBet();
    }
}
