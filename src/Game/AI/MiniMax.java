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

    private static final int timeLimit = 30000;

    public MiniMax(int depth){
        this.searchDepth = depth;
        this.searchTiles = 0;
        this.nullMoveHeuristicCORD = new ArrayList<>();

    }

    public void nullMoveHeuristic(final Game mGame){
        Game simulatingGame = mGame.copy();
        simulatingGame.getBoard().currentPlayer = simulatingGame.getBoard().currentPlayer.getOpponent();
        int depth = this.searchDepth;
        this.searchDepth = depth-2;
        getBestMove(mGame, true);
        this.searchDepth = depth;
    }

    public Move getBestMove(final Game mGame, boolean nullHeuristic) {
        // UI
        if(mGame.getBoard().currentPlayer.isWhite()) {
            System.out.println("WHITE " + "THINKING with depth = " + this.searchDepth);
        }else{
            System.out.println("BLACK " + "THINKING with depth = " + this.searchDepth);
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

       Move[] sortedMoves = MoveOrdering.orderMoves(simulatingGame.copy());

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
                System.out.println("WHITE score: "+highestSeenValue);
                BestMove = move;

            }if(!player.isWhite() && currentValue < lowestSeenValues){
                lowestSeenValues = currentValue;
                System.out.println("BLACK score: "+ -lowestSeenValues);
                BestMove = move;

            }
            all+=numMoves;
            System.out.println("Process: "+ all+" %");
            if(System.currentTimeMillis() - starTime>=timeLimit){
                System.out.println("Time: "+ (System.currentTimeMillis() - starTime));
                return BestMove;
            }

        }

       final long executionTime = System.currentTimeMillis() - starTime;
       System.out.println("Time: "+executionTime);
       System.out.println(searchTiles);
       return BestMove;





    }
    private double max(Game game, final int depth, double alpha, double beta, boolean nullHeuristic){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            searchTiles++;
            return Evaluate.EvaluateGame(game, depth);
        }
        double highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();


        int x = 0;
        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final double currentValue = min(game.getGameAfterMove(move), depth-1,alpha, beta, nullHeuristic);

            highestSeenValue = Math.max(currentValue, highestSeenValue);
            alpha = Math.max(alpha, highestSeenValue);

            if(beta <= alpha || !nullHeuristic && nullMoveHeuristicCORD.contains(new int[]{x, depth})){
                if(nullHeuristic){
                    this.nullMoveHeuristicCORD.add(new int[]{x,depth});
                }
                break;
            }
            x++;


        }





        return highestSeenValue;
    }private double min(Game game, final int depth, double alpha, double beta, boolean nullHeuristic){
        if(depth == 0||isEndGameScenario(game.getBoard())){
            searchTiles++;
            return  Evaluate.EvaluateGame(game,depth);
        }

        double lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        int x = 0;
        for(final Move move: original.getBoard().currentPlayer.Legal_moves()){

            final double currentValue = max(game.getGameAfterMove(move), depth-1, alpha, beta, nullHeuristic);

            lowestSeenValue = Math.min(currentValue, lowestSeenValue);
            beta = Math.min(beta, lowestSeenValue);
            if(beta <= alpha || !nullHeuristic && nullMoveHeuristicCORD.contains(new int[]{x, depth})){
                if(nullHeuristic){
                    this.nullMoveHeuristicCORD.add(new int[]{x,depth});
                }
                break;
            }
            x++;

        }



        return lowestSeenValue;

    }
    private boolean isEndGameScenario(Board board) {
        return board.currentPlayer.IsInCheckMate() ||
                board.currentPlayer.IsInStaleMate();

    }








}
