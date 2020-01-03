package RunTime;

import Board.Move;
import Board.Spot;
import Game.AI.MiniMax;
import Game.Game;
import pieces.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Screen extends PApplet {
    private Game mGame;
    private List<Game> Game_history;

    // images variables
    private PImage King_img;
    private PImage King_black_img;

    private PImage Queen_img;
    private PImage Queen_black_img;

    private PImage Bishop_img;
    private PImage Bishop_black_img;

    private PImage Knight_img;
    private PImage Knight_black_img;

    private PImage Rook_img;
    private PImage Rook_black_img;

    private PImage Pawn_img;
    private PImage Pawn_black_img;

    private boolean show_hint = false;

    //Menu
    private boolean AIvsAI = false;
    private boolean PLAYERvsAI = false;
    private boolean PLAYERvsPLAYER = true;
    private boolean MENU = true;
    private boolean side_setup_Menu = true;
    private boolean depth_menu = true;
    private boolean WHITE = false;
    private boolean BLACK = false;
    private boolean AI_player_white = true;
    private int AI_depth = -1;

    // for ai so the canvas has a couple frames to update before the ai starts thinking
    private int draw;




    public void settings() {
        draw = 0;

        // Canvas
        size(800, 900);
        // noLoop();

        // add the base dir of the images
        String base_dir = "C:\\Users\\Oliver\\IdeaProjects\\Chess\\src\\sprites\\";

        //loading Images
        King_img = loadImage(base_dir+"King.png");
        King_black_img  = loadImage(base_dir+"King_black.png");

        Queen_img = loadImage(base_dir+"Queen.png");
        Queen_black_img = loadImage(base_dir+"Queen_black.png");

        Bishop_img = loadImage(base_dir+"Bishop.png");
        Bishop_black_img = loadImage(base_dir+"Bishop_black.png");

        Knight_img = loadImage(base_dir+"Knight.png");
        Knight_black_img = loadImage(base_dir+"Knight_black.png");

        Rook_img = loadImage(base_dir+"Rook.png");
        Rook_black_img = loadImage(base_dir+"Rook_black.png");

        Pawn_img = loadImage(base_dir+"Pawn.png");
        Pawn_black_img = loadImage(base_dir+"Pawn_black.png");
        // add pieces to for black player
        mGame  = new Game();
        Game_history = new ArrayList<>();

        // get the possible for white ( white always starts )
        Game.Possible_moves_white(mGame.getBoard(),mGame.getWhite(),mGame.getBlack());


    }
    private int[] mouse_pos(){
        int X = 0;
        int Y = 0;
        if(mouseX>=100 && mouseX <200){X = 1;}
        if(mouseY>=100 && mouseY <200){Y = 1;}
        if(mouseX>=200 && mouseX <300){X = 2;}
        if(mouseY>=200 && mouseY <300){Y = 2;}
        if(mouseX>=300 && mouseX <400){X = 3;}
        if(mouseY>=300 && mouseY <400){Y = 3;}
        if(mouseX>=400 && mouseX <500){X = 4;}
        if(mouseY>=400 && mouseY <500){Y = 4;}
        if(mouseX>=500 && mouseX <600){X = 5;}
        if(mouseY>=500 && mouseY <600){Y = 5;}
        if(mouseX>=600 && mouseX <700){X = 6;}
        if(mouseY>=600 && mouseY <700){Y = 6;}
        if(mouseX>=700){X = 7;}
        if(mouseY>=700){Y = 7;}
        return new int[]{X, Y};
    }

    // function onclick for button
    private boolean overCircle(int x, int diameter) {
        float disX = x - mouseX;
        float disY = 850 - mouseY;
        return sqrt(sq(disX) + sq(disY)) < diameter / 2;
    }

    public void draw() {

        if (MENU) {
            // creating main menu pick out of: Player vs Player, AI vs Player, AI vs AI

            background(255, 153, 153);
            textSize(64);
            fill(0, 51, 51);
            text("CHESS", 300, 150);
            textSize(20);
            text("Oliver Morgan", 350, 880);

            // checking mouse (creating boundaries)
            if (mouseX >= 250 && mouseX < 600 && mouseY >= 350 && mouseY < 430) {
                PLAYERvsPLAYER = true;
                PLAYERvsAI = false;
                AIvsAI = false;
            } else if (mouseX >= 280 && mouseX < 520 && mouseY >= 450 && mouseY < 530) {
                PLAYERvsPLAYER = false;
                PLAYERvsAI = true;
                AIvsAI = false;
            } else if (mouseX >= 320 && mouseX < 470 && mouseY >= 550 && mouseY < 630) {
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
                rect(220, 350, 330, 80);
            }
            fill(102, 0, 51);
            text("PLAYER vs PLAYER", 240, 400);

            if (PLAYERvsAI) {
                fill(126, 209, 235);
                rect(280, 450, 240, 80);

            }

            fill(102, 0, 51);
            text("PLAYER vs AI", 300, 500);


            if (AIvsAI) {
                fill(126, 209, 235);
                rect(320, 550, 150, 80);

            }
            fill(102, 0, 51);
            text("AI vs AI", 340, 600);


        }else if(side_setup_Menu && !AIvsAI && !PLAYERvsPLAYER){
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
           fill(0, 51, 51);
           textSize(64);
           text("CHESS", 300, 150);
           textSize(27);
           text("Pick the color of your pieces: ",225,300);
           textSize(20);
           text("Oliver Morgan", 350, 880);

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
            side_setup_Menu = false;
            Game.whiteSide = true;

        }
        // depth menu
        else if(depth_menu&&(PLAYERvsAI||AIvsAI)){
            if (mouseX >= 275 && mouseX < 450 && mouseY >= 375 && mouseY < 425) {
                AI_depth = 1;
            } else if (mouseX >= 275 && mouseX < 450 && mouseY >= 475 && mouseY < 525) {
                AI_depth = 2;
            }else if (mouseX >= 275 && mouseX < 450 && mouseY >= 575 && mouseY < 625){
                AI_depth = 3;
            }else if (mouseX >= 275 && mouseX < 450 && mouseY >= 675 && mouseY < 725) {
                AI_depth = 4;
            } else {
                AI_depth = -1;
            }
            background(255, 153, 153);
            fill(0, 51, 51);
            textSize(64);
            text("CHESS", 300, 150);
            textSize(27);
            text("Pick in what depth the AI will think: ",215,300);
            textSize(20);
            text("Oliver Morgan", 350, 880);

            textSize(40);
            if(AI_depth==1){
                fill(126, 209, 235);
                rect(275, 350, 175, 75);
            }else if(AI_depth==2){
                fill(126, 209, 235);
                rect(275, 450, 175, 75);
            }else if(AI_depth==3){
                fill(126, 209, 235);
                rect(275, 550, 175, 75);
            }
            else if(AI_depth==4){
                fill(126, 209, 235);
                rect(275, 650, 175, 75);
            }

            fill(255, 43, 120);
            text("depth: 1", 300,400);
            text("depth: 2", 300,500);
            text("depth: 3", 300,600);
            text("depth: 4", 300,700);

        }


        else{


            // create board
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j + 1) % 2 == 0) {
                        fill(255, 255, 102); // black

                        if (show_hint) {
                            for (Move m : mGame.Piece_moving.getAll_possible_moves()) {
                                if (m.spot.x == i && m.spot.y == j) {
                                    fill(0, 204, 102);

                                }
                            }
                        }
                    } else {
                        fill(255, 255, 255); // white
                        if (show_hint) {
                            for (Move m : mGame.Piece_moving.getAll_possible_moves()) {
                                if (m.spot.x == i && m.spot.y == j) {
                                    fill(0, 204, 102);
                                }
                            }
                        }

                    }
                    int WIDTH = 800;
                    int BLOCK_X = WIDTH / 8;
                    int HEIGHT = 800;
                    int BLOCK_Y = HEIGHT / 8;
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
            rect(0,800,800,800);

            int Y_cord = 800;


            if (mGame.white_menu) {

                //show pieces
                image(Queen_img, 600, Y_cord);

                image(Rook_img, 450, Y_cord);

                image(Bishop_img, 300, Y_cord);

                image(Knight_img, 150, Y_cord);


            } else if (mGame.black_menu) {


                //show pieces
                image(Queen_black_img, 600, Y_cord);

                image(Rook_black_img, 450, Y_cord);

                image(Bishop_black_img, 300, Y_cord);

                image(Knight_black_img, 150, Y_cord);


            } else {


                String text;
                if (mGame.getTurn() % 2 == 0) {
                    text = "BLACK";
                } else {
                    text = "WHITE";
                }
                textSize(32);
                fill(135, 87, 87);
                text(text, 350, Y_cord + 50);
                text(mGame.getTurn(), 10, Y_cord + 50);
            }
            for (Spot[] spots : mGame.getBoard().spots) {
                for (Spot spot : spots) {
                    if (spot.isOccupied() || spot.toShow()) {
                        switch (spot.get_Piece_category()) {
                            case "King_white":
                                image(King_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Queen_white":
                                image(Queen_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Bishop_white":
                                image(Bishop_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Knight_white":
                                image(Knight_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Rook_white":
                                image(Rook_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Pawns_white":
                                image(Pawn_img, spot.x * 100, spot.y * 100);
                                break;
                            case "King_black":
                                image(King_black_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Queen_black":
                                image(Queen_black_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Bishop_black":
                                image(Bishop_black_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Knight_black":
                                image(Knight_black_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Rook_black":
                                image(Rook_black_img, spot.x * 100, spot.y * 100);
                                break;
                            case "Pawns_black":
                                image(Pawn_black_img, spot.x * 100, spot.y * 100);
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
                        spot.mouse_on = false;
                        spot.show_piece = null;
                    }
                }
                // change the look of the spot were the mouse is located
                Spot spot = mGame.getBoard().getSpot(X, Y);
                spot.mouse_on = true;
                spot.show_on_Spot(mGame.Piece_moving);
            }




        }
    }
    // revert move => take last game state from Game_history ArrayList
    public void keyPressed(){
        if (key == 'z' && Game_history.size() > 0) {
            mGame = Game_history.get(Game_history.size() - 1);
            Game_history.remove(Game_history.size() - 1);
            System.out.println("!!! TAKE-BACK !!!");
            System.out.println();
        }



    }

    public void mousePressed() {
        // deactivate menu if the mouse is over an option
        if(MENU) {
            if (PLAYERvsPLAYER || PLAYERvsAI || AIvsAI) {
                if(AIvsAI){
                    side_setup_Menu = false;
                }if(PLAYERvsPLAYER){
                    side_setup_Menu = false;
                    depth_menu = false;
                }
                MENU = false;
            }
        }else if(side_setup_Menu){
            if(WHITE||BLACK){

                side_setup_Menu = false;
                Game.whiteSide = WHITE;
                AI_player_white = !WHITE;
            }
        }else if(depth_menu){

            if(AI_depth==1||AI_depth==2||AI_depth==3||AI_depth==4){
                depth_menu = false;
                System.out.println(AI_depth);
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



                    int circle_Size = 80;
                    if (overCircle(650, circle_Size) && mGame.white_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Queen(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Queen_white", 900);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.white_menu = false;
                        Game.Possible_moves_black(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(500, circle_Size) && mGame.white_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Rook(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Rook_white", 500);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.white_menu = false;
                        Game.Possible_moves_black(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(350, circle_Size) && mGame.white_menu) {

                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Bishop(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Bishop_white", 325);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.white_menu = false;
                        Game.Possible_moves_black(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(200, circle_Size) && mGame.white_menu) {

                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Knight(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Knight_white", 300);
                        mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getWhite().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.white_menu = false;
                        Game.Possible_moves_black(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(650, circle_Size) && mGame.black_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Queen(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Queen_black", 900);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.black_menu = false;
                        Game.Possible_moves_white(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());

                    } else if (overCircle(500, circle_Size) && mGame.black_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Rook(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Rook_black", 500);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.black_menu = false;
                        Game.Possible_moves_white(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(350, circle_Size) && mGame.black_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Bishop(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Bishop_black", -325);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.black_menu = false;
                        Game.Possible_moves_white(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    } else if (overCircle(200, circle_Size) && mGame.black_menu) {
                        mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Knight(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Knight_black", -300);
                        mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                        mGame.getBlack().pieces.remove(mGame.change_pawn);
                        mGame.change_pawn = null;
                        mGame.black_menu = false;
                        Game.Possible_moves_white(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());


                    }


                if (mGame.getBoard().getSpot(X_pos, Y_pos).isOccupied()) {
                    // start the moving process
                    mGame.moving = true;
                    show_hint = true;

                    // the piece that we want to move
                    mGame.Piece_moving = mGame.getBoard().getSpot(X_pos, Y_pos).piece;
                }
            }
        }



    }


    public void mouseReleased(){
        if(PLAYERvsPLAYER || PLAYERvsAI && mGame.getBoard().currentPlayer.isWhite()!=AI_player_white) {

            // reset board
            assert mGame.getBoard() != null;
            for (Spot[] spots : mGame.getBoard().spots) {
                for (Spot s : spots) {

                    s.mouse_on = false;
                    s.show_piece = null;
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
                    old_spot = mGame.getBoard().getSpot(mGame.Piece_moving.getX(), mGame.Piece_moving.getY());
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
                    show_hint = false;
                    mGame.moving = false;

                    for (Move m : mGame.Piece_moving.getAll_possible_moves()) {
                        if (m.spot == new_move.spot && m.old_spot == new_move.old_spot) {
                            Game_history.add(mGame.copy());
                            mGame.MOVE(new_move, false);
                        }
                    }

                }
            }
        }
    }

    private void moveAI(){


        if(mGame.getBoard().currentPlayer.isWhite()==AI_player_white && !PLAYERvsPLAYER && draw >= 1|| AIvsAI && draw >= 1) {

            if(mGame.getBoard().currentPlayer.IsInCheckMate()){
                System.exit(0);
            }

            Game_history.add(mGame.copy());
            draw = 0; // implemented so board graphics can update


            final MiniMax AI = new MiniMax(AI_depth);
            Move move_AI = AI.getBestMove(mGame, false);
            mGame.MOVE(move_AI, true);

            if (PLAYERvsAI && AI_player_white && mGame.white_menu || AIvsAI && mGame.white_menu) {
                mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Queen(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Queen_white", 900);
                mGame.getWhite().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                mGame.getWhite().pieces.remove(mGame.change_pawn);
                mGame.change_pawn = null;
                mGame.white_menu = false;
                Game.Possible_moves_black(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());
            }
            else if (PLAYERvsAI && !AI_player_white && mGame.black_menu || AIvsAI && mGame.black_menu) {
                System.out.println("here");
                mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece = new Queen(mGame.change_pawn.getX(), mGame.change_pawn.getY(), "Queen_black", 900);
                mGame.getBlack().pieces.add(mGame.getBoard().getSpot(mGame.change_pawn.getX(), mGame.change_pawn.getY()).piece);
                mGame.getBlack().pieces.remove(mGame.change_pawn);
                mGame.change_pawn = null;
                mGame.black_menu = false;
                Game.Possible_moves_white(mGame.getBoard(), mGame.getWhite(), mGame.getBlack());
            }

        }
        if(mGame.getBoard().currentPlayer.isWhite()==AI_player_white|| AIvsAI  ) {
            // draw has one frames to update
            draw++;
        }
    }


    public static void main(String... args) {
        PApplet.main("RunTime.Screen");
    }

}
