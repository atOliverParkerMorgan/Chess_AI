package Game.AI;

import Board.Move;
import Game.Game;

final class MoveOrdering {

    static Move[] orderMoves(Game mGame){
        Move[] movesSorted = new Move[mGame.getBoard().currentPlayer.legalMoves().size()];
        double[] moveValues = new double[mGame.getBoard().currentPlayer.legalMoves().size()];
        Game staticGame = mGame.copy();

        int index = 0;
        for(final Move move : mGame.getBoard().currentPlayer.legalMoves()){
            Game simulatingGame = mGame.getGameAfterMove(move);
            moveValues[index] = Evaluate.evaluateGame(simulatingGame,1);
        }
        if(mGame.getBoard().currentPlayer.isWhite()){
            for (int i = 0; i <movesSorted.length ; i++) {


                double max = moveValues[0];
                int maxIndex = 0;
                for (int counter = 1; counter < moveValues.length; counter++) {
                    if (moveValues[counter] > max) {
                        max = moveValues[counter];
                        maxIndex = counter;
                    }
                }
                moveValues[maxIndex] = Integer.MIN_VALUE;
                movesSorted[i] = staticGame.getBoard().currentPlayer.legalMoves().get(maxIndex);
            }

        }else {
            for (int i = 0; i <movesSorted.length ; i++) {


                double min = moveValues[0];
                int minIndex = 0;
                for (int counter = 1; counter < moveValues.length; counter++) {
                    if (moveValues[counter] < min) {
                        min = moveValues[counter];
                        minIndex = counter;
                    }
                }
                moveValues[minIndex] = Integer.MAX_VALUE;
                movesSorted[i] = staticGame.getBoard().currentPlayer.legalMoves().get(minIndex);
            }
        }

        return movesSorted;
    }

}
