package Game.AI;

import Game.Game;
import pieces.Piece;

final class Evaluate {
    private static final int DEPTH_BONUS = 10;
    private static final int CHECK_BONUS = 5;
    private static final int CHECK_MATE_BONUS = 99999;
    private static final double MOBILITY_BIAS = 0.4;

    static double EvaluateGame(final Game mGame, int depth){
        return Score(mGame)+mobility(mGame)+check(mGame)+checkmate(mGame,depth);

    }
    private static int Score(final Game mGame){
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
        return whiteScore - blackScore;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Game mGame) {

        if(mGame.getBoard().currentPlayer.isWhite()) {

           return -(mGame.getBoard().currentPlayer.IsInCheck() ? CHECK_BONUS : 0);
        }else {
           return  mGame.getBoard().currentPlayer.IsInCheck() ? CHECK_BONUS : 0;
        }


    }
    private static int checkmate(final Game mGame, int depth){
        if(mGame.getBoard().currentPlayer.isWhite()) {
            return -(mGame.getBoard().currentPlayer.IsInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth): 0);
        }else {
            return mGame.getBoard().currentPlayer.IsInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth): 0;
        }

    }private static double mobility(final Game game){
        return game.getWhite().Legal_moves().size() - game.getBlack().Legal_moves().size()*MOBILITY_BIAS;
    }



}
