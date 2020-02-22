# Chess (AI included)
this program is a chess UI it comes with a built in AI

## AI
the AI uses
* [MiniMax](https://en.wikipedia.org/wiki/Minimax) 
#
* [alpha beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) 
* [null move-heuristic pruning](https://en.wikipedia.org/wiki/Null-move_heuristic)
* [move ordering](https://stackoverflow.com/questions/9964496/alpha-beta-move-ordering)
#
* [Evaluation](https://www.chessprogramming.org/Evaluation)

## For what can use this?
* play a game Player vs Player
* play a againts my toy AI 
* watch two AIs battle it out
```
note the AI is very primitive the Evaluation could be much better and the MiniMax alghorithm could be optimized
```
## Controls
* you can take back a move with Z (!!! TAKE-BACK !!! will always be printed in the console)
* the piece are controlled by dragging and dropping

## How does it look?
these are some screenshots from the app:

* ### main page
#
![Main UI](https://i.imgur.com/quMhIbL.png)

#
* ### menu page
# 

![menu UI](https://i.imgur.com/N5R8ITK.png)

#


## Built With

* [Processing](https://processing.org/) - The graphic interface

## Author

**OLIVER MORGAN**

## License

This project is licensed under the MIT License - see the [LICENSE.md](https://github.com/atOliverParkerMorgan/Chess_AI/blob/master/LICENSE) file for details

## Acknowledgments
the UI is fully functional, but the AI could be much better and faster. My computer calculates about 70 moves per a second ([DeepBlue](https://en.wikipedia.org/wiki/Deep_Blue_(chess_computer)) calculated 200 million moves per a second) 
the AI cold be further expanded with [parallel computing](https://stackoverflow.com/questions/3350459/parallel-programming-in-java) or optimization of the [MOVE](https://github.com/atOliverParkerMorgan/Chess_AI/blob/master/src/Game/Game.java) method 
