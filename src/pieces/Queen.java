package pieces;
import java.io.Serializable;
import java.util.Objects;

public final class Queen extends Piece implements Serializable {

    public int[][] pos_eval_white;
    public int[][] pos_eval_black;
    public Queen(int x, int y, String c, int score) {
        super(x, y, c,score);
        this.pos_eval_white = new int[][]{
                                            { -20, -10, -10, - 5, - 5, -10, -10, -20},
                                            { -10,   0,   0,   0,   0,   0,   0, -10},
                                            { -10,   0,   5,   5,   5,   5,   0, -10},
                                            { - 5,   0,   5,   5,   5,   5,   0, - 5},
                                            {   0,   0,   5,   5,   5,   5,   0, - 5},
                                            { -10,   5,   5,   5,   5,   5,   0, -10},
                                            { -10,   0,   5,   0,   0,   0,   0, -10},
                                            { -20, -10, -10, - 5, - 5, -10, -10, -20}
                                            };
        this.pos_eval_black = Piece.reverse_array(Objects.requireNonNull(Piece.array_clone(pos_eval_white)));

    }

}