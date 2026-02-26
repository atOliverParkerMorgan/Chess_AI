# Chess AI

## Project Overview
Chess AI is a Java desktop chess application with a Processing-based interface and a built-in engine. It supports human vs human, human vs AI, and AI vs AI play, including core chess rules such as castling, en passant, promotion, check, and checkmate.

## Features
- Three game modes: **Player vs Player**, **Player vs AI**, **AI vs AI**
- Drag-and-drop board interaction
- Undo support (`Z`) using full game-state history
- AI search based on minimax with alpha-beta pruning
- Additional AI heuristics: move ordering and evaluation terms (mobility, castling bonus, bishop pair, pawn structure)
- In-game setup menus for side selection and AI depth

## Architecture / Structure
The project is organized into focused packages:

```text
src/
	Board/      # Board state, squares, move objects
	Pieces/     # Piece classes and piece-specific movement rules
	Game/       # Game flow, players, turn handling
		AI/       # Search and evaluation (MiniMax, Evaluate, MoveOrdering)
	RunTime/    # Processing UI and runtime loop (entry point)
	sprites/    # Piece images
lib/          # Processing and JOGL dependencies
```

Key entry point:
- `RunTime.Screen` (`PApplet.main("RunTime.Screen")`)

## Build & Run Instructions

### Prerequisites
- Java 8+ (JDK recommended)
- Processing core libraries (already included in `lib/`)

### Option 1: Run from IntelliJ IDEA
1. Open the project root.
2. Ensure all JARs in `lib/` are added as project dependencies.
3. Run the `RunTime.Screen` main class.

### Option 2: Run from terminal (Linux/macOS)
From the repository root:

```bash
mkdir -p out
find src -name "*.java" > sources.txt
javac -cp "lib/*" -d out @sources.txt
java -cp "out:lib/*" RunTime.Screen
```

For Windows, use `;` instead of `:` in the runtime classpath.

## Testing
This repository does not currently include an automated test suite.

Recommended manual checks:
- Start each game mode and verify turn flow
- Validate legal move generation (including castling and en passant)
- Trigger a promotion and select each replacement piece
- Use `Z` to undo moves repeatedly
- Run AI vs AI at different depths and confirm stable execution

## Project Context
This project is a functional chess game with a "toy" AI implementation intended for learning and experimentation. The UI is complete and playable, while the AI favors clarity over peak strength and can be improved further through search optimizations and stronger evaluation logic.

## Screenshots
![Main UI](https://i.imgur.com/quMhIbL.png)

![Menu UI](https://i.imgur.com/N5R8ITK.png)

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE).
