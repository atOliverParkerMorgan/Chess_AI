package Game.AI;

import Board.Spot;

import Game.Player;
import Game.Game;
import pieces.*;

public class StandardBoardEvaluator implements BoardEvaluation {

    private static final int CHECK_BONUS = 5;
    private static final int CHECK_MATE_BONUS = 999999;
    private static final int DEPTH_BONUS = 10;
    private static final int CASTLE_BONUS = 50;


    @Override
    public  int evaluate(final Game game, final int depth) {
        return scorePlayer(game,true, depth) -
               scorePlayer(game,false, depth);
    }

    private int scorePlayer(final Game game,
                            final boolean white,
                            final int depth) {
        return pieceValue(game , white)+
               mobility(game, white);
                //check(game.board.currentPlayer) +
                //checkmate(game.board.currentPlayer, depth);
                //castled(game.board.currentPlayer);
    }

    private static int castled(Player player) {
        return player.HasCastled() ? CASTLE_BONUS : 0;
    }

    private static int checkmate(Player player, int depth){
        return player.getOpponent().IsInCheckMate() ? CHECK_MATE_BONUS * depthBonus(depth): 0;
    }

    private static int depthBonus(int depth) {
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(Player player) {
        return player.getOpponent().IsInCheck() ? CHECK_BONUS: 0;
    }

    private static int mobility(final Game game, final boolean white){
        int score = 0;

            for( Spot[] spots : game.getBoard().spots){
                for(Spot s: spots) {
                    if(s.isOccupied()){
                        if(white) {
                            switch (s.piece.category) {
                                case "Rook_white": {
                                    Rook r = (Rook) s.piece;
                                    score += r.pos_eval_white[r.y][r.x];

                                    break;
                                }
                                case "Knight_white": {
                                    Knight k = (Knight) s.piece;
                                    score += k.pos_eval_white[k.y][k.x];

                                    break;
                                }
                                case "Bishop_white": {
                                    Bishop b = (Bishop) s.piece;
                                    score += b.pos_eval_white[b.y][b.x];

                                    break;
                                }
                                case "King_white": {
                                    King k = (King) s.piece;
                                    score += k.pos_eval_white[k.y][k.x];

                                    break;
                                }
                                case "Queen_white": {
                                    Queen q = (Queen) s.piece;
                                    score += q.pos_eval_white[q.y][q.x];

                                    break;
                                }
                                case "Pawns_white": {
                                    Pawn p = (Pawn) s.piece;
                                    score += p.pos_eval_white[p.y][p.x];
                                    break;
                                }
                            }
                        }else {
                            switch (s.piece.category) {
                                case "Rook_black": {
                                    Rook r = (Rook) s.piece;
                                    score += r.pos_eval_black[r.y][r.x];

                                    break;
                                }
                                case "Knight_black": {
                                    Knight k = (Knight) s.piece;
                                    score += k.pos_eval_black[k.y][k.x];

                                    break;
                                }
                                case "Bishop_black": {
                                    Bishop b = (Bishop) s.piece;
                                    score += b.pos_eval_black[b.y][b.x];

                                    break;
                                }
                                case "King_black": {
                                    King k = (King) s.piece;
                                    score += k.pos_eval_black[k.y][k.x];

                                    break;
                                }
                                case "Queen_black": {
                                    Queen q = (Queen) s.piece;
                                    score += q.pos_eval_black[q.y][q.x];

                                    break;
                                }
                                case "Pawns_black": {
                                    Pawn p = (Pawn) s.piece;
                                    score += p.pos_eval_black[p.y][p.x];
                                    break;
                                }
                            }

                        }


                    }

                }



            }
        return score;


    }

    private static int pieceValue(final Game game, final boolean white){
        int pieceValueScore = 0;
        for( Spot[] spots : game.getBoard().spots){
            for(Spot s: spots) {
                if(s.isOccupied()){
                    if(white && s.piece.category.contains("white")){
                        pieceValueScore +=s.piece.Score;
                    }else if(!white && s.piece.category.contains("black")){
                        pieceValueScore +=s.piece.Score;

                    }
                }

            }
        }

        return pieceValueScore;
    }


}
