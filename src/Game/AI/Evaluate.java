package Game.AI;

import Game.Game;
import pieces.Piece;

class Evaluate {
    static int EvaluateGame(Game mGame){
        int blackScore = 0;
        int whiteScore = 0;

        mGame.UpdatePlayerPieces();

        for(Piece p : mGame.getWhite().getPieces()){
            whiteScore+=p.Score;
        }
        for(Piece p: mGame.getBlack().getPieces()){
            blackScore+=p.Score;
        }

        return whiteScore - blackScore;
    }

}
