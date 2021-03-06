package Game;



import Board.Board;
import Board.Spot;
import Board.Move;

import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game implements Serializable {

    private Player white;
    private Player black;

    private int turn;

    private Board board;

    // show piece menu when pawn at end of the board
    public boolean whiteMenu;
    public boolean blackMenu;

    public Piece changePawn;


    public boolean moving;
    public Piece pieceMoving;

    public static boolean whiteSide = true;


    public Game() {


        this.pieceMoving = null;
        Player.setBoardToWhiteSide(whiteSide);
        this.white = new Player(true);
        this.black = new Player(false);

        this.white.initializePieces();
        this.black.initializePieces();

        // Setup board
        this.board = new Board(this.white, this.black);
        for (Piece P : white.getPieces()) {
            board.getSpot(P.getX(), P.getY()).occupySpot(P);
        }
        for (Piece P : black.getPieces()) {
            board.getSpot(P.getX(), P.getY()).occupySpot(P);
        }

        this.white.setBoard(this.board);
        this.white.setOpponent(this.black);

        this.black.setBoard(this.board);
        this.black.setOpponent(this.white);

        //Pawn gets to the end init
        this.blackMenu = false;
        this.whiteMenu = false;

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
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void createCheckMap(Board board) {
        // set all spot isValid_for_white_king + isValid_for_white_king to true
        for (Spot[] spots1 : board.spots) {
            for (Spot s : spots1) {
                s.isValidForBlackKing = true;
                s.isValidForWhiteKing = true;
            }
        }

        // check for king being in check
        board.checkKing();

    }

    private void checkMoveBefore() {
        // the piece that is being moved
        Piece piece = this.pieceMoving;

        //RESET EN-PASSE
        if (this.turn % 2 != 0) {
            for (Piece reset_pawn : this.white.pieces) {
                if (reset_pawn.getCategory().contains("Pawns")) {
                    Pawn pawn_r = (Pawn) reset_pawn;
                    pawn_r.enPasse = false;
                }
            }
        } else {
            for (Piece reset_pawn : this.black.pieces) {
                if (reset_pawn.getCategory().contains("Pawns")) {
                    Pawn pawn_r = (Pawn) reset_pawn;
                    pawn_r.enPasse = false;
                }
            }
        }
        // if a rook move you cannot castle
        if (Objects.requireNonNull(this.board.currentPlayer.getKing()).castlingK) {
            if (piece.getCategory().equals("Rook_white")) {
                if (piece.getX() == 0) {
                    assert this.white.getKing() != null;
                    Objects.requireNonNull(this.white.getKing()).castlingL = false;
                } else if (piece.getX() == 7) {
                    assert this.white.getKing() != null;
                    Objects.requireNonNull(this.white.getKing()).castlingR = false;
                }
            } else if (piece.getCategory().equals("Rook_black")) {
                if (piece.getX() == 0) {
                    assert this.black.getKing() != null;
                    Objects.requireNonNull(this.black.getKing()).castlingL = false;
                } else if (piece.getX() == 7) {
                    assert this.black.getKing() != null;
                    Objects.requireNonNull(this.black.getKing()).castlingR = false;
                }
            }
        }

    }

    public Game getGameAfterMove(Move move) {
        Game game = this.copy();

        game.move(move, false);
        return game;


    }


    public void move(Move move, boolean UI) {
        this.pieceMoving = move.piece;
        // ---------------- MOVING ---------------
        this.checkMoveBefore();

        //EN-PASSE => effect in move
        // this if block checks if a pawn is moving diagonal and if the spot where he is moving is occupied
        // if not this means that the move is a en-passe move since in all other scenarios must have piece
        // of the opposite colour on said spot. under the if statements is the en-passe logic.
        if (move.piece.getCategory().contains("Pawns")) {
            if (move.oldSpot.x - 1 == move.spot.x && !move.spot.isOccupied()) {
                Spot enpasse = this.board.getSpot(move.oldSpot.x - 1, move.oldSpot.y);
                enpasse.unoccupiedSpot();
            } else if (move.oldSpot.x + 1 == move.spot.x && !move.spot.isOccupied()) {
                Spot enpasse = this.board.getSpot(move.oldSpot.x + 1, move.oldSpot.y);
                enpasse.unoccupiedSpot();

            }
        }


        this.board.getSpot(move.spot.x, move.spot.y).occupySpot(move.piece);
        this.board.getSpot(move.oldSpot.x, move.oldSpot.y).unoccupiedSpot();


        this.checkMoveAfter(move.oldSpot, move.spot, move.piece, UI);

    }


    private void checkMoveAfter(Spot old_spot, Spot spot, Piece piece, boolean UI) {
        int endForWhitePawn = whiteSide ? 0 : 7;
        int endForBlackPawn = whiteSide ? 7 : 0;

        // Pawn logic
        if (piece.getCategory().equals("Pawns_white")) {
            //creating menu when pawn gets at the end of the board
            Pawn p = (Pawn) piece;

            if (piece.getY() == endForWhitePawn) {
                this.whiteMenu = true;
                this.changePawn = piece;
            }

            // EN-PASSE
            p.enPasse = (old_spot.y == 1 && spot.y == 3 && p.first);
            p.first = false;

        } else if (piece.getCategory().equals("Pawns_black")) {
            //creating menu when pawn gets at the end of the board
            Pawn p = (Pawn) piece;

            if (piece.getY() == endForBlackPawn) {
                this.blackMenu = true;
                this.changePawn = piece;
            }

            // EN-PASSE
            p.enPasse = (old_spot.y == 6 && spot.y == 4 && p.first || old_spot.y == 1 && spot.y == 3 && p.first);
            p.first = false;
        }


        // castling
        if (piece.getCategory().contains("King")) {
            assert piece instanceof King;
            King p = (King) piece;
            p.castlingK = false;
            p.castlingL = false;
            p.castlingR = false;

            // old spot is the position of the king
            // the rook is located at x: 0||7

            if (old_spot.x - spot.x == 2) {

                this.board.getSpot(old_spot.x - 1, old_spot.y).occupySpot(board.getSpot(0, old_spot.y).piece); //move the rook to the left of the king
                this.board.getSpot(0, old_spot.y).unoccupiedSpot();
                p.castled = true;

            }
            if (spot.x - old_spot.x == 2) {
                this.board.getSpot(old_spot.x + 1, old_spot.y).occupySpot(board.getSpot(7, old_spot.y).piece); //move the rook to the right of the king
                this.board.getSpot(7, old_spot.y).unoccupiedSpot();
                p.castled = true;
            }
        }


        // stop moving
        // and showing hint
        this.moving = false;
        this.turn++;
        this.UpdatePlayerPieces();


        if (this.board.currentPlayer.isWhite()) {
            this.board.setCurrentPlayer(this.black);
            possibleMovesBlack(this.board, this.white, this.black);
        } else {
            this.board.setCurrentPlayer(this.white);
            possibleMovesWhite(this.board, this.white, this.black);

        }
        // Console Log UI
        if(UI) {

            System.out.println();
            this.board.boardInChars();
            this.board.printBoardChars();
            System.out.println();

            if (this.board.currentPlayer.isInCheckMate()) {
                System.out.println("!!! CHECKMATE !!!");
            } else if (this.board.currentPlayer.isInStaleMate()) {
                System.out.println("!!! STALEMATE !!! ");
            } else if (this.board.currentPlayer.isInCheck()) {
                System.out.println("!!! CHECK !!! ");
            }

            System.out.println();
            System.out.println("--- --- " + this.turn + " --- ---");
            System.out.println();

        }
    }



    private void UpdatePlayerPieces(){
        List<Piece> ALL_BLACK_PIECES = new ArrayList<>();
        List<Piece> ALL_WHITE_PIECES = new ArrayList<>();

        for(Spot[] spots : this.board.spots){
            for(Spot spot : spots){
                if(spot.isOccupied()){
                    if(spot.piece.getCategory().contains("white")){
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


    public static void possibleMovesBlack(Board board, Player white, Player black){
        // create possible moves on click @Board 57
        for (Piece p : white.pieces) {
            p.setAll_possible_moves(new ArrayList<>());
        }
        for (Piece p : black.pieces) {
            p.setAll_possible_moves(new ArrayList<>());
        }

        board.boardPossibleMoves(black.pieces);


    } public static void possibleMovesWhite(Board board, Player white, Player black){
        // create possible moves on click @Board 57
        for (Piece p : white.pieces) {
            p.setAll_possible_moves(new ArrayList<>());
        }
        for (Piece p : black.pieces) {
            p.setAll_possible_moves(new ArrayList<>());
        }

        board.boardPossibleMoves(white.pieces);

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
