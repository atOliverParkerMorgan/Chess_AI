package Board;


import pieces.Piece;

import java.io.Serializable;

public final class Spot implements Serializable {
    public int x;
    public int y;
    // the piece on the spot
    public Piece piece;
    //the piece that is shown on the UI
    public Piece show_piece;
    // white king can move here
    public boolean isValid_for_white_king;
    // black king can move here
    public boolean isValid_for_black_king;

    public boolean mouse_on = false;

    public final String id;


    Spot(int x, int y, String id) {
        super();
        // the location of the spot
        this.id = id;
        this.x = x;
        this.y = y;
        // the piece on the spot
        piece = null;
        // at init all spot are valid for kings
        this.isValid_for_white_king = true;
        this.isValid_for_black_king = true;

    }

    public void occupySpot(Piece piece){
        //if piece already here, delete it, i. e. set it dead
        //place piece here
        this.piece = piece;
        piece.x = this.x;
        piece.y = this.y;

    }
    public void unoccupiedSpot(){
        this.piece = null;
        show_piece = null;
    }

    public boolean isOccupied() {

        return this.piece != null;
    }


    public boolean toShow() {

        return mouse_on;
    }
    public String get_Piece_category() {
        if(mouse_on){
            return show_piece.category;
        }
        return piece.category;
    }
    public Piece getPiece(){return piece;}

    public void show_on_Spot(Piece piece){
        if(mouse_on) {
            show_piece = piece;
        }
    }




}



