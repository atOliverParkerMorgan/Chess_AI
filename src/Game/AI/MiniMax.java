package Game.AI;

import Board.Board;
import Board.Move;
import Game.Game;
import Game.Player;

public class MiniMax {
   private int depth;
   private boolean isWhite;

    public MiniMax(int depth, boolean isWhite){
        this.depth = depth;
        this.isWhite = isWhite;

    }
    public Move AI_logic(final Game mGame) {
        Player AI = isWhite?mGame.getBlack():mGame.getWhite();
        Move best_move = null;

        int currentScore;
        int highestScore = isWhite?Integer.MIN_VALUE:Integer.MAX_VALUE;


        // black player
        Board StartingBoard = mGame.getBoard();
        //for (int i = 0; i < depth; i++) {
        for (int i = 0; i < AI.getPieces().size(); i++) {
            for(Move move: AI.getPieces().get(i).all_possible_moves){
                mGame.MOVE(move,false);
                currentScore = Evaluate.EvaluateGame(mGame);
                mGame.setBoard(StartingBoard);

                if(currentScore>highestScore){
                    highestScore = currentScore;
                    best_move = move;
                }
            }
        }
        return best_move;
        //}





    }




}
