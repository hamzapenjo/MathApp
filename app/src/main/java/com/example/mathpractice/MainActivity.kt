package com.example.mathpractice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathpractice.ui.theme.MathPracticeTheme
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.round
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MathPracticeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScaffold()
                }
            }
        }
    }
}

@Composable
fun StartScreen(onStartClick: () -> Unit, onAboutClick: () -> Unit, onTranslateClick: () -> Unit, isBosnian: Boolean, setIsBosnian: (Boolean) -> Unit) {
    val startText = if (isBosnian) "Idemo" else "Let's Go"
    val aboutText = if (isBosnian) "O igri" else "About Game"
    val translateText = if (isBosnian) "Prevedi" else "Translate"
    val titleText = "Math App"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onStartClick) {
            Text(startText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAboutClick) {
            Text(aboutText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onTranslateClick) {
            Text(translateText)
        }
    }
}

@Composable
fun AboutGameScreen(onBackClick: () -> Unit, isBosnian: Boolean) {
    val backText = if (isBosnian) "Nazad" else "Back"
    val titleText = if (isBosnian) "O igri" else "About the Game"
    val descriptionText = if (isBosnian) {
        "Ova igra je dizajnirana da vam pomogne da vježbate osnovne matematičke vještine. Rješavajte probleme što brže i tačnije kako biste poboljšali svoj rezultat!"
    } else {
        "This game is designed to help you practice basic math skills. Solve the problems as quickly and accurately as possible to improve your score!"
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = descriptionText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackClick) {
            Text(backText)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    var currentScreen by remember { mutableStateOf("StartScreen") }
    var score by remember { mutableStateOf(0) }
    var isBosnian by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (currentScreen == "GameScreen") {
                TopAppBar(
                    title = { Text( "Math App") },
                    actions = {
                        IconButton(onClick = {
                            shareScore(
                                context,
                                if (isBosnian) "Postigao sam $score poena u aplikaciji za vježbanje matematike!" else "I scored $score points in Math Practice app!"
                            )
                        }) {
                            Icon(Icons.Filled.Share, contentDescription = "Share")
                        }
                    }
                )
            }
        },
        content = {
            when (currentScreen) {
                "StartScreen" -> StartScreen(
                    onStartClick = { currentScreen = "GameScreen" },
                    onAboutClick = { currentScreen = "AboutGameScreen" },
                    onTranslateClick = { isBosnian = !isBosnian },
                    isBosnian = isBosnian,
                    setIsBosnian = { isBosnian = it }
                )
                "GameScreen" -> GameScreen(
                    score = score,
                    onScoreChange = { newScore -> score = newScore },
                    onBackClick = { currentScreen = "StartScreen" },
                    isBosnian = isBosnian
                )
                "AboutGameScreen" -> AboutGameScreen(onBackClick = { currentScreen = "StartScreen" }, isBosnian = isBosnian)
                "GameOverScreen" -> GameOverScreen(
                    score = score,
                    onRestartClick = {
                        score = 0
                        currentScreen = "GameScreen"
                    },
                    isBosnian = isBosnian
                )
            }
        }
    )
}

