package Game.AI;


import Board.Move;
import Game.Game;

public interface MoveStrategy {

    Move execute(Game game);
}
