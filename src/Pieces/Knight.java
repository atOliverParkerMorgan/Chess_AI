package Pieces;

import Board.Board;

import java.io.Serializable;
import java.util.Objects;
import Board.*;

public final class Knight extends Piece implements Serializable {

    private final static double[][] POS_EVAL_WHITE = new double[][]{
            {-5,-4, -3, -3, -3, -3,-4,-5},
            {-4,-2,  0,  0,  0,  0,-2,-4},
            {-3, 0,  1,1.5,1.5,  1, 0,-3},
            {-3,.5,1.5,  2,  2,1.5,.5,-3},
            {-3, 0,1.5,  2,  2,1.5, 0,-3},
            {-3,.5,  1,1.5,1.5,  1,.5,-3},
            {-4,-2,  0, .5, .5,  0,-2,-4},
            {-5,-4, -3, -3, -3, -3,-4,-5}
    };

    private final static double[][] POS_EVAL_BLACK = Piece.reverseArray(Objects.requireNonNull(Piece.arrayClone(POS_EVAL_WHITE)));
    public Knight(int x, int y, String c, int score) {
        super(x, y, c,score, POS_EVAL_WHITE, POS_EVAL_BLACK);
    }
    public static void possibleMoves(Board board, Piece p, String colour){
        int x = p.getX();
        int y = p.getY();

        Spot old_spot = board.getSpot(x,y);

        if (x + 2 <= 7 && y + 1 <= 7) {
            if (board.getSpot(x + 2, y + 1).isOccupied()) {
                if (!board.getSpot(x + 2, y + 1).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x + 2, y + 1);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }

            } else {
                Spot s = board.getSpot(x + 2, y + 1);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x + 1 <= 7 && y + 2 <= 7) {
            if (board.getSpot(x + 1, y + 2).isOccupied()) {
                if (!board.getSpot(x + 1, y + 2).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x + 1, y + 2);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }

            } else {
                Spot s = board.getSpot(x + 1, y + 2);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x - 2 >= 0 && y - 1 >= 0) {
            if (board.getSpot(x - 2, y - 1).isOccupied()) {
                if (!board.getSpot(x - 2, y - 1).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x - 2, y - 1);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x - 2, y - 1);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x - 1 >= 0 && y - 2 >= 0) {
            if (board.getSpot(x - 1, y - 2).isOccupied()) {
                if (!board.getSpot(x - 1, y - 2).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x - 1, y - 2);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x - 1, y - 2);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x + 2 <= 7 && y - 1 >= 0) {
            if (board.getSpot(x + 2, y - 1).isOccupied()) {
                if (!board.getSpot(x + 2, y - 1).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x + 2, y - 1);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x + 2, y - 1);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x - 1 >= 0 && y + 2 <= 7) {
            if (board.getSpot(x - 1, y + 2).isOccupied()) {
                if (!board.getSpot(x - 1, y + 2).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x - 1, y + 2);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x - 1, y + 2);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x + 1 <= 7 && y - 2 >= 0) {
            if (board.getSpot(x + 1, y - 2).isOccupied()) {
                if (!board.getSpot(x + 1, y - 2).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x + 1, y - 2);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x + 1, y - 2);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x - 2 >= 0 && y + 1 <= 7) {
            if (board.getSpot(x - 2, y + 1).isOccupied()) {
                if (!board.getSpot(x - 2, y + 1).piece.getCategory().contains(colour)) {
                    Spot s = board.getSpot(x - 2, y + 1);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Spot s = board.getSpot(x - 2, y + 1);
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
    }

    //Check Logic
    public static void checkLogic(Board board, int x, int y, String colour){
        if (x + 2 <= 7 && y + 1 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x + 2, y + 1).isValidForBlackKing = false;
            }else{
                board.getSpot(x + 2, y + 1).isValidForWhiteKing = false;
            }

        }
        if (x + 1 <= 7 && y + 2 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x + 1, y + 2).isValidForBlackKing = false;
            }else{
                board.getSpot(x + 1, y + 2).isValidForWhiteKing = false;
            }
        }
        if (x - 2 >= 0 && y - 1 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x - 2, y - 1).isValidForBlackKing = false;
            }else{
                board.getSpot(x - 2, y - 1).isValidForWhiteKing = false;
            }
        }
        if (x - 1 >= 0 && y - 2 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x - 1, y - 2).isValidForBlackKing = false;
            }else{
                board.getSpot(x - 1, y - 2).isValidForWhiteKing = false;
            }
        }
        if (x + 2 <= 7 && y - 1 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x + 2, y - 1).isValidForBlackKing = false;
            }else{
                board.getSpot(x + 2, y - 1).isValidForWhiteKing = false;
            }
        }
        if (x - 1 >= 0 && y + 2 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x - 1, y + 2).isValidForBlackKing = false;
            }else{
                board.getSpot(x - 1, y + 2).isValidForWhiteKing = false;
            }
        }
        if (x + 1 <= 7 && y - 2 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x + 1, y - 2).isValidForBlackKing = false;
            }else{
                board.getSpot(x + 1, y - 2).isValidForWhiteKing = false;
            }
        }
        if (x - 2 >= 0 && y + 1 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x - 2, y + 1).isValidForBlackKing = false;
            }else{
                board.getSpot(x - 2, y + 1).isValidForWhiteKing = false;
            }
        }
    }

}