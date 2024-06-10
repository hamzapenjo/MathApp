package com.example.mathpractice

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathpractice.ui.theme.MathPracticeTheme
import kotlin.math.round
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
}

@Composable
fun StartScreen(onStartClick: () -> Unit, onAboutClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Math App",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onStartClick) {
            Text("Let's Go")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onAboutClick) {
            Text("About Game")
        }
    }
}

@Composable
fun AboutGameScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "About the Game",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "This game is designed to help you practice basic math skills. Solve the problems as quickly and accurately as possible to improve your score!",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf("StartScreen") }
    val (score, setScore) = remember { mutableStateOf(0) }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (currentScreen == "GameScreen") {
                TopAppBar(
                    title = { Text("Math App") },
                    actions = {
                        IconButton(onClick = {
                            shareScore(context, "I scored $score points in Math Practice app!")
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
                    onStartClick = { setCurrentScreen("GameScreen") },
                    onAboutClick = { setCurrentScreen("AboutGameScreen") }
                )
                "GameScreen" -> GameScreen(
                    score = score,
                    onScoreChange = { newScore -> setScore(newScore) },
                    onBackClick = { setCurrentScreen("StartScreen") }
                )
                "AboutGameScreen" -> AboutGameScreen(onBackClick = { setCurrentScreen("StartScreen") })
            }
        }
    )
}


fun shareScore(context: Context, message: String) {
    val intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, message)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}

@Composable
fun GameScreen(score: Int, onScoreChange: (Int) -> Unit, onBackClick: () -> Unit) {
    var num1 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var num2 by remember { mutableStateOf(Random.nextInt(1, 10)) }
    var answer by remember { mutableStateOf(TextFieldValue("")) }
    var timeRemaining by remember { mutableStateOf(60) }
    val context = LocalContext.current

    var operator by remember { mutableStateOf(getRandomOperator()) }

    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            kotlinx.coroutines.delay(1000L)
            timeRemaining--
        }
        Toast.makeText(context, "Time's up!", Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        val questionText = when (operator) {
            '+' -> "$num1 + $num2 = ?"
            '-' -> "$num1 - $num2 = ?"
            '*' -> "$num1 * $num2 = ?"
            '/' -> "$num1 / $num2 = ?"
            else -> "$num1 + $num2 = ?"
        }

        Text(text = questionText, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Your answer") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val correctAnswer = calculateCorrectAnswer(num1, num2, operator)
            val userAnswer = answer.text.toDoubleOrNull()
            if (userAnswer != null && isAnswerCorrect(userAnswer, correctAnswer)) {
                val newScore = score + 1
                onScoreChange(newScore)
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
                num1 = Random.nextInt(1, 10)
                num2 = Random.nextInt(1, 10)
                operator = getRandomOperator()
                answer = TextFieldValue("")
            } else {
                Toast.makeText(context, "Try again!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Score: $score", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Time remaining: $timeRemaining", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onScoreChange(0)
            num1 = Random.nextInt(1, 10)
            num2 = Random.nextInt(1, 10)
            operator = getRandomOperator()
            answer = TextFieldValue("")
            timeRemaining = 60
        }) {
            Text("Restart Game")
        }
        Button(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Back")
        }
    }
}

private fun getRandomOperator(): Char {
    val operators = arrayOf('+', '-', '*', '/')
    return operators.random()
}

private fun calculateCorrectAnswer(num1: Int, num2: Int, operator: Char): Double {
    return when (operator) {
        '+' -> (num1 + num2).toDouble()
        '-' -> (num1 - num2).toDouble()
        '*' -> (num1 * num2).toDouble()
        '/' -> (num1.toDouble() / num2.toDouble())
        else -> (num1 + num2).toDouble()
    }
}

private fun isAnswerCorrect(userAnswer: Double, correctAnswer: Double): Boolean {
    return if (userAnswer == correctAnswer) {
        true
    } else {
        val roundedCorrectAnswer = round(correctAnswer * 10) / 10
        val roundedUserAnswer = round(userAnswer * 10) / 10
        roundedUserAnswer == roundedCorrectAnswer
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MathPracticeTheme {
        AppScaffold()
    }
}