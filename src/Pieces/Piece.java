package Pieces;
import Board.Move;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Piece implements Serializable {

    private String category;

    private int x;
    private int y;

    private int Score;

    List<Move> all_possible_moves;
    public final double[][] pos_eval_white;
    public final double[][] pos_eval_black;

    public Piece(int x, int y, String c, int Score,double[][] pos_eval_white, double[][] pos_eval_black){

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
    static double[][] arrayClone(double[][] array){
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


    static double[][] reverseArray(double[][] array){


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

    public boolean isPawn(){
        return category.contains("Pawns");
    }
    public boolean isRook(){
        return category.contains("Rook");
    }
    public boolean isKnight(){
        return category.contains("Knight");
    }
    public boolean isBishop(){
        return category.contains("Bishop");
    }
    public boolean isQueen(){
        return category.contains("Queen");
    }
    public boolean isKing(){
        return  category.contains("King");
    }
    public boolean isWhite(){
        return category.contains("white");
    }
    public boolean isBlack(){
        return category.contains("black");
    }

    public String getCategory() {
        return category;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getScore() {
        return Score;
    }

    public List<Move> getAll_possible_moves() {
        return all_possible_moves;
    }
    public void removePossibleMove(Move move){
        all_possible_moves.remove(move);
    }

    public double[][] getPos_eval_white() {
        return pos_eval_white;
    }

    public double[][] getPos_eval_black() {
        return pos_eval_black;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAll_possible_moves(List<Move> all_possible_moves) {
        this.all_possible_moves = all_possible_moves;
    }
}
