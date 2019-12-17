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

    public Piece(int x, int y, String c, int Score){
        super();
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
    static int[][] array_clone(int[][] array){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(array);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (int[][]) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    static int[][] reverse_array(int[][] array){


        // switch items in array
        for(int[] ar : array){
            for(int i = 0; i < ar.length / 2; i++)
            {
                int temp = ar[i];
                ar[i] = ar[ar.length - i - 1];
                ar[ar.length - i - 1] = temp;
            }

        }
        // switch arrays in array
        for(int y = 0; y<array.length / 2; y++){
            int[] temp = array[y];
            array[y] = array[array.length- y- 1];
            array[array.length- y- 1] = temp;

        }

        return array;
    }





}
