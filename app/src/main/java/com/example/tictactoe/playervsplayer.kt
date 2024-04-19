package com.example.tictactoe

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class playervsplayer : Activity() {

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView
    private lateinit var gridLayoutBoard: GridLayout

    private var currentPlayer = 1
    private val playerMarks = arrayOf("", "X", "O")
    private val board = Array(3) { IntArray(3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.playervsplayerttt)


        textViewPlayer1 = findViewById(R.id.textViewPlayer1)
        textViewPlayer2 = findViewById(R.id.textViewPlayer2)
        gridLayoutBoard = findViewById(R.id.gridLayoutBoard)

        // Getting player names from Intent
        val player1Name = intent.getStringExtra("PLAYER1_NAME") ?: "Player 1"
        val player2Name = intent.getStringExtra("PLAYER2_NAME") ?: "Player 2"

        // Setting player names to text views
        textViewPlayer1.text = "$player1Name's turn"
        textViewPlayer2.text = ""

        initializeBoard()

        // Setting initial player
        currentPlayer = 1
        updatePlayerTurnText()

        // Assigning click listeners to buttons
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.setOnClickListener {
                onButtonClick(button, i / 3, i % 3)
            }
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
                val winner = if (currentPlayer == 1) "Player 1" else "Player 2"
                textViewPlayer1.text = "$winner wins!"
                textViewPlayer2.text = ""
                disableAllButtons()
            } else if (isBoardFull()) {
                // Draw
                textViewPlayer1.text = "It's a draw!"
                textViewPlayer2.text = ""
            } else {
                // Switching players
                currentPlayer = if (currentPlayer == 1) 2 else 1
                updatePlayerTurnText()
            }
        }
    }

    private fun checkWinner(row: Int, col: Int): Boolean {
        // Checking rows and columns
        if (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) return true
        if (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) return true

        // Checking diagonals
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
        val playerTurnText = if (currentPlayer == 1) "Player 1's turn" else "Player 2's turn"
        textViewPlayer1.text = playerTurnText
        textViewPlayer2.text = ""
    }

    private fun disableAllButtons() {
        for (i in 0 until gridLayoutBoard.childCount) {
            val button = gridLayoutBoard.getChildAt(i) as Button
            button.isEnabled = false
        }
    }
    private fun restartGame() {
        // Returning to initial state
        initializeBoard()
        currentPlayer = 1
        textViewPlayer1.text = "Player 1's turn"
        textViewPlayer2.text = ""
    }
}
