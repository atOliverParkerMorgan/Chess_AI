package Pieces;

import Board.Board;
import Board.Spot;
import Board.Move;
import Game.Game;
import java.io.Serializable;
import java.util.Objects;

public final class King extends Piece implements Serializable {
    public boolean castlingR;
    public boolean castlingL;
    public boolean castlingK;
    public boolean castled;
    private final static double[][] POS_EVAL_WHITE = new double[][]{
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-3,-4,-4,-5,-5,-4,-4,-3},
            {-2,-3,-3,-4,-4,-3,-3,-2},
            {-1,-2,-2,-2,-2,-2,-2,-1},
            { 2, 2, 0, 0, 0, 0, 2, 2},
            { 2, 3, 1, 0, 0, 1, 3, 2}
    };

    private final static double[][] POS_EVAL_BLACK = Piece.reverseArray(Objects.requireNonNull(Piece.arrayClone(POS_EVAL_WHITE)));



    public King(int x, int y, String c, int score) {
        super(x, y, c,score, POS_EVAL_WHITE, POS_EVAL_BLACK);
        this.castlingR = true;
        this.castlingL = true;
        this.castlingK = true;
        this.castled = false;

    }

    public static void possibleMoves(Board board, Piece p, String colour){
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
                if(k.castlingK) {
                    Spot s2 = board.getSpot(x + 2, y);
                    //checking if the spot is occupied
                    if (k.castlingR && !s2.isOccupied()) {
                        Game.createCheckMap(board);
                        Spot current = board.getSpot(k.getX(),k.getY());
                        // checking if the castling would be valid
                        if (colour.equals("white") && s.isValidForWhiteKing && s2.isValidForWhiteKing && current.isValidForWhiteKing ||
                                colour.equals("black") && s.isValidForBlackKing && s2.isValidForBlackKing && current.isValidForBlackKing) {
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
                if(k.castlingK) {
                    Spot s2 = board.getSpot(x-2,y);
                    //checking if the spot is occupied
                    if(k.castlingL && !s2.isOccupied()){
                        // checking if the castling would be valid
                        Game.createCheckMap(board);
                        Spot current = board.getSpot(k.getX(),k.getY());
                        // checking if the castling would be valid
                        if (colour.equals("white") && s.isValidForWhiteKing && s2.isValidForWhiteKing && current.isValidForWhiteKing ||
                                colour.equals("black") && s.isValidForBlackKing && s2.isValidForBlackKing && current.isValidForBlackKing) {
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
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }
        if(GoToY>0) {
            Spot s = board.getSpot(x, y + 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }
        if(x>0) {
            Spot s = board.getSpot(x - 1, y);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }

        }
        if(y>0){
            Spot s = board.getSpot(x, y - 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }
        if(side1>0){
            Spot s = board.getSpot(x + 1, y + 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }
        if(side4>0){
            Spot s = board.getSpot(x + 1, y - 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }
        if(side2>0){
            Spot s = board.getSpot(x - 1, y + 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }

        }
        if(side3>0){
            Spot s = board.getSpot(x - 1, y - 1);
            if(colour.equals("white")) {
                s.isValidForBlackKing = false;
            }else{
                s.isValidForWhiteKing = false;
            }
        }

    }



}
