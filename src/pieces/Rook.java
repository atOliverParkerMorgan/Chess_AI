package pieces;


import Board.Board;
import Board.Spot;
import Board.Move;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Rook extends Piece implements Serializable {

    private final static double[][] pos_eval_white = new double[][]{
            {  0, 0,  0,  0,  0,  0,  0,   0},
            { .5, 1,  1,  1,  1,  1,  1,  .5},
            {-.5, 0,  0,  0,  0,  0,  0, -.5},
            {-.5, 0,  0,  0,  0,  0,  0, -.5},
            {-.5, 0,  0,  0,  0,  0,  0, -.5},
            {-.5, 0,  0,  0,  0,  0,  0, -.5},
            {-.5, 0,  0,  0,  0,  0,  0, -.5},
            {  0, 0,  0, .5, .5,  0,  0,   0}
    };

    private final static double[][] pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));
    public Rook(int x, int y, String c, int score) {
        super(x, y, c,score,pos_eval_white,pos_eval_black);
    }
    public static void possible_moves(Board board, Piece p, String colour) {
        int x = p.x;
        int y = p.y;

        Spot old_spot = board.getSpot(x,y);

        int GoToX1 = 7 - p.x;
        int GoToY1 = 7 - p.y;

        for (int i = 0; i < GoToX1; i++) {
            Spot s = board.getSpot(x + i + 1, y);
            if(s.isOccupied()){
                if(s.piece.category.contains(colour)){
                    break;
                }else{
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                    break;
                }

            }else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        for (int i = 0; i < GoToY1; i++) {
            Spot s = board.getSpot(x, y + i + 1);
            if(s.isOccupied()){
                if(s.piece.category.contains(colour)){
                    break;
                }else{
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                    break;
                }

            }else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        for (int i = 0; i < x; i++) {
            Spot s = board.getSpot(x - i - 1, y);
            if(s.isOccupied()){
                if(s.piece.category.contains(colour)){
                    break;
                }else{
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                    break;
                }

            }else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }

        }
        for (int i = 0; i < y; i++) {
            Spot s = board.getSpot(x, y - i - 1);
            if(s.isOccupied()){
                if(s.piece.category.contains(colour)){
                    break;
                }else{
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                    break;
                }

            }else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
    }
    // Check logic
    public static void checkLogic(Board board, int x, int y, int goToX1, int goToY1, String colour) {
        for (int i = 0; i < goToX1; i++) {
            Spot s = board.getSpot(x + i + 1, y);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }
        }
        for (int i = 0; i < goToY1; i++) {
            Spot s = board.getSpot(x, y + i + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }
        }
        for (int i = 0; i < x; i++) {
            Spot s = board.getSpot(x - i - 1, y);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }

        }
        for (int i = 0; i < y; i++) {
            Spot s = board.getSpot(x, y - i - 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }
        }
    }



}