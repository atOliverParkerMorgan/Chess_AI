package pieces;

import Board.Board;

import java.io.Serializable;
import java.util.Objects;
import Board.*;

public final class Knight extends Piece implements Serializable {

    public int[][] pos_eval_white;
    public int[][] pos_eval_black;
    public Knight(int x, int y, String c, int score) {
        super(x, y, c, score);
        this.pos_eval_white = new int[][]{
                                        {-50, -40, -30, -30, -30, -30, -40, -50},
                                        {-40, -20,   0,   0,   0,   0, -20, -40},
                                        {-30,   0,  10,  15,  15,  10,   0, -30},
                                        {-30,   5,  15,  20,  20,  15,   5, -30},
                                        {-30,   0,  15,  20,  20,  15,   0, -30},
                                        {-30,   5,  10,  15,  15,  10,   5, -30},
                                        {-40, -20,   0,   5,   5,   0, -20, -40},
                                        {-50, -40, -30, -30, -30, -30, -40, -50}
                                        };
        this.pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));

        // TODO Auto-generated constructor stub
    }
    public static void possible_moves(Board board, Piece p, String colour){
        int x = p.x;
        int y = p.y;

        Spot old_spot = board.getSpot(x,y);

        if (x + 2 <= 7 && y + 1 <= 7) {
            if (board.getSpot(x + 2, y + 1).isOccupied()) {
                if (!board.getSpot(x + 2, y + 1).piece.category.contains(colour)) {
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
                if (!board.getSpot(x + 1, y + 2).piece.category.contains(colour)) {
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
                if (!board.getSpot(x - 2, y - 1).piece.category.contains(colour)) {
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
                if (!board.getSpot(x - 1, y - 2).piece.category.contains(colour)) {
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
                if (!board.getSpot(x + 2, y - 1).piece.category.contains(colour)) {
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
                if (!board.getSpot(x - 1, y + 2).piece.category.contains(colour)) {
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
                if (!board.getSpot(x + 1, y - 2).piece.category.contains(colour)) {
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
                if (!board.getSpot(x - 2, y + 1).piece.category.contains(colour)) {
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
                board.getSpot(x + 2, y + 1).isValid_for_black_king = false;
            }else{
                board.getSpot(x + 2, y + 1).isValid_for_white_king = false;
            }

        }
        if (x + 1 <= 7 && y + 2 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x + 1, y + 2).isValid_for_black_king = false;
            }else{
                board.getSpot(x + 1, y + 2).isValid_for_white_king = false;
            }
        }
        if (x - 2 >= 0 && y - 1 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x - 2, y - 1).isValid_for_black_king = false;
            }else{
                board.getSpot(x - 2, y - 1).isValid_for_white_king = false;
            }
        }
        if (x - 1 >= 0 && y - 2 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x - 1, y - 2).isValid_for_black_king = false;
            }else{
                board.getSpot(x - 1, y - 2).isValid_for_white_king = false;
            }
        }
        if (x + 2 <= 7 && y - 1 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x + 2, y - 1).isValid_for_black_king = false;
            }else{
                board.getSpot(x + 2, y - 1).isValid_for_white_king = false;
            }
        }
        if (x - 1 >= 0 && y + 2 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x - 1, y + 2).isValid_for_black_king = false;
            }else{
                board.getSpot(x - 1, y + 2).isValid_for_white_king = false;
            }
        }
        if (x + 1 <= 7 && y - 2 >= 0) {
            if(colour.equals("white")){
                board.getSpot(x + 1, y - 2).isValid_for_black_king = false;
            }else{
                board.getSpot(x + 1, y - 2).isValid_for_white_king = false;
            }
        }
        if (x - 2 >= 0 && y + 1 <= 7) {
            if(colour.equals("white")){
                board.getSpot(x - 2, y + 1).isValid_for_black_king = false;
            }else{
                board.getSpot(x - 2, y + 1).isValid_for_white_king = false;
            }
        }
    }

}