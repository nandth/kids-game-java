# Mathematical Quiz Engine

An elegant demonstration of fundamental Object-Oriented Programming principles in Java, showcasing clean architecture, separation of concerns, and practical design patterns through an interactive mathematical quiz application.

## Overview

This project exemplifies core OOP concepts through a well-structured application that separates business logic, data models, and presentation layers. The architecture demonstrates proper encapsulation, single responsibility principle, and thoughtful abstraction.

## Technical Architecture

### Core Components

**Game Engine (`Game.java`)**
- Encapsulates all quiz logic and state management
- Implements the Strategy pattern through dynamic question generation
- Provides clean separation between game mechanics and presentation
- Demonstrates proper use of access modifiers (public API vs private implementation)

**Player Model (`Player.java`)**
- Pure data model following the JavaBean convention
- Encapsulates player state with controlled access through getters
- Demonstrates proper constructor design and initialization patterns

**Presentation Layer (`KidsGameGUI.java`)**
- Implements the MVC pattern with clear separation of concerns
- Leverages composition over inheritance for UI components
- Demonstrates event-driven programming and observer patterns
- Features responsive design with CardLayout for state management

## Object-Oriented Principles Demonstrated

### Encapsulation
- Private fields with public interfaces
- Information hiding through controlled access
- Immutable design where appropriate

### Abstraction
- Clean separation between interface and implementation
- Game logic abstracted from presentation concerns
- Question generation algorithms hidden behind simple public methods

### Modularity
- Single Responsibility Principle throughout
- Each class has a clearly defined purpose
- Easy to test, extend, and maintain

### Polymorphism
- Event handling through ActionListener interfaces
- Dynamic method dispatch in question generation
- Flexible design supporting multiple game modes

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

Swing was selected as the GUI framework due to its comprehensive component library, mature event handling system, and straightforward integration with core Java. Its robust architecture provides an excellent foundation for demonstrating OOP principles without the overhead of external dependencies or complex build configurations.

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

- **Strategy Pattern**: Question generation methods
- **MVC Pattern**: Clear separation of model, view, and controller
- **Observer Pattern**: Event listeners for user interactions
- **Singleton Behavior**: Game state management
- **Factory Method**: Dynamic question creation

## Educational Value

This codebase serves as an excellent reference for:
- Clean code organization in Java
- Practical application of OOP principles
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

*A demonstration of elegant software design through practical application.*
