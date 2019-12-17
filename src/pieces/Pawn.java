package pieces;

import Board.Spot;
import Board.Move;
import Board.Board;
import Game.Player;

import java.io.Serializable;
import java.util.Objects;

public final class Pawn extends Piece implements Serializable {

    public boolean first;
    public boolean en_passe;
    public int[][] pos_eval_white;
    public int[][] pos_eval_black;
    public Pawn(int x, int y, String c, int score) {
        super(x, y, c,score);

        this.pos_eval_white = new int[][]{
                                            { 0,   0,   0,   0,   0,   0,   0,   0},
                                            {50,  50,  50,  50,  50,  50,  50,  50},
                                            {10,  10,  20,  30,  30,  20,  10,  10},
                                            { 5,   5,  10,  25,  25,  10,   5,   5},
                                            { 0,   0,   0,  20,  20,   0,   0,   0},
                                            { 5, - 5, -10,   0,   0, -10, - 5,   5},
                                            { 5,  10,  10, -20, -20,  10,  10,   5},
                                            { 0,   0,   0,   0,   0,   0,   0,   0}
                                            };
        this.pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));
        this.en_passe = false;
        this.first = true;
        // TODO Auto-generated constructor stub
    }

    public static void possible_moves(Board board, Piece p, String colour){
        int x = p.x;
        int y = p.y;

        Spot old_spot = board.getSpot(x,y);

        Spot s;
        Spot s2;
        Pawn pawn = (Pawn) p;

        if(colour.equals("white")) {
            final int cord_y = Player.getCord_white_y();
            int en_passe_y;
            int end_board_y;
            int minus;

            if(cord_y == 0){

                en_passe_y = 4;
                end_board_y = 7;
                minus = 1;
            }else{
                en_passe_y = 3;
                end_board_y = 0;
                minus = -1;

            }

            //EN-PASSE
            if (y == en_passe_y) {
                if (x != 7) {
                    if (board.getSpot(x + 1, y).isOccupied()) {
                        if (board.getSpot(x + 1, y).piece.category.equals("Pawns_black")) {
                            Pawn pawn_enpasse = (Pawn) board.getSpot(x + 1, y).piece;
                            if (pawn_enpasse.en_passe) {
                                Spot s_enpasse = board.getSpot(x + 1, y + minus);
                                Move move = new Move(s_enpasse,old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }
                }
                if (x != 0) {
                    if (board.getSpot(x - 1, y).isOccupied()) {
                        if (board.getSpot(x - 1, y).piece.category.equals("Pawns_black")) {
                            Pawn pawn_enpasse = (Pawn) board.getSpot(x - 1, y).piece;
                            if (pawn_enpasse.en_passe) {
                                Spot s_enpasse = board.getSpot(x - 1, y + minus);
                                Move move = new Move(s_enpasse,old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }
                }

            }

            if (y != end_board_y) {
                if (!board.getSpot(x, y + minus).isOccupied()) {
                    s = board.getSpot(x, y + minus);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }

                if (pawn.first) {
                    if(end_board_y==0){
                        if (y > 1) {
                            s = board.getSpot(x, y + 2 * minus);
                            s2 = board.getSpot(x,y + minus);
                            if(!s.isOccupied() && !s2.isOccupied()) {
                                Move move = new Move(s,old_spot);
                                p.all_possible_moves.add(move);
                            }

                        }
                    }else {
                        if (y < 6) {
                            s = board.getSpot(x, y + 2 * minus);
                            s2 = board.getSpot(x, y + minus);
                            if (!s.isOccupied() && !s2.isOccupied()) {
                                Move move = new Move(s, old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }

                }
                if (x != 0) {
                    s = board.getSpot(x - 1, y + minus);
                    if (s.isOccupied()) {
                        if (!s.piece.category.contains("white")) {
                            Move move = new Move(s,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }
                }
                if (x != 7) {
                    s = board.getSpot(x + 1, y + minus);
                    if (s.isOccupied()) {
                        if (!s.piece.category.contains("white")) {
                            Move move = new Move(s,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }

                }
            }
        }else if(colour.equals("black")){
            final int cord_y = Player.getCord_white_y();

            int en_passe_y;
            int end_board_y;
            int minus;

            if(cord_y == 0){

                en_passe_y = 3;
                end_board_y = 0;
                minus = -1;
            }else{
                en_passe_y = 4;
                end_board_y = 7;
                minus = 1;

            }

            //EN-PASSE
            if(y==en_passe_y){
                if(x!=7) {
                    if (board.getSpot(x + 1, y).isOccupied()) {
                        if (board.getSpot(x + 1, y).piece.category.equals("Pawns_white")) {
                            Pawn pawn_enpasse = (Pawn) board.getSpot(x + 1, y).piece;
                            if (pawn_enpasse.en_passe) {
                                Spot s_enpasse = board.getSpot(x + 1, y + minus);
                                Move move = new Move(s_enpasse,old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }
                }
                if(x!=0) {
                    if (board.getSpot(x - 1, y).isOccupied()) {
                        if (board.getSpot(x - 1, y).piece.category.equals("Pawns_white")) {
                            Pawn pawn_enpasse = (Pawn) board.getSpot(x - 1, y).piece;
                            if (pawn_enpasse.en_passe) {
                                Spot s_enpasse = board.getSpot(x - 1, y + minus);
                                Move move = new Move(s_enpasse,old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }
                }

            }
            if(y!=0){
                if(!board.getSpot(x,y + minus).isOccupied()) {
                    s = board.getSpot(x, y + minus);
                    Move move = new Move(s,old_spot);
                    p.all_possible_moves.add(move);
                }

                if(pawn.first) {
                    if(end_board_y==7){
                        if (y < 6) {
                            s = board.getSpot(x, y + 2 * minus);
                            s2 = board.getSpot(x,y + minus);
                            if(!s.isOccupied() && !s2.isOccupied()) {
                                Move move = new Move(s,old_spot);
                                p.all_possible_moves.add(move);
                            }

                        }
                    }else {
                        if (y > 1) {
                            s = board.getSpot(x, y + 2 * minus);
                            s2 = board.getSpot(x, y + minus);
                            if (!s.isOccupied() && !s2.isOccupied()) {
                                Move move = new Move(s, old_spot);
                                p.all_possible_moves.add(move);
                            }
                        }
                    }

                }if(x!=0) {
                    s = board.getSpot(x - 1, y + minus);
                    if (s.isOccupied()) {
                        if (!s.piece.category.contains("black")) {
                            Move move = new Move(s,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }
                }if(x!=7) {
                    s = board.getSpot(x + 1, y + minus);
                    if (s.isOccupied()) {
                        if (!s.piece.category.contains("black")) {
                            Move move = new Move(s,old_spot);
                            p.all_possible_moves.add(move);
                        }
                    }

                }
            }


        }

    }
    public static void checkLogic(Board board, int x, int y,String colour){


        if(colour.equals("white")){
            final int cord_y = Player.getCord_white_y();
            final int end;
            final int minus;

            if(cord_y==7){
                end = 0;
                minus = -1;
            }else{
                end = 7;
                minus = 1;
            }
            if(y!=end){
                if(x!=7) {
                    Spot s = board.getSpot(x + 1, y + minus);
                    s.isValid_for_black_king = false;
                }if(x!=0) {
                    Spot s2 = board.getSpot(x - 1, y + minus);
                    s2.isValid_for_black_king = false;
                }
            }
        }else{
            final int cord_y = Player.getCord_black_y();
            final int end;
            final int minus;

            if(cord_y==7){
                end = 0;
                minus = -1;
            }else{
                end = 7;
                minus = 1;
            }

            if(y!=end){
                if(x!=7) {
                    Spot s = board.getSpot(x + 1, y + minus);
                    s.isValid_for_white_king = false;
                }if(x!=0){
                    Spot s2 = board.getSpot(x - 1, y + minus);
                    s2.isValid_for_white_king = false;
                }
            }
        }
    }

}
