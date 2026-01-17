# Mathematical Quiz Engine

A Java application exploring fundamental Object-Oriented Programming concepts through an interactive mathematical quiz system with multiple game modes and multiplayer support.

## Overview

The project demonstrates separation of concerns across business logic, data models, and presentation layers. The implementation focuses on practical application of OOP principles including encapsulation, modularity, and event-driven design.

## Technical Architecture

### Core Components

**Game Engine (`Game.java`)**
- Manages quiz logic and state
- Dynamic question generation across multiple operation types
- Validation logic for user answers with tolerance for floating-point operations
- Private methods for question generation maintain implementation flexibility

**Player Model (`Player.java`)**
- Data model for player state and scoring
- Tracks individual performance across game sessions
- Simple interface for data access and updates

**Presentation Layer (`KidsGameGUI.java`)**
- Swing-based user interface with CardLayout state management
- Event-driven architecture using ActionListener patterns
- Component composition for modular UI construction
- Custom rendering for background graphics and themed styling

## Object-Oriented Principles

### Encapsulation
- State management within appropriate class boundaries
- Interface methods provide controlled access to game state
- Question generation logic hidden from external callers

### Abstraction
- Game logic separated from presentation concerns
- Question types implemented through distinct private methods
- UI components abstract away Swing implementation details

### Modularity
- Clear separation of responsibilities across classes
- Game state independent of player tracking
- Each component maintains focused purpose

### Polymorphism
- Interface-based event handling
- Dynamic behavior through method selection in question generation
- Extensible design supports additional game modes

## Features

### Game Modes
1. **Make a Wish** - Complete a specified number of questions
2. **No Mistakes** - Survive with zero errors tolerance
3. **Take Chances** - Limited lives system (three strikes)
4. **Time Trial** - Race against the clock

### Question Types
- Addition and subtraction operations
- Multiplication tables
- Division with decimal precision
- Dynamic difficulty adjustment

### Multiplayer Support
- Turn-based competitive gameplay
- Leaderboard with automatic ranking
- Individual score tracking and summaries

## Technology Stack

**Framework**: Java Swing

Swing was selected for its comprehensive component library, mature event model, and straightforward integration with core Java. The framework provides a solid foundation for GUI development without external dependencies.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code with Java extensions)

### Building and Running

#### Using Command Line
```bash
# Compile
javac *.java

# Run
java KidsGameGUI
```

#### Using IDE
1. Import the project into your IDE
2. Ensure the JDK is properly configured
3. Run `KidsGameGUI.java` as the main class

### Project Structure
```
.
├── Game.java           # Core game logic and question engine
├── Player.java         # Player data model
├── KidsGameGUI.java    # Presentation layer and main entry point
└── minecraft_bg.jpg    # Visual assets
```

## Design Patterns

- **Strategy Pattern**: Question generation through private method selection
- **Observer Pattern**: Event listeners for user interactions
- **Composition**: UI built from assembled components rather than inheritance hierarchies
- **State Management**: CardLayout for managing application flow

## Educational Value

The codebase serves as a reference for:
- Code organization in Java
- Application of OOP principles
- Event-driven programming patterns
- GUI development with Swing
- State management in interactive applications

## Future Enhancements

The modular architecture supports easy extension:
- Additional question types (geometry, algebra)
- Persistent storage for high scores
- Difficulty progression algorithms
- Network multiplayer capabilities
- Plugin architecture for custom question generators

## License

This project is available for educational and reference purposes.

---

*A demonstration of software design through practical application.*
