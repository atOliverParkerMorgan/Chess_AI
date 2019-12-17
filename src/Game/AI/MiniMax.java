package Game.AI;


import Board.Board;
import Board.Move;
import Game.Game;
import Game.Player;

public class MiniMax implements MoveStrategy{
    private BoardEvaluation boardEvaluator;
    private final int searchDepth;



    public MiniMax(final int searchDepth){
        this.boardEvaluator = new StandardBoardEvaluator();
        this.searchDepth = searchDepth;
    }

    @Override
    public String toString(){
        return "MiniMax";
    }

    @Override
    public Move execute(Game game){

        final long starTime = System.currentTimeMillis();

        Move Best_Move = null;

        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;

        final Game current_game = game.copy();
        Player player = current_game.board.currentPlayer;

        if(game.board.currentPlayer.white) {
            System.out.println("WHITE " + "THINKING with depth = " + this.searchDepth);
        }else{
            System.out.println("BLACK " + "THINKING with depth = " + this.searchDepth);
        }
        int numMoves = 100/game.board.currentPlayer.Legal_moves().size();
        int all = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for(Move move : game.board.currentPlayer.Legal_moves()){
            game = current_game;

            game = game.GetGameAfterMove(move);

            currentValue = game.board.currentPlayer.white ?
                    min(game,this.searchDepth-1,alpha,beta):
                    max(game,this.searchDepth-1,alpha,beta);

            if(player.white && currentValue >= highestSeenValue){
                highestSeenValue = currentValue;
                System.out.println("WHITE score: "+highestSeenValue);

                Game g = game.copy();
                g.MOVE(move,true);

                Best_Move = move;

            }if(!player.white && currentValue <= lowestSeenValue){
                lowestSeenValue = currentValue;
                System.out.println("BLACK score: "+ -lowestSeenValue);

                Game g = game.copy();
                g.MOVE(move,true);

                Best_Move = move;

            }
            all+=numMoves;
            System.out.println("Process: "+ all+" %");
        }

        final long executionTime = System.currentTimeMillis() - starTime;
        System.out.println("Time: "+executionTime);
        return Best_Move;



    }
    private int min(Game game, final int depth, int alpha, int beta){
        if(depth == 0){
            game.board.currentPlayer = game.board.white;
            return this.boardEvaluator.evaluate(game, depth);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        Game original = game.copy();

        for(Move move: original.board.currentPlayer.Legal_moves()){

            final int currentValue = max(game.GetGameAfterMove(move), depth-1,alpha, beta);

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


    private int max(Game game, final int depth, int alpha, int beta){
        if(depth == 0){
            game.board.currentPlayer = game.board.black;
            return this.boardEvaluator.evaluate(game, depth);
        }

        int highestSeenValue = Integer.MIN_VALUE;
        Game original = game.copy();

        for(Move move: original.board.currentPlayer.Legal_moves()){

            final int currentValue = min(game.GetGameAfterMove(move), depth-1, alpha, beta);


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

    }

    private boolean isEndGameScenario(Board board) {
        return board.currentPlayer.IsInCheckMate() ||
                board.currentPlayer.IsInStaleMate();

    }

}
