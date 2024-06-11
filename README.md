Math Practice App
=======================================================

Introduction
------------

This document provides an architectural overview and outlines the functionality of various classes within the Math Practice Android application. It also covers essential Android framework concepts such as activities and lifecycle management.

Application Architecture
------------------------

The Math Practice app is built using Kotlin and Jetpack Compose. It consists of several key components and follows the MVVM (Model-View-ViewModel) architecture pattern.

### Components

1.  **Model**

    -   The `MainActivity` serves as the entry point and sets up the UI using Jetpack Compose.
    -   Various classes like `GameScreen`, `StartScreen`, and `AboutGameScreen` handle different UI screens and their logic.
2.  **View**

    -   Composable functions (`@Composable`) define the UI components.
    -   Screens such as `StartScreen`, `GameScreen`, `AboutGameScreen`, and `GameOverScreen` are implemented using Jetpack Compose's declarative UI approach.
3.  **ViewModel**

    -   State management is handled using Jetpack Compose's `remember` and `mutableStateOf` functions.
    -   `AppScaffold` manages the state and navigation between different screens (`StartScreen`, `GameScreen`, `AboutGameScreen`, `GameOverScreen`).

### Navigation

Navigation between screens (`StartScreen`, `GameScreen`, `AboutGameScreen`, `GameOverScreen`) is managed through state variables (`currentScreen` in `AppScaffold`) and button clicks.

### Data Flow

Data flow is unidirectional:

-   User interactions trigger state changes.
-   State changes update the UI.
-   Composable functions reflect the updated state.

### Integration with Android Framework

The app integrates seamlessly with the Android framework:

-   Activities (`ComponentActivity` in `MainActivity`) and lifecycles are managed according to Android's standards.
-   Contexts (`LocalContext.current`) are used for accessing Android system services.

Functionality of Individual Classes
-----------------------------------

### `MainActivity`

-   Entry point of the app.
-   Sets up the UI using Jetpack Compose.
-   Initializes the theme and primary screen (`AppScaffold`).

### `StartScreen`

-   Initial screen users see.
-   Allows users to start the game, view information about the game, and toggle between languages.
-   Buttons trigger navigation to different screens (`GameScreen`, `AboutGameScreen`).

### `GameScreen`

-   Main game screen.
-   Generates math questions (`+`, `-`, `*`, `/`) with random numbers.
-   Tracks score, time remaining, and user responses.
-   Validates user responses and updates score.
-   Navigates to `GameOverScreen` when time is up.

### `AboutGameScreen`

-   Provides information about the game's purpose and how to play.
-   Users can return to the `StartScreen`.

### `GameOverScreen`

-   Displays the final score when the game ends.
-   Allows users to restart the game.

### Supporting Functions and Classes

-   `getRandomOperator()`: Returns a random math operator (`+`, `-`, `*`, `/`).
-   `calculateCorrectAnswer()`: Calculates the correct answer based on the operator.
-   `isAnswerCorrect()`: Checks if the user's answer matches the correct answer.
-   `shareScore()`: Shares the user's score using Android's sharing functionality.

Android Framework Concepts
--------------------------

### Activity Lifecycle

-   `MainActivity` and other activities adhere to the Android activity lifecycle.
-   Lifecycle methods like `onCreate()`, `onStart()`, `onResume()`, etc., are implemented in `MainActivity`.

### Context

-   `LocalContext.current` is used to access the current Android context.
-   Context is crucial for accessing system services (`Toast.makeText()` for notifications).

### Material Design and Compose

-   The app follows Material Design principles with Compose components.
-   `MaterialTheme` and `Surface` are used for styling and theming.

Conclusion
----------

The Math Practice app showcases the power of Jetpack Compose for building engaging and interactive UIs. It adheres to the MVVM architecture pattern for separation of concerns and follows best practices for Android app development.

This documentation provides a comprehensive overview of the app's architecture, functionality of individual classes, and integration with essential Android framework concepts.
