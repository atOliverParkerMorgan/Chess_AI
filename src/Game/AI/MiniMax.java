package Game.AI;

import Board.Board;
import Board.Move;
import Game.Game;
import Game.Player;

public final class MiniMax {
    private int searchDepth;

    private static final int timeLimit = 90000;

    public MiniMax(int depth){
        this.searchDepth = depth;

    }
    public Move getBestMove(final Game mGame) {

        if(mGame.getBoard().currentPlayer.isWhite()) {
            System.out.println("WHITE " + "THINKING with depth = " + this.searchDepth);
        }else{
            System.out.println("BLACK " + "THINKING with depth = " + this.searchDepth);
        }
        int numMoves = 100 / mGame.getBoard().currentPlayer.Legal_moves().size();
        int all = 0;

        Move BestMove = null;


        double highestSeenValue = Integer.MIN_VALUE;
        double lowestSeenValues = Integer.MAX_VALUE;

        double currentValue;

        Game simulatingGame = mGame.copy();

        Player player = mGame.getBoard().currentPlayer;

        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;

        Move[] sortedMoves = MoveOrdering.orderMoves(simulatingGame.copy());

        // time limit started
        final long starTime = System.currentTimeMillis();

        for(final Move move : sortedMoves){
            simulatingGame = mGame;
            simulatingGame = simulatingGame.getGameAfterMove(move);


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
            if(System.currentTimeMillis() - starTime>=timeLimit&& all>=60){
                System.out.println("Time: "+ (System.currentTimeMillis() - starTime));
                return BestMove;
            }

        }

       final long executionTime = System.currentTimeMillis() - starTime;
       System.out.println("Time: "+executionTime);
       return BestMove;





    }
    private double max(Game game, final int depth, double alpha, double beta){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            return Evaluate.EvaluateGame(game, depth);
        }
        double highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();



        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final double currentValue = min(game.getGameAfterMove(move), depth-1,alpha, beta);

            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if(beta <= alpha){
                break;
            }


        }





        return highestSeenValue;
    }private double min(Game game, final int depth, double alpha, double beta){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            return  Evaluate.EvaluateGame(game,depth);
        }

        double lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final double currentValue = max(game.getGameAfterMove(move), depth-1, alpha, beta);

            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);
            if(beta <= alpha){
                break;
            }

        }



        return lowestSeenValue;

    }
    private boolean isEndGameScenario(Board board) {
        return board.currentPlayer.IsInCheckMate() ||
                board.currentPlayer.IsInStaleMate();

    }








}
