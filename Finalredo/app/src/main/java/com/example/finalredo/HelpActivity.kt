package com.example.finalredo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        // Find the button by its ID
        val backToMainButton: Button = findViewById(R.id.backToMainButton)

        // Set an OnClickListener for the button to go back to MainActivity
        backToMainButton.setOnClickListener {
            finish()
        }

        // Additional setup if needed
    }
}