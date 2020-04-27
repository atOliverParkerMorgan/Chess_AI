package Board;


import Pieces.Piece;

import java.io.Serializable;

public final class Move implements Serializable {

    public final Spot spot;
    public final Spot oldSpot;
    public final Piece piece;

    public Move(Spot spot, Spot oldSpot){
        this.spot = spot;
        this.oldSpot = oldSpot;
        this.piece = oldSpot.piece;
        if(this.piece == null){
            throw new Error("Move the move that you're trying to make does not contain a piece spot from: x: "+ oldSpot.x+" y: "+ oldSpot.y+" spot to: x: "+spot.x+" y: "+spot.y+" piece status: "+ piece);
        }
    }


}
