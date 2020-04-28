package Board;


import Game.Player;
import Pieces.*;
import Game.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Board implements Serializable{
    public Spot[][] spots;

    public Player currentPlayer;
    private Player white;
    private Player black;

    // for keeping track of moves
    private List<String> charRepresentation;

    public Board(Player player, Player black){
        this.currentPlayer = player;

        this.white = player;
        this.black = black;
        // default


        this.charRepresentation = new ArrayList<>();
        this.spots = new Spot[8][8];

        final String[] X_CORD = new String[] {"a","b","c","d","e","f","g","h"};
        final String[] Y_CORD = new String[] {"1","2","3","4","5","6","7","8"};

        for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                this.spots[j][i] = new Spot(j, i,Y_CORD[i]+"-"+X_CORD[j]);
            }
        }



    }


    public void switchPlayer(){
        if(this.currentPlayer.isWhite()){
            this.currentPlayer = this.black;
        }else{
            this.currentPlayer = this.white;
        }
    }
    public void showCords(){
        StringBuilder show = new StringBuilder();
        for(Spot[] spots: this.spots){
            for(Spot spot: spots){
                show.append(spot.id).append(" ");
            }
            System.out.println(show.toString());
            show = new StringBuilder();
        }

    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    // copy
    private Board copy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Board) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void printBoardChars(){

        for (int i = 0; i < charRepresentation.size(); i += 8) {
            System.out.println(this.charRepresentation.get(i) +
                    this.charRepresentation.get(i + 1) +
                    this.charRepresentation.get(i + 2) +
                    this.charRepresentation.get(i + 3) +
                    this.charRepresentation.get(i + 4) +
                    this.charRepresentation.get(i + 5) +
                    this.charRepresentation.get(i + 6) +
                    this.charRepresentation.get(i + 7));

        }if(this.charRepresentation.size()!=64) {
            System.out.println("ERROR => Char_Representation != 64; size = "+this.charRepresentation.size());
        }

    }


    public void boardInChars(){
    this.charRepresentation = new ArrayList<>();

        for(int y = 0; y<8;y++){
            for(int x = 0; x<8;x++) {
                Spot spot = this.getSpot(x,y);
                if(spot.isOccupied()){
                    switch (spot.piece.getCategory()) {
                        case "Pawns_white":
                            this.charRepresentation.add("\u2659");

                            break;
                        case "Queen_white":
                            this.charRepresentation.add("\u2655");

                            break;
                        case "King_white":
                            this.charRepresentation.add("\u2654");


                            break;
                        case "Bishop_white":
                            this.charRepresentation.add("\u2657");


                            break;
                        case "Knight_white":
                            this.charRepresentation.add("\u2658");


                            break;
                        case "Rook_white":
                            this.charRepresentation.add("\u2656");


                            break;
                        case "Pawns_black":
                            this.charRepresentation.add("\u265F");


                            break;
                        case "Queen_black":
                            this.charRepresentation.add("\u265B");


                            break;
                        case "King_black":
                            this.charRepresentation.add("\u265A");


                            break;
                        case "Bishop_black":
                            this.charRepresentation.add("\u265D");


                            break;
                        case "Knight_black":
                            this.charRepresentation.add("\u265E");


                            break;
                        case "Rook_black":
                            this.charRepresentation.add("\u265C");

                            break;
                    }

                }else{
                    this.charRepresentation.add("- ");
                }
            }
        }
    }

    public Spot getSpot(int x, int y) {
        return spots[x][y];
    }



    public void boardPossibleMoves(List<Piece> pieces){
        for(Piece p: pieces){
            String c = p.getCategory();


            switch (c) {
                case "Rook_black":
                    Rook.possibleMoves(this, p, "black");
                    simulateMoves(p);

                    break;
                case "Rook_white":
                    Rook.possibleMoves(this, p, "white");
                    simulateMoves(p);

                    break;
                case "Knight_black":
                    Knight.possibleMoves(this, p, "black");
                    simulateMoves(p);

                    break;
                case "Knight_white":
                    Knight.possibleMoves(this, p, "white");
                    simulateMoves(p);
                    break;
                case "Bishop_black":
                    Bishop.possibleMoves(this, p, "black");
                    simulateMoves(p);

                    break;
                case "Bishop_white":
                    Bishop.possibleMoves(this, p, "white");
                    simulateMoves(p);
                    break;
                case "Queen_black":
                    // ROOK + Bishop
                    Bishop.possibleMoves(this, p, "black");
                    Rook.possibleMoves(this, p, "black");
                    simulateMoves(p);
                    break;
                case "Queen_white":
                    // ROOK + Bishop
                    Bishop.possibleMoves(this, p, "white");
                    Rook.possibleMoves(this, p, "white");
                    simulateMoves(p);

                    break;
                case "King_white":
                    King.possibleMoves(this, p, "white");
                    simulateMoves(p);

                    break;
                case "King_black":
                    King.possibleMoves(this, p, "black");
                    simulateMoves(p);

                    break;
                case "Pawns_white":
                    Pawn.possibleMoves(this, p, "white");
                    simulateMoves(p);

                    break;
                case "Pawns_black":
                    Pawn.possibleMoves(this, p, "black");
                    simulateMoves(p);

                    break;
            }
        }
    }

    private void simulateMoves(Piece p){
        // this function prevents a move that would put the king in check by simulating the move and then deleting all
        // moves that could lead to the king being in check
        //kings
        Piece k_w = null;
        Piece k_b = null;

        int x = p.getX();
        int y = p.getY();

        Piece default_piece = p;

        List<Move> delete = new ArrayList<>();
        for(Move move : p.getAll_possible_moves()) {

                Board simulating_board = this.copy();

                //move piece
                assert simulating_board != null;
                simulating_board.getSpot(p.getX(), p.getY()).unoccupiedSpot();
                simulating_board.getSpot(move.spot.x, move.spot.y).occupySpot(p);

                // create check map for king
                Game.createCheckMap(simulating_board);
                for(Spot[] s_list: simulating_board.spots){
                    for (Spot s2:s_list) {
                        if (s2.isOccupied()){
                            if (s2.piece.getCategory().contains("King_white")) {
                                k_w = s2.piece;
                            } else if (s2.piece.getCategory().contains("King_black")) {
                                k_b = s2.piece;
                            }
                        }
                    }
                }
                // king can go to a spot occupied by an enemy
                if (p.getCategory().contains("white")) {
                    assert k_w != null;
                    if (!simulating_board.getSpot(k_w.getX(), k_w.getY()).isValidForWhiteKing) {
                        delete.add(move);
                    }


                    // king can go to a spot occupied by an enemy (black)
                }else if (p.getCategory().contains("black")) {
                    assert k_b != null;
                    if (!simulating_board.getSpot(k_b.getX(), k_b.getY()).isValidForBlackKing) {
                        delete.add(move);
                    }
                }
                simulating_board.getSpot(move.spot.x, move.spot.y).unoccupiedSpot();
                simulating_board.getSpot(x, y).occupySpot(p);




        }
        p = default_piece;
        for(Move move_delete: delete){
            p.removePossibleMove(move_delete);
        }


    }




    public void checkKing(){
        // this function adds all if siValid_for_white_king or black_king
        // to false or true depending on the pieces on the board
        for (Spot[] spots : this.spots){
            for(Spot spot: spots){
                if(spot.piece!=null) {
                    int x = spot.piece.getX();
                    int y = spot.piece.getY();
                    switch (spot.piece.getCategory()) {
                        case "Rook_black": {
                            Rook.checkLogic(this, x, y, 7 - x, 7 - y,"black");
                            break;
                        }
                        case "Rook_white": {
                            Rook.checkLogic(this, x, y, 7 - x, 7 - y,"white");
                            break;
                        }
                        case "Knight_black":
                            Knight.checkLogic(this,x,y,"black");
                            break;
                        case "Knight_white":
                            Knight.checkLogic(this,x,y,"white");
                            break;
                        case "Bishop_black":
                            Bishop.checkLogic(this,x,y,7-x,7-y,"black");
                            break;
                        case "Bishop_white":
                            Bishop.checkLogic(this,x,y,7-x,7-y,"white");
                            break;
                        case "Queen_white":
                            // Bishop + Rook
                            Bishop.checkLogic(this,x,y,7-x,7-y,"white");
                            Rook.checkLogic(this, x, y, 7-x, 7-y,"white");

                            break;
                        case "Queen_black":
                            // Bishop + Rook
                            Bishop.checkLogic(this,x,y,7-x,7-y,"black");
                            Rook.checkLogic(this, x, y, 7-x, 7-y,"black");

                            break;
                        case "King_white":
                            King.checkLogic(this,x,y,"white");


                            break;
                        case "King_black":
                            King.checkLogic(this,x,y,"black");

                            break;
                        case "Pawns_white":
                            Pawn.checkLogic(this,x,y,"white");
                            break;
                        case "Pawns_black":
                            Pawn.checkLogic(this,x,y,"black");
                            break;
                    }
                }
            }
        }
    }


}
