# math-practice
Math Practice App Documentation
===============================

Table of Contents
-----------------

*   [Introduction](#introduction)
    
*   [GameViewModel](#gameviewmodel)
    
    *   [Properties](#properties)
        
    *   [Functions](#functions)
        
*   [MainActivity](#mainactivity)
    
*   [Composable Functions](#composable-functions)
    
    *   [StartScreen](#startscreen)
        
    *   [AboutGameScreen](#aboutgamescreen)
        
    *   [AppScaffold](#appscaffold)
        
    *   [GameScreen](#gamescreen)
        
*   [Conclusion](#conclusion)
    

Introduction
------------

The Math Practice App is designed to help users practice basic math skills. The app randomly generates arithmetic problems and challenges users to solve them within a time limit. This documentation provides an overview of the app's architecture, focusing on the ViewModel and the MainActivity, along with explanations of key Composable functions.

GameViewModel
-------------

The GameViewModel manages the game logic and state. It is responsible for generating math problems, calculating scores, and checking answers.

### Propertiesprivate var \_score = 0val score: Int get() = \_scoreprivate var \_num1 = Random.nextInt(1, 10)private var \_num2 = Random.nextInt(1, 10)private var \_operator = getRandomOperator()private var \_timeRemaining = 60**score**: Holds the current score of the user.

*   **questionText**: Provides the text of the current arithmetic problem.
    
*   **timeRemaining**: Holds the remaining time in seconds.
    

Functions

fun submitAnswer(answer: Double): Booleanfun restartGame()fun updateTimeRemaining()

*   **submitAnswer**: Checks if the provided answer is correct, updates the score, and generates a new problem.
    
*   **restartGame**: Resets the game state.
    
*   **updateTimeRemaining**: Decrements the time remaining.
    

MainActivity
------------

The MainActivity sets up the user interface using Jetpack Compose and manages navigation between different screens.

### Setupoverride fun onCreate(savedInstanceState: Bundle?) {

super.onCreate(savedInstanceState)

enableEdgeToEdge()

setContent {

MathPracticeTheme {

Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {

AppScaffold()

}

}

}

}

Functionfun shareScore(context: Context, message: String)

*   **shareScore**: Shares the user's score using Android's share functionality.
    

Composable Functions
--------------------

### StartScreen

@Composablefun StartScreen(onStartClick: () -> Unit, onAboutClick: () -> Unit)

*   **onStartClick**: Callback to start the game.
    
*   **onAboutClick**: Callback to show the about game screen.
    

### AboutGameScreen

@Composablefun AboutGameScreen(onBackClick: () -> Unit)

*   **onBackClick**: Callback to return to the start screen.
    

### AppScaffold

@Composablefun AppScaffold()

*   Manages the navigation between different screens using Jetpack Compose's Scaffold and remember to manage current screen state.
    

### GameScreen

@Composablefun GameScreen(score: Int, onScoreChange: (Int) -> Unit, onBackClick: () -> Unit)

*   **score**: Current score of the user.
    
*   **onScoreChange**: Callback to update the score.
    
*   **onBackClick**: Callback to return to the start screen.
    

Conclusion
----------

The Math Practice App uses the MVVM architecture with Jetpack Compose for UI. It provides a simple interface for users to practice basic math skills by solving arithmetic problems within a time limit. The documentation covers the ViewModel, MainActivity, and key Composable functions to provide a comprehensive overview of the app's structure and functionality.