@Composable
fun GameScreen(score: Int, onScoreChange: (Int) -> Unit, onBackClick: () -> Unit, isBosnian: Boolean) {
    var num1 by remember { mutableIntStateOf(Random.nextInt(1, 10)) }
    var num2 by remember { mutableIntStateOf(Random.nextInt(1, 10)) }
    var answer by remember { mutableStateOf(TextFieldValue("")) }
    var timeRemaining by remember { mutableStateOf(60) }
    val context = LocalContext.current

    var operator by remember { mutableStateOf(getRandomOperator()) }
    var currentScreen by remember { mutableStateOf("GameScreen") }

    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1000L)
            timeRemaining--
        }
        currentScreen = "GameOverScreen"
    }

    if (currentScreen == "GameOverScreen") {
        GameOverScreen(
            score = score,
            onRestartClick = {
                onScoreChange(0)
                num1 = Random.nextInt(1, 10)
                num2 = Random.nextInt(1, 10)
                operator = getRandomOperator()
                answer = TextFieldValue("")
                timeRemaining = 60
                currentScreen = "GameScreen"
            },
            isBosnian = isBosnian
        )
        return
    }

    val questionText = when (operator) {
        '+' -> "$num1 + $num2 = ?"
        '-' -> "$num1 - $num2 = ?"
        '*' -> "$num1 * $num2 = ?"
        '/' -> {
            if (num1 < num2) {
                "$num2 / $num1 = ?"
            } else {
                "$num1 / $num2 = ?"
            }
        }
        else -> "$num1 + $num2 = ?"
    }
    val restartText = if (isBosnian) "Ponovo pokreni igru" else "Restart Game"
    val backText = if (isBosnian) "Nazad" else "Back"
    val correctToastText = if (isBosnian) "Tačno!" else "Correct!"
    val incorrectToastText = if (isBosnian) "Netačno!" else "Incorrect!"
    val scoreText = if (isBosnian) "Rezultat: $score" else "Score: $score"
    val timeRemainingText = if (isBosnian) "Preostalo vrijeme: $timeRemaining" else "Time remaining: $timeRemaining"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 23.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = questionText, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text(if (isBosnian) "Vaš odgovor" else "Your answer") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = scoreText, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = timeRemainingText, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val correctAnswer = calculateCorrectAnswer(num1, num2, operator)
            val userAnswer = answer.text.toDoubleOrNull()
            if (userAnswer != null && isAnswerCorrect(userAnswer, correctAnswer, operator)) {
                val newScore = score + 1
                onScoreChange(newScore)
                Toast.makeText(context, correctToastText, Toast.LENGTH_SHORT).show()
            } else {
                val newScore = score - 1
                onScoreChange(newScore)
                Toast.makeText(context, incorrectToastText, Toast.LENGTH_SHORT).show()
            }
            num1 = Random.nextInt(1, 10)
            num2 = Random.nextInt(1, 10)
            operator = getRandomOperator()
            answer = TextFieldValue("")
        }) {
            Text(if (isBosnian) "Pošalji!" else "Submit!")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onScoreChange(0)
            num1 = Random.nextInt(1, 10)
            num2 = Random.nextInt(1, 10)
            operator = getRandomOperator()
            answer = TextFieldValue("")
            timeRemaining = 60
        }) {
            Text(restartText)
        }
        Button(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(backText)
        }
    }
}

@Composable
fun GameOverScreen(score: Int, onRestartClick: () -> Unit, isBosnian: Boolean) {
    val gameOverText = if (isBosnian) "Igra gotova!" else "Game Over!"
    val scoreText = if (isBosnian) "Rezultat: $score" else "Score: $score"
    val restartText = if (isBosnian) "Ponovo pokreni igru" else "Restart Game"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = gameOverText,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = scoreText,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onRestartClick) {
            Text(restartText)
        }
    }
}

fun getRandomOperator(): Char {
    val operators = arrayOf('+', '-', '*', '/')
    return operators.random()
}

fun calculateCorrectAnswer(num1: Int, num2: Int, operator: Char): Double {
    return when (operator) {
        '+' -> (num1 + num2).toDouble()
        '-' -> (num1 - num2).toDouble()
        '*' -> (num1 * num2).toDouble()
        '/' -> (num1.toDouble() / num2.toDouble())
        else -> (num1 + num2).toDouble()
    }
}

fun isAnswerCorrect(userAnswer: Double, correctAnswer: Double, operator: Char): Boolean {
    val precision = 1e-6
    return when (operator) {
        '/' -> abs(userAnswer - correctAnswer) < precision
        else -> round(userAnswer * 100) == round(correctAnswer * 100)
    }
}

fun shareScore(context: Context, message: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MathPracticeTheme {
        AppScaffold()
    }
}