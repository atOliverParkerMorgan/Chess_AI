package Game;



import Board.Board;
import Board.Spot;
import Board.Move;

import pieces.King;
import pieces.Pawn;
import pieces.Piece;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

   private Player white;
   private Player black;

   private int turn;

   private Board board;

    // show piece menu when pawn at end of the board
    public boolean white_menu;
    public boolean black_menu;

    public Piece change_pawn;


    public boolean moving;
    public Piece Piece_moving;

    public static final boolean whiteSide = false;


    public Game(){


        this.Piece_moving = null;
        Player.setBoardToWhiteSide(whiteSide);
        this.white = new Player(true);
        this.black = new Player(false);

        this.white.initializePieces();
        this.black.initializePieces();

        // Setup board
        this.board = new Board(this.white, this.black);
        for(Piece P: white.getPieces()) {
            board.getSpot(P.x, P.y).occupySpot(P);
        }
        for(Piece P: black.getPieces()) {
            board.getSpot(P.x, P.y).occupySpot(P);
        }

        this.white.setBoard(this.board);
        this.white.setOpponent(this.black);

        this.black.setBoard(this.board);
        this.black.setOpponent(this.white);

        //Pawn gets to the end init
        this.black_menu = false;
        this.white_menu = false;

        this.turn = 1;
        this.moving = false;
    }

    // copy
    public Game copy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Game) ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void create_check_map(Board board){
        // set all spot isValid_for_white_king + isValid_for_white_king to true
        for (Spot[] spots1 : board.spots) {
            for (Spot s : spots1) {
                s.isValid_for_black_king = true;
                s.isValid_for_white_king = true;
            }
        }

        // check for king being in check
        board.Check_king();

    }
    private void checkMoveBefore(Spot spot){
        // the piece that is being moved
        Piece piece = this.Piece_moving;

        //RESET EN-PASSE
        if(this.turn%2!=0) {
            for (Piece reset_pawn : this.white.pieces){
                if(reset_pawn.category.contains("Pawns")){
                    Pawn pawn_r = (Pawn) reset_pawn;
                    pawn_r.en_passe = false;
                }
            }
        }else{
            for (Piece reset_pawn : this.black.pieces){
                if(reset_pawn.category.contains("Pawns")){
                    Pawn pawn_r = (Pawn) reset_pawn;
                    pawn_r.en_passe = false;
                }
            }
        }

        if(piece.category.equals("Rook_white")) {
            if(piece.x==0) {
                assert this.white.Get_King() != null;
                this.white.Get_King().castling_l = false;
            }else if(piece.x==7){
                assert this.white.Get_King() != null;
                this.white.Get_King().castling_r = false;
            }
        }
        else if(piece.category.equals("Rook_black")){
            if(piece.x==0) {
                assert this.black.Get_King() != null;
                this.black.Get_King().castling_l = false;
            }else if(piece.x==7){
                assert this.black.Get_King() != null;
                this.black.Get_King().castling_r = false;
            }
        }
        // add piece to new spot
        if(spot.isOccupied()){
            if(spot.piece.category.contains("white")){
                this.white.pieces.remove(spot.piece);
            }else if(spot.piece.category.contains("black")){
                this.black.pieces.remove(spot.piece);
            }
        }
    }
    public Game GetGameAfterMove(Move move){
        Game game = this.copy();

        game.MOVE(move, false);
        return game;



    }


    public void MOVE(Move move, boolean UI){

        // ---------------- MOVING ---------------
        this.checkMoveBefore(move.spot);

        //EN-PASSE => effect in move
        // this if block checks if a pawn is moving diagonal and if the spot where he is moving is occupied
        // if not this means that the move is a en-passe move since in all other scenarios must have piece
        // of the opposite colour on said spot. under the if statements is the en-passe logic.
        if (move.piece.category.contains("Pawns")) {
            if (move.old_spot.x - 1 == move.spot.x && !move.spot.isOccupied()) {
                Spot enpasse = this.board.getSpot(move.old_spot.x - 1, move.old_spot.y);
                enpasse.unoccupiedSpot();
            } else if (move.old_spot.x + 1 == move.spot.x && !move.spot.isOccupied()) {
                Spot enpasse = this.board.getSpot(move.old_spot.x + 1, move.old_spot.y);
                enpasse.unoccupiedSpot();

            }
        }


        this.board.getSpot(move.spot.x,move.spot.y).occupySpot(move.piece);
        this.board.getSpot(move.old_spot.x,move.old_spot.y).unoccupiedSpot();


        this.UpdatePlayerPieces();

        this.checkMoveAfter(move.old_spot, move.spot, move.piece, UI);

    }



    private void checkMoveAfter(Spot old_spot, Spot spot, Piece piece, boolean UI) {

        int endForWhitePawn = whiteSide?0:7;
        int endForBlackPawn = whiteSide?7:0;

        // Pawn logic
        if (piece.category.equals("Pawns_white")) {
            //creating menu when pawn gets at the end of the board
            Pawn p = (Pawn) piece;

            if (piece.y == endForWhitePawn) {
                this.white_menu = true;
                this.change_pawn = piece;
            }

            // EN-PASSE
            p.en_passe = (old_spot.y == 1 && spot.y == 3 && p.first);
            p.first = false;

        } else if (piece.category.equals("Pawns_black")) {
            //creating menu when pawn gets at the end of the board
            Pawn p = (Pawn) piece;

            if (piece.y == endForBlackPawn) {
                this.black_menu = true;
                this.change_pawn = piece;
            }

            // EN-PASSE
            p.en_passe = (old_spot.y == 6 && spot.y == 4 && p.first || old_spot.y == 1 && spot.y == 3 && p.first);
            p.first = false;
        }

        if (piece.category.contains("King")) {
            King p = (King) piece;

            p.castling_k = false;
            p.castling_l = false;
            p.castling_r = false;

            if (old_spot.x - spot.x == 2) {

                this.board.getSpot(0, old_spot.y).unoccupiedSpot();
                this.board.getSpot(old_spot.x - 1, old_spot.y).occupySpot(board.getSpot(7, old_spot.y).piece);
                p.castled = true;

            }
            if (-old_spot.x + spot.x == 2) {
                this.board.getSpot(7, old_spot.y).unoccupiedSpot();
                this.board.getSpot(old_spot.x + 1, old_spot.y).occupySpot(board.getSpot(0, old_spot.y).piece);
                p.castled = true;
            }
        }
        // stop moving
        // and showing hint
        this.moving = false;
        this.turn++;

        if (this.board.currentPlayer.isWhite()) {
            this.board.setCurrentPlayer(this.black);
            Possible_moves_black(this.board, this.white, this.black);

        } else {
            this.board.setCurrentPlayer(this.white);
            Possible_moves_white(this.board, this.white, this.black);

        }
        // Console Log UI
        if(UI) {

            System.out.println();
            this.board.BoardInChars();
            this.board.printBoardChars();
            System.out.println();

            if (this.board.currentPlayer.IsInCheckMate()) {
                System.out.println("!!! CHECKMATE !!!");
            } else if (this.board.currentPlayer.IsInStaleMate()) {
                System.out.println("!!! STALEMATE !!! ");
            } else if (this.board.currentPlayer.IsInCheck()) {
                System.out.println("!!! CHECK !!! ");
            }

            System.out.println();
            System.out.println("--- --- " + this.turn + " --- ---");
            System.out.println();

        }
    }



    public void UpdatePlayerPieces(){
        List<Piece> ALL_BLACK_PIECES = new ArrayList<>();
        List<Piece> ALL_WHITE_PIECES = new ArrayList<>();

        for(Spot[] spots : this.board.spots){
            for(Spot spot : spots){
                if(spot.isOccupied()){
                    if(spot.piece.category.contains("white")){
                        ALL_WHITE_PIECES.add(spot.piece);
                    }else{
                        ALL_BLACK_PIECES.add(spot.piece);
                    }
                }
            }
        }

        this.white.pieces = ALL_WHITE_PIECES;
        this.black.pieces = ALL_BLACK_PIECES;
    }


    public static void Possible_moves_black(Board board, Player white, Player black){
        // create possible moves on click @Board 57
        for (Piece p : white.pieces) {
            p.all_possible_moves = new ArrayList<>();
        }
        for (Piece p : black.pieces) {
            p.all_possible_moves = new ArrayList<>();
        }

        board.board_possible_moves(black.pieces);


    } public static void Possible_moves_white(Board board, Player white, Player black){
        // create possible moves on click @Board 57
        for (Piece p : white.pieces) {
            p.all_possible_moves = new ArrayList<>();
        }
        for (Piece p : black.pieces) {
            p.all_possible_moves = new ArrayList<>();
        }

        board.board_possible_moves(white.pieces);

    }

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board){
        this.board = board;
    }

    public Player getWhite() {
        return white;
    }

    public Player getBlack() {
        return black;
    }

    public int getTurn() {
        return turn;
    }
}
