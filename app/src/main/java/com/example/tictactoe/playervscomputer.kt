package com.example.tictactoe

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import kotlin.random.Random

class playervscomputer : Activity() {

    private lateinit var textViewPlayer: TextView
    private lateinit var gridLayoutBoard: GridLayout

    private var currentPlayer = 1
    private val playerMarks = arrayOf("", "X", "O")
    private val board = Array(3) { IntArray(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playervscomputer) // Replace "your_layout_file_name" with your layout file name


        gridLayoutBoard = findViewById(R.id.gridLayoutBoard)

        // Get player name from Intent
        val playerName = intent.getStringExtra("PLAYER_NAME") ?: "Player"

        // Set player name to text view
        textViewPlayer.text = "$playerName's turn"

        initializeBoard()

        // Set initial player
        currentPlayer = 1
        updatePlayerTurnText()

        // Set listener for buttons
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClick(button, i / 3, i % 3)
            }
        }

        // Set listener for "Restart Game" button
        val buttonRestartGame = findViewById<Button>(R.id.buttonRestartGame)
        buttonRestartGame.setOnClickListener {
            restartGame()
        }
    }

    private fun initializeBoard() {
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.text = ""
            board[i / 3][i % 3] = 0
        }
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isEmpty()) {
            button.text = playerMarks[currentPlayer]
            board[row][col] = currentPlayer
            if (checkWinner(row, col)) {
                // Winning combination
                val winner = if (currentPlayer == 1) "Player" else "Computer"
                textViewPlayer.text = "$winner wins!"
                disableAllButtons()
            } else if (isBoardFull()) {
                // Draw
                textViewPlayer.text = "It's a draw!"
            } else {
                // Switch player
                currentPlayer = if (currentPlayer == 1) 2 else 1
                if (currentPlayer == 2) {
                    computerTurn()
                }
                updatePlayerTurnText()
            }
        }
    }

    private fun computerTurn() {
        // Computer's turn
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val randomIndex = Random.nextInt(emptyCells.size)
            val (row, col) = emptyCells[randomIndex]
            val button = gridLayoutBoard.getChildAt(row * 3 + col) as Button
            button.performClick()
        }
    }

    private fun checkWinner(row: Int, col: Int): Boolean {
        // Check rows and columns
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) return true
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) return true

        // Check diagonals
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) return true
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) return true

        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in 0..2) {
            for (col in 0..2) {
                if (board[row][col] == 0) return false
            }
        }
        return true
    }

    private fun updatePlayerTurnText() {
        val playerTurnText = if (currentPlayer == 1) "Player's turn" else "Computer's turn"
        textViewPlayer.text = playerTurnText
    }

    private fun disableAllButtons() {
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.isEnabled = false
        }
    }

    private fun restartGame() {
        // Reset game to initial state
        initializeBoard()
        currentPlayer = 1
        textViewPlayer.text = "Player's turn"
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.isEnabled = true
        }
    }
}
