package Game;

import Board.Spot;
import Board.Move;
import Board.Board;
import Pieces.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Player implements Serializable {

    public List<Piece> pieces;
    private boolean white;
    private Player opponent;
    private Board board;
    private static int cordWhiteY = 0;
    private static int cordBlackY = 7;

    Player(boolean white){
        this.opponent = null;
        this.board = null;

        // seven is bottom zero is top

        this.white = white;
        this.pieces = new ArrayList<>();
    }
    public boolean isInCheck(){
        King k = this.getKing();

        assert k != null;
        Spot s = this.board.getSpot(k.getX(),k.getY());

        Game.createCheckMap(this.board);

        if(this.white){
           return !s.isValidForWhiteKing;
        }else{
           return  !s.isValidForBlackKing;
        }

    }
    public boolean isInCheckMate(){
        King k = this.getKing();

        assert k != null;
        Spot s = this.board.getSpot(k.getX(),k.getY());

        Game.createCheckMap(this.board);


        if(this.white){
            Game.possibleMovesWhite(this.board, this , this.opponent);

            return !s.isValidForWhiteKing && this.numberOfLegalMoves()==0;
        }else{
            Game.possibleMovesBlack(this.board,this.opponent , this);

            return  !s.isValidForBlackKing && this.numberOfLegalMoves()==0;
        }

    }

    public boolean isInStaleMate(){
        return this.numberOfLegalMoves()==0;
    }



    public List<Piece> getPieces(){
        return this.pieces;
    }
    private int numberOfLegalMoves(){
        int total = 0;
        for(Piece p :this.pieces){
            total+=p.getAll_possible_moves().size();
        }
        return total;
    }
    public List<Move> legalMoves(){
        List<Move> total = new ArrayList<>();
        for(Piece p :this.pieces){
            total.addAll(p.getAll_possible_moves());
        }
        return total;
    }

    void initializePieces(){
        final int NUMBER_OF_PAWNS = 8;
        if(this.white) {

            pieces.add(new Rook(0, cordWhiteY, "Rook_white",500));
            pieces.add(new Rook(7, cordWhiteY, "Rook_white",500));
            pieces.add(new Knight(1, cordWhiteY, "Knight_white",300));
            pieces.add(new Knight(6, cordWhiteY, "Knight_white",300));
            pieces.add(new Bishop(2, cordWhiteY, "Bishop_white",300));
            pieces.add(new Bishop(5, cordWhiteY, "Bishop_white",300));
            // queen always has to have her color this will change if black is on index_y zero or seven
            if(cordWhiteY ==0) {
                pieces.add(new Queen(3, cordWhiteY, "Queen_white", 900));
                pieces.add(new King(4, cordWhiteY, "King_white", 10000));
            }else{
                pieces.add(new Queen(3, cordWhiteY, "Queen_white", 900));
                pieces.add(new King(4, cordWhiteY, "King_white", 10000));
            }
            for (int i = 0; i < NUMBER_OF_PAWNS; i++) {
                if(cordWhiteY ==7) {
                    pieces.add(new Pawn(i, cordWhiteY - 1, "Pawns_white", 100));
                }else{
                    pieces.add(new Pawn(i, cordWhiteY + 1, "Pawns_white", 100));
                }
            }
        }else {

            pieces.add(new Rook(0, cordBlackY,"Rook_black",500));
            pieces.add(new Rook(7, cordBlackY,"Rook_black",500));
            pieces.add(new Knight(1, cordBlackY,"Knight_black",300));
            pieces.add(new Knight(6, cordBlackY,"Knight_black",300));
            pieces.add(new Bishop(2, cordBlackY,"Bishop_black",300));
            pieces.add(new Bishop(5, cordBlackY,"Bishop_black",300));
            // queen always has to have her color this will change if black is on index_y zero or seven
            if(cordBlackY ==7) {
                pieces.add(new Queen(3, cordBlackY, "Queen_black", 900));
                pieces.add(new King(4, cordBlackY, "King_black", 10000));
            }else {
                pieces.add(new Queen(3, cordBlackY, "Queen_black", 900));
                pieces.add(new King(4, cordBlackY, "King_black", 10000));
            }
            for (int i = 0; i < NUMBER_OF_PAWNS; i++) { // draw pawns
                if(cordBlackY ==7) {
                    pieces.add(new Pawn(i, cordBlackY - 1, "Pawns_black", 100));
                }else{
                    pieces.add(new Pawn(i, cordBlackY + 1, "Pawns_black", 100));
                }
            }
        }



    }



    public King getKing(){
        for(Piece p :this.pieces){
           if(p.getCategory().contains("King")){
                return (King) p;
           }
        }
        return null;


    }

    static void setBoardToWhiteSide(boolean toME){
        if(toME){
            cordWhiteY = 7;
            cordBlackY = 0;
        }else {
            cordWhiteY = 0;
            cordBlackY = 7;
        }
    }

    public static int getCordWhiteY() {
        return cordWhiteY;
    }

    public static int getCordBlackY() {
        return cordBlackY;
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
