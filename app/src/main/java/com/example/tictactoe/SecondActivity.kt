package com.example.tictactoe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class SecondActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.slide2_activity)

        val buttonPlayComputer: Button = findViewById(R.id.buttonPlayComputer)
        val buttonPlayHuman: Button = findViewById(R.id.buttonPlayHuman)

       buttonPlayComputer.setOnClickListener {
                    val intent = Intent(this, playervscomputer::class.java)
                    startActivity(intent)
               }
        buttonPlayHuman.setOnClickListener {
            val intent = Intent(this, Name::class.java)
            startActivity(intent)
        }
    }
}
