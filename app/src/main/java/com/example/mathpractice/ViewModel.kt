package com.example.mathpractice

import androidx.lifecycle.ViewModel
import kotlin.math.round
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private var _num1 = Random.nextInt(1, 10)
    private var _num2 = Random.nextInt(1, 10)
    private var _operator = getRandomOperator()

    private var _timeRemaining = 60

    val questionText: String
        get() = when (_operator) {
            '+' -> "$_num1 + $_num2 = ?"
            '-' -> "$_num1 - $_num2 = ?"
            '*' -> "$_num1 * $_num2 = ?"
            '/' -> "$_num1 / $_num2 = ?"
            else -> "$_num1 + $_num2 = ?"
        }

    val timeRemaining: Int
        get() = _timeRemaining

    fun submitAnswer(answer: Double): Boolean {
        val correctAnswer = calculateCorrectAnswer(_num1, _num2, _operator)
        val isCorrect = isAnswerCorrect(answer, correctAnswer)
        if (isCorrect) {
            _score++
            _num1 = Random.nextInt(1, 10)
            _num2 = Random.nextInt(1, 10)
            _operator = getRandomOperator()
            _timeRemaining = 60
        }
        return isCorrect
    }

    fun restartGame() {
        _score = 0
        _num1 = Random.nextInt(1, 10)
        _num2 = Random.nextInt(1, 10)
        _operator = getRandomOperator()
        _timeRemaining = 60
    }

    fun updateTimeRemaining() {
        _timeRemaining--
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
}
