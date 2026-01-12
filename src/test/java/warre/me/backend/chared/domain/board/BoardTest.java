package warre.me.backend.chared.domain.board;

import org.junit.jupiter.api.Test;
import warre.me.backend.chared.domain.board.tile.Tile;

import java.util.Arrays;

import static warre.me.backend.chared.domain.board.Board.TILES_SIZE;
import static warre.me.backend.chared.domain.board.Board.getFullBoard;

class BoardTest {

    @Test
    public void testBoardToFrontEndBoard() {
        var board= getFullBoard();
        var tilesPerSide= (TILES_SIZE/4) + 1;
        var frontEndTiles= new Tile[tilesPerSide][tilesPerSide];

        final int startCol= tilesPerSide-1;
        final int startRow= tilesPerSide-1;

        int colCounter=0;
        int rowCounter=0;

        int col= startCol;
        int row= startRow;

        int cornerCount=0;

        for (int i = 0; i < TILES_SIZE; i++) {
            var tile= board.get(i);
            var isCorner= tile.isCorner();

            frontEndTiles[row][col]= tile;

            if (isCorner) {
                if (cornerCount==0) {
                    colCounter=-1;
                } else if (cornerCount==1) {
                    colCounter=0;
                    rowCounter=-1;
                } else if (cornerCount==2) {
                    rowCounter=0;
                    colCounter=1;
                } else if (cornerCount==3) {
                    rowCounter=1;
                    colCounter=0;
                }
                cornerCount++;
            }
            col+=colCounter;
            row+=rowCounter;
        }

        Arrays.stream(frontEndTiles)
                .forEach(array -> System.out.println(Arrays.toString(array)));
    }
}