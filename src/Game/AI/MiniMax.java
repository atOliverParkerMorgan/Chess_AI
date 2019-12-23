package Game.AI;

import Board.Board;
import Board.Move;
import Game.Game;
import Game.Player;

public class MiniMax {
    private int searchDepth;

    public MiniMax(int depth, boolean isWhite){
        this.searchDepth = depth;

    }
    public Move getBestMove(final Game mGame) {

        final long starTime = System.currentTimeMillis();

        if(mGame.getBoard().currentPlayer.isWhite()) {
            System.out.println("WHITE " + "THINKING with depth = " + this.searchDepth);
        }else{
            System.out.println("BLACK " + "THINKING with depth = " + this.searchDepth);
        }
        int numMoves = 100 / mGame.getBoard().currentPlayer.Legal_moves().size();
        int all = 0;

        Move BestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValues = Integer.MAX_VALUE;

        int currentValue;

        Game simulatingGame = mGame.copy();

        Player player = mGame.getBoard().currentPlayer;

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for(final Move move : simulatingGame.getBoard().currentPlayer.Legal_moves()){
            simulatingGame = mGame;
            simulatingGame = simulatingGame.GetGameAfterMove(move);


            currentValue = simulatingGame.getBoard().currentPlayer.isWhite() ?
                    max(simulatingGame,this.searchDepth-1,alpha,beta):
                    min(simulatingGame,this.searchDepth-1,alpha,beta);

            if(player.isWhite() && currentValue > highestSeenValue){
                highestSeenValue = currentValue;
                System.out.println("WHITE score: "+highestSeenValue);
                BestMove = move;

            }if(!player.isWhite() && currentValue < lowestSeenValues){
                lowestSeenValues = currentValue;
                System.out.println("BLACK score: "+ -lowestSeenValues);
                BestMove = move;

            }
            all+=numMoves;
            System.out.println("Process: "+ all+" %");

        }

       final long executionTime = System.currentTimeMillis() - starTime;
       System.out.println("Time: "+executionTime);
        return BestMove;





    }
    private int max(Game game, final int depth, int alpha, int beta){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            return Evaluate.EvaluateGame(game, depth);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();



        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final int currentValue = max(game.GetGameAfterMove(move), depth-1,alpha, beta);
            //Alpha beta pruning
            alpha = Integer.max(alpha, currentValue);
            if(beta <= alpha){
                break;
            }

            if(currentValue >= highestSeenValue){
                highestSeenValue = currentValue;
            }
        }





        return highestSeenValue;
    }private int min(Game game, final int depth, int alpha, int beta){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            return  Evaluate.EvaluateGame(game,depth);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final int currentValue = min(game.GetGameAfterMove(move), depth-1, alpha, beta);

            //Alpha beta pruning
            beta = Integer.min(beta,currentValue);
            if(beta<=alpha){
                break;
            }
            if(currentValue <= lowestSeenValue){
                lowestSeenValue = currentValue;
            }
        }



        return lowestSeenValue;

    }
    private boolean isEndGameScenario(Board board) {
        return board.currentPlayer.IsInCheckMate() ||
                board.currentPlayer.IsInStaleMate();

    }








}
