package Board;


import Pieces.Piece;

import java.io.Serializable;

public final class Move implements Serializable {

    public final Spot spot;
    public final Spot old_spot;
    public final Piece piece;

    public Move(Spot spot, Spot old_spot){
        this.spot = spot;
        this.old_spot = old_spot;
        this.piece = old_spot.piece;
        if(this.piece == null){
            throw new Error("Move the move that you're trying to make does not contain a piece spot from: x: "+old_spot.x+" y: "+old_spot.y+" spot to: x: "+spot.x+" y: "+spot.y+" piece status: "+ piece);
        }
    }


}
