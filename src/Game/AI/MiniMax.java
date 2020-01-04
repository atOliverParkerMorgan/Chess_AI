package Game.AI;

import Board.Board;
import Board.Move;
import Game.Game;
import Game.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class MiniMax {
    private int searchDepth;
    private int searchTiles;
    private List<int[]> nullMoveHeuristicCORD;
    private long timeMove = 0;
    private int ab;
    public boolean UI;
    private int timeLimit;

    public MiniMax(int depth, boolean UI, int timeLimit){
        this.searchDepth = depth;
        this.timeLimit = timeLimit; //the max time the AI is allowed to think
        this.UI = UI;

        this.searchTiles = 0;
        this.nullMoveHeuristicCORD = new ArrayList<>();
        this.timeMove = 0;
        this.ab = 0;

    }


    public Move getBestMove(final Game mGame, boolean nullHeuristic) {
        // UI
        if(UI) {
            if (mGame.getBoard().currentPlayer.isWhite()) {
                System.out.println("WHITE " + "THINKING with depth = " + this.searchDepth);
            } else {
                System.out.println("BLACK " + "THINKING with depth = " + this.searchDepth);
            }
        }
        // percentage
        int numMoves = 100 / mGame.getBoard().currentPlayer.Legal_moves().size();
        int all = 0;

        Move BestMove = null;

        // these values have to be change during the first miniMax loop
        double highestSeenValue = Integer.MIN_VALUE;
        double lowestSeenValues = Integer.MAX_VALUE;

        double currentValue;

        Game simulatingGame = mGame.copy();

        Player player = mGame.getBoard().currentPlayer;

        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;

        Move[] sortedMoves;
        if(searchDepth>2) {
            sortedMoves = MoveOrdering.orderMoves(simulatingGame.copy());
        }else {
            sortedMoves = simulatingGame.getBoard().currentPlayer.Legal_moves().toArray(new Move[0]);
        }

        // time limit started
        final long starTime = System.currentTimeMillis();

        for(final Move move : sortedMoves){
            simulatingGame = mGame;
            simulatingGame = simulatingGame.getGameAfterMove(move);


            currentValue = simulatingGame.getBoard().currentPlayer.isWhite() ?
                    max(simulatingGame,this.searchDepth-1,alpha,beta, nullHeuristic):
                    min(simulatingGame,this.searchDepth-1,alpha,beta, nullHeuristic);


            if(player.isWhite() && currentValue > highestSeenValue){
                highestSeenValue = currentValue;
                if(UI){System.out.println("WHITE score: "+highestSeenValue);}
                BestMove = move;

            }if(!player.isWhite() && currentValue < lowestSeenValues){
                lowestSeenValues = currentValue;
                if(UI){System.out.println("BLACK score: "+ -lowestSeenValues);}
                BestMove = move;

            }
            if(UI){
                all+=numMoves;
                System.out.println("Process: "+ all+" %");
            }
            if(System.currentTimeMillis() - starTime>=timeLimit){
                if(UI){System.out.println("Time: "+ (System.currentTimeMillis() - starTime));}
                return BestMove;
            }

        }
       if(UI) {
           final long executionTime = System.currentTimeMillis() - starTime;
           System.out.println("Time: "+executionTime);
           // System.out.println("Move Time: "+this.timeMove+" ( "+this.timeMove/(executionTime/100)+"%"+" )"); // in depth one process so fast that execution time is zero so Error / by zero is called
           // System.out.println("AlphaBeta Cuts: "+ab);
           // System.out.println("searched Tiles: "+searchTiles);
           // System.out.println("searches per a second: "+ searchTiles/(executionTime/1000));
       }

       return BestMove;





    }
    private double max(Game game, final int depth, double alpha, double beta, boolean nullHeuristic){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            searchTiles++;
            return Evaluate.EvaluateGame(game, depth);
        }
        double highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();

        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){
            final long starTime = System.currentTimeMillis();
            Game newGame = game.getGameAfterMove(move);
            this.timeMove+= System.currentTimeMillis() - starTime;

            final double currentValue = min(newGame, depth-1,alpha, beta, nullHeuristic);

            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if(beta <= alpha ){
                ab++;
                break;
            }

        }


        return highestSeenValue;
    }private double min(Game game, final int depth, double alpha, double beta, boolean nullHeuristic){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            searchTiles++;
            return  Evaluate.EvaluateGame(game,depth);
        }

        double lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final long starTime = System.currentTimeMillis();
            Game newGame = game.getGameAfterMove(move);
            this.timeMove+= System.currentTimeMillis() - starTime;

            final double currentValue = max(newGame, depth-1, alpha, beta, nullHeuristic);

            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);
            if(beta <= alpha) {
                ab++;
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
