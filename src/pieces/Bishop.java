package pieces;
import Board.Board;
import Board.Spot;
import Board.Move;

import java.io.Serializable;
import java.util.Objects;

public final class Bishop extends Piece implements Serializable {

    public int[][] pos_eval_white;
    public int[][] pos_eval_black;
    public Bishop(int x, int y, String c, int score) {
        super(x, y, c, score);
        this.pos_eval_white = new int[][]{
                                            { -20, -10, -10, -10, -10, -10, -10, -20},
                                            { -10,   0,   0,   0,   0,   0,   0, -10},
                                            { -10,   0,   5,  10,  10,   5,   0, -10},
                                            { -10,   5,   5,  10,  10,   5,   5, -10},
                                            { -10,   0,  10,  10,  10,  10,   0, -10},
                                            { -10,  10,  10,  10,  10,  10,  10, -10},
                                            { -10,   5,   0,   0,   0,   0,   5, -10},
                                            { -20, -10, -10, -10, -10, -10, -10, -20}
                                            };
        this.pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));

        // TODO Auto-generated constructor stub
    }
    public static void possible_moves(Board board, Piece p, String colour){
        int side1;
        int side2;
        int side3;
        int side4;

        int x = p.x;
        int y = p.y;

        Spot old_spot = board.getSpot(x,y);

        int GoToX = 7-p.x;
        int GoToY = 7-p.y;


        // the diagonal distance is the smaller x or y value
        side1 = Math.min(GoToX, GoToY);
        side2 = Math.min(GoToY, x);
        side3 = Math.min(x, y);
        side4 = Math.min(y, GoToX);

        for (int i = 0; i < side1; i++) {
            Spot s = board.getSpot(x + i + 1, y + i + 1);
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
        for (int i = 0; i < side4; i++) {
            Spot s = board.getSpot(x + i + 1, y - i - 1);
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
        for (int i = 0; i < side2; i++) {
            Spot s = board.getSpot(x - i - 1, y + i + 1);
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
        for (int i = 0; i < side3; i++) {
            Spot s = board.getSpot(x - i - 1, y - i - 1);
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
    // Check Logic
    public static void checkLogic(Board board, int x, int y, int GoToX, int GoToY, String colour){
        int side1;
        int side2;
        int side3;
        int side4;

        // the diagonal distance is the smaller x or y value
        side1 = Math.min(GoToX, GoToY);
        side2 = Math.min(GoToY, x);
        side3 = Math.min(x, y);
        side4 = Math.min(y, GoToX);

        for (int i = 0; i < side1; i++) {
            Spot s = board.getSpot(x + i + 1, y + i + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }
        }
        for (int i = 0; i < side4; i++) {
            Spot s = board.getSpot(x + i + 1, y - i - 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }
        }
        for (int i = 0; i < side2; i++) {
            Spot s = board.getSpot(x - i - 1, y + i + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
            if (s.isOccupied()) {
                break;
            }

        }
        for (int i = 0; i < side3; i++) {
            Spot s = board.getSpot(x - i - 1, y - i - 1);
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