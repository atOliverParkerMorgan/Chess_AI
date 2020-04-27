package Board;

import Pieces.Piece;
import java.io.Serializable;

public final class Spot implements Serializable {
    public int x;
    public int y;
    // the piece on the spot
    public Piece piece;
    //the piece that is shown on the UI
    public Piece showPiece;
    // white king can move here
    public boolean isValidForWhiteKing;
    // black king can move here
    public boolean isValidForBlackKing;

    public boolean mouseOn = false;

    final String id;


    Spot(int x, int y, String id) {
        // the location of the spot
        this.id = id;
        this.x = x;
        this.y = y;
        // the piece on the spot
        piece = null;
        // at init all spot are valid for kings
        this.isValidForWhiteKing = true;
        this.isValidForBlackKing = true;

    }

    public void occupySpot(Piece piece){
        //if piece already here, delete it, i. e. set it dead
        //place piece here
        this.piece = piece;
        this.piece.setX(x);
        this.piece.setY(y);

    }
    public void unoccupiedSpot(){
        this.piece = null;
        showPiece = null;
    }

    public boolean isOccupied() {

        return this.piece != null;
    }


    public boolean toShow() {

        return mouseOn;
    }
    public String getPieceCategory() {
        if(mouseOn){
            return showPiece.getCategory();
        }
        return piece.getCategory();
    }
    public Piece getPiece(){return piece;}

    public void showOnSpot(Piece piece){
        if(mouseOn) {
            showPiece = piece;
        }
    }




}



