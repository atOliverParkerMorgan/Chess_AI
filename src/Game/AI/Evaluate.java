package Game.AI;

import Game.Game;
import pieces.Piece;

final class Evaluate {
    private static final int DEPTH_BONUS = 10;
    private static final int CHECK_BONUS = 20;
    private static final int CHECK_MATE_BONUS = 100000;
    private static final double MOBILITY_BIAS = 2;
    private static double PLACEMENT_BIAS = 11;

    private final static int CASTLE_BONUS = 40;
    private final static int TWO_BISHOPS_BONUS = 50;
    private final static int NOT_ISOLATED_PAWN_BONUS = 10;

    static double EvaluateGame(final Game mGame, int depth) {
        if(PLACEMENT_BIAS>2){
            PLACEMENT_BIAS-=mGame.getTurn();
        }
        return Score(mGame) + mobility(mGame) + check(mGame) + checkmate(mGame, depth) + hasCastled(mGame)+twoBishops(mGame) + pawnStructure(mGame);

    }

    private static int Score(final Game mGame) {
        int blackScore = 0;
        int whiteScore = 0;

        for (Piece p : mGame.getWhite().getPieces()) {
            whiteScore += p.Score;
            whiteScore += (Game.whiteSide ? p.pos_eval_white[p.y][p.x] : p.pos_eval_black[p.y][p.x])*PLACEMENT_BIAS;
        }
        for (Piece p : mGame.getBlack().getPieces()) {
            blackScore += p.Score;
            blackScore += (Game.whiteSide ? p.pos_eval_black[p.y][p.x] : p.pos_eval_white[p.y][p.x])*PLACEMENT_BIAS;
        }
        return whiteScore - blackScore;
    }

    private static int depthBonus(final int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Game mGame) {

        if (mGame.getBoard().currentPlayer.isWhite()) {

            return -(mGame.getBoard().currentPlayer.IsInCheck() ? CHECK_BONUS : 0);
        } else {
            return mGame.getBoard().currentPlayer.IsInCheck() ? CHECK_BONUS : 0;
        }


    }

    private static int checkmate(final Game mGame, int depth) {
        if (mGame.getBoard().currentPlayer.isWhite()) {
            return -(mGame.getBoard().currentPlayer.IsInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0);
        } else {
            return mGame.getBoard().currentPlayer.IsInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth) : 0;
        }

    }

    private static double mobility(final Game game) {
        return (game.getWhite().Legal_moves().size() - game.getBlack().Legal_moves().size()) * MOBILITY_BIAS;
    }
    private static double hasCastled(final Game game){
        assert game.getWhite().Get_King() != null;
        double white = game.getWhite().Get_King().castled?CASTLE_BONUS:0;
        assert game.getBlack().Get_King() != null;
        double black = game.getBlack().Get_King().castled?CASTLE_BONUS:0;

        return white - black;
    }
    private static double twoBishops(final Game game){
        int w = 0;
        int b = 0;
        int re = 0;

        for(Piece p: game.getWhite().pieces){
            if(p.category.contains("Bishop")){
               w++;
               if(w==2){
                   break;
               }
            }
        }

        for(Piece p: game.getBlack().pieces){
            if(p.category.contains("Bishop")){
                b++;
                if(b==2){
                    break;
                }
            }
        }

        if(w==2){
            re+=TWO_BISHOPS_BONUS;
        }if(b==2){
            re-=TWO_BISHOPS_BONUS;
        }
        return re;

    }
    private static double pawnStructure(final Game game){
        int behindW = Game.whiteSide?-1:1;
        int behindB = Game.whiteSide?1:-1;

        int whitePawnStructure = 0;
        int blackPawnStructure = 0;

        for(Piece p :game.getWhite().pieces){
            if(p.category.contains("Pawns")){
                if(p.x!=7){
                    if(game.getBoard().getSpot(p.x+1,p.y+behindW).piece!=null) {
                        if (game.getBoard().getSpot(p.x + 1, p.y + behindW).piece.category.equals("Pawns_white")) {
                            whitePawnStructure += NOT_ISOLATED_PAWN_BONUS;
                        }
                    }
                }if(p.x!=0){
                    if(game.getBoard().getSpot(p.x-1,p.y+behindW).piece!=null) {
                        if (game.getBoard().getSpot(p.x - 1, p.y + behindW).piece.category.equals("Pawns_white")) {
                            whitePawnStructure += NOT_ISOLATED_PAWN_BONUS;
                        }
                    }
                }

            }
        }
        for(Piece p :game.getBlack().pieces){
            if(p.category.contains("Pawns")){
                if(p.x!=7){
                    if(game.getBoard().getSpot(p.x+1,p.y+behindB).piece!=null) {
                        if (game.getBoard().getSpot(p.x + 1, p.y + behindB).piece.category.equals("Pawns_black")) {
                            blackPawnStructure += NOT_ISOLATED_PAWN_BONUS;
                        }
                    }
                }if(p.x!=0){
                    if(game.getBoard().getSpot(p.x-1,p.y+behindB).piece!=null) {
                        if (game.getBoard().getSpot(p.x - 1, p.y + behindB).piece.category.equals("Pawns_black")) {
                            blackPawnStructure += NOT_ISOLATED_PAWN_BONUS;
                        }
                    }
                }

            }
        }
        return whitePawnStructure-blackPawnStructure;
    }


}