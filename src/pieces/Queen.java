package pieces;
import java.io.Serializable;
import java.util.Objects;

public final class Queen extends Piece implements Serializable {

    private final static double[][] pos_eval_white = new double[][]{
            { -2,  -1,  -1, -.5, -.5,  -1,  -1,  -2},
            { -1,   0,   0,   0,   0,   0,   0,  -1},
            { -1,   0,  .5,  .5, .5,   .5,   0,  -1},
            {-.5,   0,  .5,  .5, .5,   .5,   0, -.5},
            { -0,   0,  .5,  .5, .5,   .5,   0,  -0},
            { -1,  .5,  .5,  .5, .5,   .5,   0,  -1},
            { -1,   0,   0,   0,   0,   0,   0,  -1},
            {  -2,  -1,  -1,-.5, -.5,  -1,  -1,  -2}
    };

    private final static double[][] pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));
    public Queen(int x, int y, String c, int score) {
        super(x, y, c,score,pos_eval_white,pos_eval_black);
    }

}