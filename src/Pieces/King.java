package Pieces;

import Board.Board;
import Board.Spot;
import Board.Move;
import Game.Game;
import java.io.Serializable;
import java.util.Objects;

public final class King extends Piece implements Serializable {
    public boolean castling_r;
    public boolean castling_l;
    public boolean castling_k;
    public boolean castled;
    private final static double[][] pos_eval_white = new double[][]{
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-2,-3,-3,-4,-4,-3,-3,-2},
            {-1,-2,-2,-2,-2,-2,-2,-1},
            { 2, 2, 0, 0, 0, 0, 2, 2},
            { 2, 3, 1, 0, 0, 1, 3, 2}
    };

    private final static double[][] pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));



    public King(int x, int y, String c, int score) {
        super(x, y, c,score,pos_eval_white,pos_eval_black);
        this.castling_r = true;
        this.castling_l = true;
        this.castling_k = true;
        this.castled = false;

    }

    public static void possible_moves(Board board, Piece p, String colour){
        int x = p.getX();
        int y = p.getY();

        Spot old_spot = board.getSpot(x,y);

        int GoToX = 7 - x;
        int GoToY = 7 - y;
        int side1;
        int side2;
        int side3;
        int side4;

        side1 = Math.min(GoToX, GoToY);
        side2 = Math.min(GoToY, x);
        side3 = Math.min(x, y);
        side4 = Math.min(y, GoToX);

        // updating board check values
        checkLogic(board,x,y,"white");
        checkLogic(board,x,y,"black");
        if (GoToX > 0) {
            Spot s = board.getSpot(x + 1, y);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                // castling

                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
                King k = (King) p;
                if(k.castling_k) {
                    Spot s2 = board.getSpot(x + 2, y);
                    //checking if the spot is occupied
                    if (k.castling_r && !s2.isOccupied()) {
                        Game.create_check_map(board);
                        Spot current = board.getSpot(k.getX(),k.getY());
                        // checking if the castling would be valid
                        if (colour.equals("white") && s.isValid_for_white_king && s2.isValid_for_white_king && current.isValid_for_white_king ||
                                colour.equals("black") && s.isValid_for_black_king && s2.isValid_for_black_king && current.isValid_for_black_king) {
                            move = new Move(s2,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }
                }
            }
        }
        if (GoToY > 0) {
            Spot s = board.getSpot(x, y + 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (x > 0) {
            Spot s = board.getSpot(x - 1, y);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                // castling

                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
                King k = (King) p;
                if(k.castling_k) {
                    Spot s2 = board.getSpot(x-2,y);
                    //checking if the spot is occupied
                    if(k.castling_l && !s2.isOccupied()){
                        // checking if the castling would be valid
                        Game.create_check_map(board);
                        Spot current = board.getSpot(k.getX(),k.getY());
                        // checking if the castling would be valid
                        if (colour.equals("white") && s.isValid_for_white_king && s2.isValid_for_white_king && current.isValid_for_white_king ||
                                colour.equals("black") && s.isValid_for_black_king && s2.isValid_for_black_king && current.isValid_for_black_king) {
                            move = new Move(s2,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }
                }
            }

        }
        if (y > 0) {
            Spot s = board.getSpot(x, y - 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (side1 > 0) {
            Spot s = board.getSpot(x + 1, y + 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (side4 > 0) {
            Spot s = board.getSpot(x + 1, y - 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }
        if (side2 > 0) {
            Spot s = board.getSpot(x - 1, y + 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }

        }
        if (side3 > 0) {
            Spot s = board.getSpot(x - 1, y - 1);
            if (s.isOccupied()) {
                if (!s.piece.getCategory().contains(colour)) {
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }
            } else {
                Move move = new Move(s,old_spot);
                p.all_possible_moves.add(move);
            }
        }


    }

    public static void checkLogic (Board board, int x, int y, String colour){
        int GoToX;
        int GoToY;

        int side1;
        int side2;
        int side3;
        int side4;

        GoToX = 7 - x;
        GoToY = 7 - y;

        side1 = Math.min(GoToX, GoToY);
        side2 = Math.min(GoToY, x);
        side3 = Math.min(x, y);
        side4 = Math.min(y, GoToX);
        if(GoToX>0){
            Spot s = board.getSpot(x + 1, y);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }
        if(GoToY>0) {
            Spot s = board.getSpot(x, y + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }
        if(x>0) {
            Spot s = board.getSpot(x - 1, y);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }

        }
        if(y>0){
            Spot s = board.getSpot(x, y - 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }
        if(side1>0){
            Spot s = board.getSpot(x + 1, y + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }
        if(side4>0){
            Spot s = board.getSpot(x + 1, y - 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }
        if(side2>0){
            Spot s = board.getSpot(x - 1, y + 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }

        }
        if(side3>0){
            Spot s = board.getSpot(x - 1, y - 1);
            if(colour.equals("white")) {
                s.isValid_for_black_king = false;
            }else{
                s.isValid_for_white_king = false;
            }
        }

    }



}
