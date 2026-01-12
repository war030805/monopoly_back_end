package warre.me.backend.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import warre.me.backend.chared.domain.board.Board;
import warre.me.backend.chared.domain.board.tile.Tile;

import java.util.List;

@Service
@Transactional
public class BoardService {

    public List<Tile> getBoard() {
        return Board.getFullBoard();
    }
}
