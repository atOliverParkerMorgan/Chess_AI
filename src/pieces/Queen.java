package pieces;
import java.io.Serializable;
import java.util.Objects;

public final class Queen extends Piece implements Serializable {

    private final static int[][] pos_eval_white = new int[][]{
            { 0,   0,   0,   0,   0,   0,   0,   0},
            {50,  50,  50,  50,  50,  50,  50,  50},
            {10,  10,  20,  30,  30,  20,  10,  10},
            { 5,   5,  10,  25,  25,  10,   5,   5},
            { 0,   0,   0,  20,  20,   0,   0,   0},
            { 5, - 5, -10,   0,   0, -10, - 5,   5},
            { 5,  10,  10, -20, -20,  10,  10,   5},
            { 0,   0,   0,   0,   0,   0,   0,   0}
    };

    private final static int[][] pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));
    public Queen(int x, int y, String c, int score) {
        super(x, y, c,score,pos_eval_white,pos_eval_black);
    }

}