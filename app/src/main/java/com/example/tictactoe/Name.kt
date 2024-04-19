package com.example.tictactoe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Name : Activity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.name_activity)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("player_names", Context.MODE_PRIVATE)

        // Get references to UI components
        val editTextPlayer1: EditText = findViewById(R.id.editTextPlayer1)
        val editTextPlayer2: EditText = findViewById(R.id.editTextPlayer2)
        val buttonStartGame: Button = findViewById(R.id.buttonStartGame)

        // Load previously saved names if any
        editTextPlayer1.setText(sharedPreferences.getString("PLAYER1_NAME", ""))
        editTextPlayer2.setText(sharedPreferences.getString("PLAYER2_NAME", ""))

        // Button "Start Game" click listener
        buttonStartGame.setOnClickListener {
            // Get entered player names
            val player1Name = editTextPlayer1.text.toString()
            val player2Name = editTextPlayer2.text.toString()

            // Save player names to SharedPreferences
            sharedPreferences.edit().apply {
                putString("PLAYER1_NAME", player1Name)
                putString("PLAYER2_NAME", player2Name)
                apply()
            }

            // Create Intent to navigate to PlayerVsPlayerActivity
            val intent = Intent(this, playervsplayer::class.java).apply {
                // Pass player names to the new activity
                putExtra("PLAYER1_NAME", player1Name)
                putExtra("PLAYER2_NAME", player2Name)
            }
            startActivity(intent)
        }
    }
}
