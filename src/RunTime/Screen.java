package RunTime;

import Board.Move;
import Board.Spot;
import Game.AI.MiniMax;
import Game.Game;
import Pieces.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Screen extends PApplet {
    private Game mGame;
    private List<Game> gameHistory;

    // images variables
    private PImage kingImg;
    private PImage kingBlackImg;

    private PImage queenImg;
    private PImage queenBlackImg;

    private PImage bishopImg;
    private PImage bishopBlackImg;

    private PImage knightImg;
    private PImage knightBlackImg;

    private PImage rookImg;
    private PImage rookBlackImg;

    private PImage pawnImg;
    private PImage pawnBlackImg;

    private boolean showHint = false;

    //Menu
    private boolean AIvsAI = false;
    private boolean PLAYERvsAI = false;
    private boolean PLAYERvsPLAYER = true;
    private boolean MENU = true;
    private boolean sideSetupMenu = true;
    private boolean depthMenu = true;
    private boolean WHITE = false;
    private boolean BLACK = false;
    private boolean AIPlayerWhite = true;
    private int AIDepth = -1;

    // for ai so the canvas has a couple frames to update before the ai starts thinking
    private int draw;




    public void settings() {
        draw = 0;

        // Canvas
        size((displayHeight-200), displayHeight-80);
        // noLoop();

        // add the base dir of the images
        String base_dir = System.getProperty("user.dir")+"\\src\\sprites\\"; // may create error everybody as their own directory

        //loading Images
        kingImg = loadImage(base_dir+"King.png");
        kingBlackImg = loadImage(base_dir+"King_black.png");

        queenImg = loadImage(base_dir+"Queen.png");
        queenBlackImg = loadImage(base_dir+"Queen_black.png");

        bishopImg = loadImage(base_dir+"Bishop.png");
        bishopBlackImg = loadImage(base_dir+"Bishop_black.png");

        knightImg = loadImage(base_dir+"Knight.png");
        knightBlackImg = loadImage(base_dir+"Knight_black.png");

        rookImg = loadImage(base_dir+"Rook.png");
        rookBlackImg = loadImage(base_dir+"Rook_black.png");

        pawnImg = loadImage(base_dir+"Pawn.png");
        pawnBlackImg = loadImage(base_dir+"Pawn_black.png");
        // add pieces to for black player
        mGame  = new Game();
        gameHistory = new ArrayList<>();

        // get the possible for white ( white always starts )
        Game.possibleMovesWhite(mGame.getBoard(),mGame.getWhite(),mGame.getBlack());


    }
    private int[] mouse_pos(){
        int X = 0;
        int Y = 0;
        if(mouseX>=(displayHeight-200)/8 && mouseX <2*(displayHeight-200)/8){X = 1;}
        if(mouseY>=(displayHeight-200)/8 && mouseY <2*(displayHeight-200)/8){Y = 1;}
        if(mouseX>=2*(displayHeight-200)/8 && mouseX <3*(displayHeight-200)/8){X = 2;}
        if(mouseY>=2*(displayHeight-200)/8 && mouseY <3*(displayHeight-200)/8){Y = 2;}
        if(mouseX>=3*(displayHeight-200)/8 && mouseX <4*(displayHeight-200)/8){X = 3;}
        if(mouseY>=3*(displayHeight-200)/8 && mouseY <4*(displayHeight-200)/8){Y = 3;}
        if(mouseX>=4*(displayHeight-200)/8 && mouseX <5*(displayHeight-200)/8){X = 4;}
        if(mouseY>=4*(displayHeight-200)/8 && mouseY <5*(displayHeight-200)/8){Y = 4;}
        if(mouseX>=5*(displayHeight-200)/8 && mouseX <6*(displayHeight-200)/8){X = 5;}
        if(mouseY>=5*(displayHeight-200)/8 && mouseY <6*(displayHeight-200)/8){Y = 5;}
        if(mouseX>=6*(displayHeight-200)/8 && mouseX <7*(displayHeight-200)/8){X = 6;}
        if(mouseY>=6*(displayHeight-200)/8 && mouseY <7*(displayHeight-200)/8){Y = 6;}
        if(mouseX>=7*(displayHeight-200)/8){X = 7;}
        if(mouseY>=7*(displayHeight-200)/8){Y = 7;}
        return new int[]{X, Y};
    }

    // function onclick for button
    private boolean overCircle(int x, int diameter) {
        float disX = x - mouseX;
        float disY = displayHeight-150 - mouseY;
        return sqrt(sq(disX) + sq(disY)) < diameter / 2;
    }

    public void draw() {

        if (MENU) {
            // creating main menu pick out of: Player vs Player, AI vs Player, AI vs AI

            background(255, 153, 153);
            textSize(64);
            fill(0, 51, 51);
            text("CHESS", (displayHeight-200)/2-64, displayHeight/10);
            textSize(20);
            text("Oliver Morgan", (displayHeight-200)/2, (int)(displayHeight/1.1));

            // checking mouse (creating boundaries)
            if (mouseX >= (displayHeight-200)/2-120 && mouseX <(displayHeight-200)/2+210  && mouseY >= displayHeight/3-40 && mouseY < displayHeight/3+40) {
                PLAYERvsPLAYER = true;
                PLAYERvsAI = false;
                AIvsAI = false;
            } else if (mouseX >= (displayHeight-200)/2-80 && mouseX < (displayHeight-200)/2+160 && mouseY >= displayHeight/3+60 && mouseY < displayHeight/3+120) {
                PLAYERvsPLAYER = false;
                PLAYERvsAI = true;
                AIvsAI = false;
            } else if (mouseX >= (displayHeight-200)/2-40 && mouseX < (displayHeight-200)/2+110 && mouseY >= displayHeight/3+150 && mouseY < displayHeight/3+230) {
                PLAYERvsPLAYER = false;
                PLAYERvsAI = false;
                AIvsAI = true;
            } else {
                PLAYERvsPLAYER = false;
                PLAYERvsAI = false;
                AIvsAI = false;
            }

            // out line the option the mouse is over
            textSize(32);
            if (PLAYERvsPLAYER) {
                fill(126, 209, 235);
                rect((displayHeight-200)/2-120, displayHeight/3-40, 330, 80);
            }
            fill(102, 0, 51);
            text("PLAYER vs PLAYER", (displayHeight-200)/2-100, displayHeight/3);

            if (PLAYERvsAI) {
                fill(126, 209, 235);
                rect((displayHeight-200)/2-80, displayHeight/3+60, 240, 80);

            }

            fill(102, 0, 51);
            text("PLAYER vs AI", (displayHeight-200)/2-60, displayHeight/3+100);


            if (AIvsAI) {
                fill(126, 209, 235);
                rect((displayHeight-200)/2-40,displayHeight/3+150, 150, 80);

            }
            fill(102, 0, 51);
            text("AI vs AI", (displayHeight-200)/2-20,displayHeight/3+200);


        }else if(sideSetupMenu && !AIvsAI && !PLAYERvsPLAYER){
            if (mouseX >= 125 && mouseX < 275 && mouseY >= 350 && mouseY < 425) {
                WHITE = true;
                BLACK = false;
            } else if (mouseX >= 525 && mouseX < 675 && mouseY >= 350 && mouseY < 425) {
                WHITE = false;
                BLACK = true;
            }else {
                WHITE = false;
                BLACK = false;
            }
           background(255, 153, 153);
            textSize(64);
            fill(0, 51, 51);
            text("CHESS", (displayHeight-200)/2-64, displayHeight/10);
            textSize(27);
            text("Pick the color of your pieces: ",(displayHeight-200)/2-150, displayHeight/5);
            textSize(20);
            text("Oliver Morgan", (displayHeight-200)/2, (int)(displayHeight/1.1));
           fill(0, 51, 51);


           textSize(40);
           if(WHITE){
               fill(126, 209, 235);
               rect(125, 350, 175, 75);
           }else if(BLACK){
                fill(126, 209, 235);
                rect(525, 350, 175, 75);
            }

           fill(255, 255, 255);
           text("WHITE", 150, 400);
           fill(225, 225, 102);
           text("BLACK", 550,400);

        }
        // there is no need to pick a color for Player vs Player so the menu isn't called
        else if(PLAYERvsPLAYER&&!WHITE){
            WHITE = true;
            sideSetupMenu = false;
            Game.whiteSide = true;
        }
        // depth menu
        else if(depthMenu &&(PLAYERvsAI||AIvsAI)){
            if (mouseX >=(displayHeight-200)/2-40 && mouseX < (displayHeight-200)/2+135 && mouseY >= displayHeight/5+150 && mouseY < displayHeight/5+225) {
                AIDepth = 1;
            } else if (mouseX >=(displayHeight-200)/2-40 && mouseX < (displayHeight-200)/2+135 && mouseY >= displayHeight/5+250 && mouseY < displayHeight/5+325) {
                AIDepth = 2;
            }else if (mouseX >= (displayHeight-200)/2-40 && mouseX < (displayHeight-200)/2+135 && mouseY >= displayHeight/5+350 && mouseY < displayHeight/5+425){
                AIDepth = 3;
            }else if (mouseX >= (displayHeight-200)/2-40 && mouseX < (displayHeight-200)/2+135 && mouseY >= displayHeight/5+450 && mouseY < displayHeight/5+525) {
                AIDepth = 4;
            } else {
                AIDepth = -1;
            }
            background(255, 153, 153);
            textSize(64);
            fill(0, 51, 51);
            text("CHESS", (displayHeight-200)/2-64, displayHeight/10);
            textSize(27);
            text("Pick in what depth the AI will think:",(displayHeight-200)/2-170, displayHeight/5);
            textSize(20);
            text("Oliver Morgan", (displayHeight-200)/2, (int)(displayHeight/1.1));



            textSize(40);
            if(AIDepth ==1){
                fill(126, 209, 235);
                rect((displayHeight-200)/2-40, displayHeight/5+150, 175, 75);
            }else if(AIDepth ==2){
                fill(126, 209, 235);
                rect((displayHeight-200)/2-40, displayHeight/5+250, 175, 75);
            }else if(AIDepth ==3){
                fill(126, 209, 235);
                rect((displayHeight-200)/2-40, displayHeight/5+350, 175, 75);
            }
            else if(AIDepth ==4){
                fill(126, 209, 235);
                rect((displayHeight-200)/2-40, displayHeight/5+450, 175, 75);
            }

            fill(255, 43, 120);
            text("depth: 1", (displayHeight-200)/2-20,displayHeight/5+200);
            text("depth: 2", (displayHeight-200)/2-20,displayHeight/5+300);
            text("depth: 3", (displayHeight-200)/2-20,displayHeight/5+400);
            text("depth: 4", (displayHeight-200)/2-20,displayHeight/5+500);

        }
        else{
            // create board
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j + 1) % 2 == 0) {
                        fill(255, 255, 102); // black

                        if (showHint) {
                            for (Move m : mGame.pieceMoving.getAll_possible_moves()) {
                                if (m.spot.x == i && m.spot.y == j) {
                                    fill(0, 204, 102);

                                }
                            }
                        }
                    } else {
                        fill(255, 255, 255); // white
                        if (showHint) {
                            for (Move m : mGame.pieceMoving.getAll_possible_moves()) {
                                if (m.spot.x == i && m.spot.y == j) {
                                    fill(0, 204, 102);
                                }
                            }
                        }

                    }
                    final int BLOCK_X = (displayHeight-200) / 8;
                    final int BLOCK_Y = (displayHeight-200) / 8;
                    rect(i * BLOCK_X, j * BLOCK_Y, (i + 1) * BLOCK_X, (j + 1) * BLOCK_Y);
                }
            }
            moveAI();
            // getting the board ready
            // add menu
            if(mGame.getBoard().currentPlayer.isWhite()) {
                fill(255, 255, 255);
            }else {
                fill(225, 225, 102);
            }
            rect(0,displayHeight-200,displayHeight,200);

            final int Y_cord = displayHeight-200;


            if (mGame.whiteMenu) {

                //show pieces
                image(queenImg, Y_cord-Y_cord/4+80, Y_cord);

                image(rookImg, Y_cord-(Y_cord/4)*2+80, Y_cord);

                image(bishopImg, Y_cord-(Y_cord/4)*3+80, Y_cord);

                image(knightImg, 80, Y_cord);


            } else if (mGame.blackMenu) {


                //show pieces
                image(queenBlackImg, Y_cord-Y_cord/4+80, Y_cord);

                image(rookBlackImg, Y_cord-(Y_cord/4)*2+80, Y_cord);

                image(bishopBlackImg, Y_cord-(Y_cord/4)*3+80, Y_cord);

                image(knightBlackImg, 80, Y_cord);


            } else {


                String text;
                if (mGame.getTurn() % 2 == 0) {
                    text = "BLACK";
                } else {
                    text = "WHITE";
                }
                textSize(32);
                fill(135, 87, 87);
                text(text, (displayHeight-200)/2-20, displayHeight-130);
                text(mGame.getTurn(), 20, displayHeight-130);
            }
            final int BLOCK_X = (displayHeight-200) / 8;
            final int BLOCK_Y = (displayHeight-200) / 8;
            for (Spot[] spots : mGame.getBoard().spots) {
                for (Spot spot : spots) {
                    if (spot.isOccupied() || spot.toShow()) {
                        switch (spot.getPieceCategory()) {
                            case "King_white":
                                image(kingImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Queen_white":
                                image(queenImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Bishop_white":
                                image(bishopImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Knight_white":
                                image(knightImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Rook_white":
                                image(rookImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Pawns_white":
                                image(pawnImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "King_black":
                                image(kingBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Queen_black":
                                image(queenBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Bishop_black":
                                image(bishopBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Knight_black":
                                image(knightBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Rook_black":
                                image(rookBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                            case "Pawns_black":
                                image(pawnBlackImg, spot.x * BLOCK_X, spot.y * BLOCK_Y);
                                break;
                        }
                    }

                }
            }
            if (mGame.moving) {
                int X;
                int Y;
                // the x and y from 0 - 800 to 0 - 7
                X = mouse_pos()[0];
                Y = mouse_pos()[1];
                // default
                for (Spot[] spots : mGame.getBoard().spots) {
                    for (Spot spot : spots) {
                        spot.mouseOn = false;
                        spot.showPiece = null;
                    }
                }
                // change the look of the spot were the mouse is located
                Spot spot = mGame.getBoard().getSpot(X, Y);
                spot.mouseOn = true;
                spot.showOnSpot(mGame.pieceMoving);
            }




        }
    }
    // revert move => take last game state from Game_history ArrayList
    public void keyPressed(){
        if (key == 'z' && gameHistory.size() > 0) {
            mGame = gameHistory.get(gameHistory.size() - 1);
            gameHistory.remove(gameHistory.size() - 1);
            System.out.println("!!! TAKE-BACK !!!");
            System.out.println();
        }



    }

    public void mousePressed() {
        // deactivate menu if the mouse is over an option
        if(MENU) {
            if (PLAYERvsPLAYER || PLAYERvsAI || AIvsAI) {
                if(AIvsAI){
                    sideSetupMenu = false;
                }if(PLAYERvsPLAYER){
                    sideSetupMenu = false;
                    depthMenu = false;
                }
                MENU = false;
            }
        }else if(sideSetupMenu){
            if(WHITE||BLACK){

                sideSetupMenu = false;
                Game.whiteSide = WHITE;
                AIPlayerWhite = !WHITE;
            }
        }else if(depthMenu){

            if(AIDepth ==1|| AIDepth ==2|| AIDepth ==3|| AIDepth ==4){
                depthMenu = false;
            }
        }
        // pawn level nine menu
        else {

            if (PLAYERvsPLAYER || PLAYERvsAI) {
                // the x and y from 0 - 800 to 0 - 7
                int X_pos = mouse_pos()[0];
                int Y_pos = mouse_pos()[1];

                // take back move board history bigger than one
                // button pos


                    final int Y_cord = displayHeight-200;
                    final int circle_Size = 80;

                    if (overCircle(Y_cord-Y_cord/4+130, circle_Size) && mGame.whiteMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Queen(mGame.changePawn.getX(), mGame.changePawn.getY(), "Queen_white", 900);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.whiteMenu = false;
                        Game.possibleMovesBlack(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(Y_cord-(Y_cord/4)*2+130, circle_Size) && mGame.whiteMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Rook(mGame.changePawn.getX(), mGame.changePawn.getY(), "Rook_white", 500);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.whiteMenu = false;
                        Game.possibleMovesBlack(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(Y_cord-(Y_cord/4)*3+130, circle_Size) && mGame.whiteMenu) {

                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Bishop(mGame.changePawn.getX(), mGame.changePawn.getY(), "Bishop_white", 325);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.whiteMenu = false;
                        Game.possibleMovesBlack(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(130, circle_Size) && mGame.whiteMenu) {

                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Knight(mGame.changePawn.getX(), mGame.changePawn.getY(), "Knight_white", 300);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.whiteMenu = false;
                        Game.possibleMovesBlack(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(Y_cord-Y_cord/4+130, circle_Size) && mGame.blackMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Queen(mGame.changePawn.getX(), mGame.changePawn.getY(), "Queen_black", 900);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.blackMenu = false;
                        Game.possibleMovesWhite(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());

                    } else if (overCircle(Y_cord-(Y_cord/4)*2+130, circle_Size) && mGame.blackMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Rook(mGame.changePawn.getX(), mGame.changePawn.getY(), "Rook_black", 500);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.blackMenu = false;
                        Game.possibleMovesWhite(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(Y_cord-(Y_cord/4)*3+130, circle_Size) && mGame.blackMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Bishop(mGame.changePawn.getX(), mGame.changePawn.getY(), "Bishop_black", -325);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.blackMenu = false;
                        Game.possibleMovesWhite(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(130, circle_Size) && mGame.blackMenu) {
                        mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Knight(mGame.changePawn.getX(), mGame.changePawn.getY(), "Knight_black", -300);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.changePawn);
                        mGame.changePawn = null;
                        mGame.blackMenu = false;
                        Game.possibleMovesWhite(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    }


                if (mGame.getBoard().getSpot(X_pos, Y_pos).isOccupied()) {
                    // start the moving process
                    mGame.moving = true;
                    showHint = true;

                    // the piece that we want to move
                    mGame.pieceMoving = mGame.getBoard().getSpot(X_pos, Y_pos).piece;
                }
            }
        }



    }


    public void mouseReleased(){
        if(PLAYERvsPLAYER || PLAYERvsAI && mGame.getBoard().currentPlayer.isWhite()!= AIPlayerWhite) {

            // reset board
            assert mGame.getBoard() != null;
            for (Spot[] spots : mGame.getBoard().spots) {
                for (Spot s : spots) {

                    s.mouseOn = false;
                    s.showPiece = null;
                }

            }


            if (mGame.moving) {
                // the x and y from 0 - 800 to 0 - 7

                int X_pos = mouse_pos()[0];
                int Y_pos = mouse_pos()[1];

                // the piece that we want to move

                // the spot that we want to move it to
                Spot spot = mGame.getBoard().getSpot(X_pos, Y_pos);
                // the spot were it was located before we started moving it
                Spot old_spot;
                boolean Error = false;
                try {
                    old_spot = mGame.getBoard().getSpot(mGame.pieceMoving.getX(), mGame.pieceMoving.getY());
                } catch (Exception e) {
                    print(e);
                    print('\n');
                    Error = true;
                    old_spot = null;
                }
                // check if an error hasn't accrued this error can happen if you overload the program with clicks
                if (!Error) {

                    Move new_move = new Move(spot, old_spot);
                    // check if piece is in the possible moves + if its the players turn
                    showHint = false;
                    mGame.moving = false;

                    for (Move m : mGame.pieceMoving.getAll_possible_moves()) {
                        if (m.spot == new_move.spot && m.oldSpot == new_move.oldSpot) {
                            gameHistory.add(mGame.copy());
                            mGame.move(new_move, false);
                        }
                    }

                }
            }
        }
    }

    private void moveAI(){


        if(mGame.getBoard().currentPlayer.isWhite()== AIPlayerWhite && !PLAYERvsPLAYER && draw >= 1|| AIvsAI && draw >= 1) {

            if(mGame.getBoard().currentPlayer.isInCheckMate()){
                System.exit(0);
            }

            gameHistory.add(mGame.copy());
            draw = 0; // implemented so board graphics can update


            final MiniMax AI = new MiniMax(AIDepth,true,Integer.MAX_VALUE);
            Move move_AI = AI.getBestMove(mGame, false);
            mGame.move(move_AI, AI.UI);

            if (PLAYERvsAI && AIPlayerWhite && mGame.whiteMenu || AIvsAI && mGame.whiteMenu) {
                mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Queen(mGame.changePawn.getX(), mGame.changePawn.getY(), "Queen_white", 900);
                mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                mGame.getWhite().pieces.remove(mGame.changePawn);
                mGame.changePawn = null;
                mGame.whiteMenu = false;
                Game.possibleMovesBlack(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());
            }
            else if (PLAYERvsAI && !AIPlayerWhite && mGame.blackMenu || AIvsAI && mGame.blackMenu) {
                mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece = new Queen(mGame.changePawn.getX(), mGame.changePawn.getY(), "Queen_black", 900);
                mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.changePawn.getX(), mGame.changePawn.getY()).piece);
                mGame.getBlack().pieces.remove(mGame.changePawn);
                mGame.changePawn = null;
                mGame.blackMenu = false;
                Game.possibleMovesWhite(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());
            }

        }
        if(mGame.getBoard().currentPlayer.isWhite()== AIPlayerWhite || AIvsAI  ) {
            // draw has one frames to update
            draw++;
        }
    }


    public static void main(String... args) {
        PApplet.main("RunTime.Screen");
    }

}
