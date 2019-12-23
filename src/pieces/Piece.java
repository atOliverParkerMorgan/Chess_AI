package pieces;
import Board.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Piece implements Serializable {

    public final String category;

    public int x;
    public int y;

    public int Score;

    public List<Move> all_possible_moves;
    public double[][] pos_eval_white;
    public double[][] pos_eval_black;

    public Piece(int x, int y, String c, int Score,double[][] pos_eval_white, double[][] pos_eval_black){
        super();
        this.pos_eval_white = pos_eval_white;
        this.pos_eval_black = pos_eval_black;
        this.x = x;
        this.y = y;
        this.category = c;
        this.all_possible_moves = new ArrayList<>();
        this.Score = Score;

    }

    public Piece copy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Piece) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    static double[][] array_clone(double[][] array){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(array);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (double [][]) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    static double[][] reverse_array(double[][] array){


        // switch items in array
        for(double[] ar : array){
            for(int i = 0; i < ar.length / 2; i++)
            {
                double temp = ar[i];
                ar[i] = ar[ar.length - i - 1];
                ar[ar.length - i - 1] = temp;
            }

        }
        // switch arrays in array
        for(int y = 0; y<array.length / 2; y++){
            double[] temp = array[y];
            array[y] = array[array.length- y- 1];
            array[array.length- y- 1] = temp;

        }

        return array;
    }





}
