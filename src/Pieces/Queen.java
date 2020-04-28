package Pieces;
import java.io.Serializable;
import java.util.Objects;

public final class Queen extends Piece implements Serializable {

    private final static double[][] POS_EVAL_WHITE = new double[][]{
            { -2,  -1,  -1, -.5, -.5,  -1,  -1,  -2},
            { -1,   0,   0,   0,   0,   0,   0,  -1},
            { -1,   0,  .5,  .5, .5,   .5,   0,  -1},
            {-.5,   0,  .5,  .5, .5,   .5,   0, -.5},
            { -0,   0,  .5,  .5, .5,   .5,   0,  -0},
            { -1,  .5,  .5,  .5, .5,   .5,   0,  -1},
            { -1,   0,   0,   0,   0,   0,   0,  -1},
            {  -2,  -1,  -1,-.5, -.5,  -1,  -1,  -2}
    };

    private final static double[][] POS_EVAL_BLACK = Piece.reverseArray(Objects.requireNonNull(Piece.arrayClone(POS_EVAL_WHITE)));
    public Queen(int x, int y, String c, int score) {
        super(x, y, c,score, POS_EVAL_WHITE, POS_EVAL_BLACK);
    }

}