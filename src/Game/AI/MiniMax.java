package Game.AI;

import Board.Move;
import Game.Game;
import Game.Player;

public class MiniMax {
    private int searchDepth;

    public MiniMax(int depth, boolean isWhite){
        this.searchDepth = depth;

    }
    public Move getBestMove(final Game mGame) {
        Move BestMove = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValues = Integer.MAX_VALUE;

        int currentValue;

        Game simulatingGame = mGame.copy();

        Player player = mGame.getBoard().currentPlayer;

        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for(Move move : simulatingGame.getBoard().currentPlayer.Legal_moves()){
            simulatingGame = mGame;
            simulatingGame = simulatingGame.GetGameAfterMove(move);


            currentValue = simulatingGame.getBoard().currentPlayer.isWhite() ?
                    min(simulatingGame,this.searchDepth-1,alpha,beta):
                    max(simulatingGame,this.searchDepth-1,alpha,beta);

            if(player.isWhite() && currentValue > highestSeenValue){
                highestSeenValue = currentValue;
                System.out.println("WHITE score: "+highestSeenValue);
                BestMove = move;

            }if(!player.isWhite() && currentValue < lowestSeenValues){
                lowestSeenValues = currentValue;
                System.out.println("BLACK score: "+ -lowestSeenValues);
                simulatingGame.getBoard().BoardInChars();
                simulatingGame.getBoard().printBoardChars();
                System.out.println();
                BestMove = move;

            }

        }

       // final long executionTime = System.currentTimeMillis() - starTime;
       // System.out.println("Time: "+executionTime);
        return BestMove;





    }
    private int min(Game game, final int depth, int alpha, int beta){
        if(depth == 0){
            return Evaluate.EvaluateGame(game);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();

        original.getBoard().currentPlayer = original.getWhite();


        for(Move move: original.getBoard().currentPlayer.Legal_moves()){

            final int currentValue = max(game.GetGameAfterMove(move), depth-1,alpha, beta);
            //Alpha beta pruning
            beta = Integer.min(beta,currentValue);
            if(beta<=alpha){
               break;
            }
            if(currentValue > highestSeenValue){
                highestSeenValue = currentValue;
            }
        }





        return highestSeenValue;
    }private int max(Game game, final int depth, int alpha, int beta){
        if(depth == 0){
            return  Evaluate.EvaluateGame(game);
        }

        int lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        original.getBoard().currentPlayer = original.getBlack();
        for(Move move: original.getBoard().currentPlayer.Legal_moves()){

            final int currentValue = min(game.GetGameAfterMove(move), depth-1, alpha, beta);

            //Alpha beta pruning
             alpha = Integer.max(alpha, currentValue);
            if(beta <= alpha){
                break;
            }
            if(currentValue < lowestSeenValue){
                lowestSeenValue = currentValue;
            }
        }



        return lowestSeenValue;

    }








}
