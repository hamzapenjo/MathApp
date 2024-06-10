# Math Practice App Documentation
===============================

Introduction
------------

The Math Practice App is designed to help users practice basic math skills. The app randomly generates arithmetic problems and challenges users to solve them within a time limit. This documentation provides an overview of the app's architecture, focusing on the ViewModel and the MainActivity, along with explanations of key Composable functions.

GameViewModel
-------------

The GameViewModel manages the game logic and state. It is responsible for generating math problems, calculating scores, and checking answers.

### Properties

*   **score**: Holds the current score of the user.
    
*   **questionText**: Provides the text of the current arithmetic problem.
    
*   **timeRemaining**: Holds the remaining time in seconds.
    

### Functions

*   **submitAnswer(answer: Double)**: Checks if the provided answer is correct, updates the score, and generates a new problem.
    
*   **restartGame()**: Resets the game state.
    
*   **updateTimeRemaining()**: Decrements the time remaining.
    

MainActivity
------------

The MainActivity sets up the user interface using Jetpack Compose and manages navigation between different screens.

### Function

*   **shareScore(context: Context, message: String)**: Shares the user's score using Android's share functionality.
    

Composable Functions
--------------------

### StartScreen

*   **onStartClick**: Callback to start the game.
    
*   **onAboutClick**: Callback to show the about game screen.
    

### AboutGameScreen

*   **onBackClick**: Callback to return to the start screen.
    

### AppScaffold

*   Manages the navigation between different screens using Jetpack Compose's Scaffold and remember to manage current screen state.
    

### GameScreen

*   **score**: Current score of the user.
    
*   **onScoreChange**: Callback to update the score.
    
*   **onBackClick**: Callback to return to the start screen.
    

Conclusion
----------

The Math Practice App uses the MVVM architecture with Jetpack Compose for UI. It provides a simple interface for users to practice basic math skills by solving arithmetic problems within a time limit. The documentation covers the ViewModel, MainActivity, and key Composable functions to provide a comprehensive overview of the app's structure and functionality.

This concludes the documentation for the Math Practice App. For more detailed code, please refer to the source files.

This documentation provides an overview of the Math Practice App's architecture and functionality. It explains the key components and how they interact to create a seamless user experience for practicing math skills.
