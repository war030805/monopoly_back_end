package warre.me.backend.board.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import warre.me.backend.board.api.dto.TileDto;
import warre.me.backend.board.application.BoardService;
import warre.me.backend.shared.domain.board.tile.Tile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping()
    public ResponseEntity<List<TileDto>> getBoard(
//            @AuthenticationPrincipal Jwt token
    ) {
        List<Tile> tiles= boardService.getBoard();
        return ResponseEntity.ok(tiles.stream()
                .map(TileDto::fromDomain)
                .toList());
    }
}
