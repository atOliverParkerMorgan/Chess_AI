package Game;

import Board.Spot;
import Board.Move;
import Board.Board;
import pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Player implements Serializable {

    public List<Piece> pieces;
    private boolean white;
    private Player opponent;
    private Board board;
    private static int cord_white_y = 0;
    private static int cord_black_y = 7;

    Player(boolean white){
        this.opponent = null;
        this.board = null;

        // seven is bottom zero is top

        this.white = white;
        this.pieces = new ArrayList<>();
    }
    public boolean IsInCheck(){
        King k = this.Get_King();

        assert k != null;
        Spot s = this.board.getSpot(k.x,k.y);

        Game.create_check_map(this.board);

        if(this.white){
           return !s.isValid_for_white_king;
        }else{
           return  !s.isValid_for_black_king;
        }

    }
    public boolean IsInCheckMate(){
        King k = this.Get_King();

        assert k != null;
        Spot s = this.board.getSpot(k.x,k.y);

        Game.create_check_map(this.board);


        if(this.white){
            Game.Possible_moves_white(this.board, this , this.opponent);

            return !s.isValid_for_white_king && this.Number_of_Legal_moves()==0;
        }else{
            Game.Possible_moves_black(this.board,this.opponent , this);

            return  !s.isValid_for_black_king && this.Number_of_Legal_moves()==0;
        }

    }

    public boolean IsInStaleMate(){
        return this.Number_of_Legal_moves()==0;
    }



    public List<Piece> getPieces(){
        return this.pieces;
    }
    private int Number_of_Legal_moves(){
        int total = 0;
        for(Piece p :this.pieces){
            total+=p.all_possible_moves.size();
        }
        return total;
    }
    public List<Move> Legal_moves(){
        List<Move> total = new ArrayList<>();
        for(Piece p :this.pieces){
            total.addAll(p.all_possible_moves);
        }
        return total;
    }

    void initializePieces(){
        final int NUMBER_OF_PAWNS = 8;
        if(this.white) {

            pieces.add(new Rook(0, cord_white_y, "Rook_white",500));
            pieces.add(new Rook(7, cord_white_y, "Rook_white",500));
            pieces.add(new Knight(1, cord_white_y, "Knight_white",300));
            pieces.add(new Knight(6, cord_white_y, "Knight_white",300));
            pieces.add(new Bishop(2, cord_white_y, "Bishop_white",300));
            pieces.add(new Bishop(5, cord_white_y, "Bishop_white",300));
            // queen always has to have her color this will change if black is on index_y zero or seven
            if(cord_white_y==0) {
                pieces.add(new Queen(3, cord_white_y, "Queen_white", 900));
                pieces.add(new King(4, cord_white_y, "King_white", 10000));
            }else{
                pieces.add(new Queen(4, cord_white_y, "Queen_white", 900));
                pieces.add(new King(3, cord_white_y, "King_white", 10000));
            }
            for (int i = 0; i < NUMBER_OF_PAWNS; i++) {
                if(cord_white_y==7) {
                    pieces.add(new Pawn(i, cord_white_y - 1, "Pawns_white", 100));
                }else{
                    pieces.add(new Pawn(i, cord_white_y + 1, "Pawns_white", 100));
                }
            }
        }else {

            pieces.add(new Rook(0, cord_black_y,"Rook_black",500));
            pieces.add(new Rook(7, cord_black_y,"Rook_black",500));
            pieces.add(new Knight(1, cord_black_y,"Knight_black",300));
            pieces.add(new Knight(6, cord_black_y,"Knight_black",300));
            pieces.add(new Bishop(2, cord_black_y,"Bishop_black",300));
            pieces.add(new Bishop(5, cord_black_y,"Bishop_black",300));
            // queen always has to have her color this will change if black is on index_y zero or seven
            if(cord_black_y==7) {
                pieces.add(new Queen(3, cord_black_y, "Queen_black", 900));
                pieces.add(new King(4, cord_black_y, "King_black", 10000));
            }else {
                pieces.add(new Queen(4, cord_black_y, "Queen_black", 900));
                pieces.add(new King(3, cord_black_y, "King_black", 10000));
            }
            for (int i = 0; i < NUMBER_OF_PAWNS; i++) { // draw pawns
                if(cord_black_y==7) {
                    pieces.add(new Pawn(i, cord_black_y - 1, "Pawns_black", 100));
                }else{
                    pieces.add(new Pawn(i, cord_black_y + 1, "Pawns_black", 100));
                }
            }
        }



    }



    public King Get_King(){
        for(Piece p :this.pieces){
           if(p.category.contains("King")){
                return (King) p;
           }
        }
        return null;


    }

    static void setBoardToWhiteSide(boolean toME){
        if(toME){
            cord_white_y = 7;
            cord_black_y = 0;
        }else {
            cord_white_y = 0;
            cord_black_y = 7;
        }
    }

    public static int getCord_white_y() {
        return cord_white_y;
    }

    public static int getCord_black_y() {
        return cord_black_y;
    }

    public boolean isWhite() {
        return white;
    }

    public Player getOpponent() {
        return opponent;
    }

    void setOpponent(Player opponent) {
        this.opponent = opponent;
    }
    void setBoard(Board board){
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
