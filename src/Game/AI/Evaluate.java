package Game.AI;

import Game.Game;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;

class Evaluate {
    static int EvaluateGame(Game mGame){
        mGame.UpdatePlayerPieces();
        mGame.getBoard().BoardInChars();
        mGame.getBoard().printBoardChars();



        int blackScore = 0;
        int whiteScore = 0;

        for(Piece p : mGame.getWhite().getPieces()){
            whiteScore +=p.Score;
            whiteScore += Game.whiteSide ? p.pos_eval_white[p.y][p.x] : p.pos_eval_black[p.y][p.x];
        }
        for(Piece p: mGame.getBlack().getPieces()){
            blackScore+=p.Score;
            blackScore += Game.whiteSide ? p.pos_eval_black[p.y][p.x] : p.pos_eval_white[p.y][p.x];
        }

        System.out.println(whiteScore - blackScore);
        return whiteScore - blackScore;
    }

}
