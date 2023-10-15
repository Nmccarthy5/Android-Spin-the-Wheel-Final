package com.example.finalredo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var namesEditText: EditText
    private lateinit var spinButton: Button
    private lateinit var spinWheel: SpinWheelView
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        namesEditText = findViewById(R.id.namesEditText)
        spinButton = findViewById(R.id.spinButton)
        spinWheel = findViewById(R.id.spinWheel)
        resultTextView = findViewById(R.id.resultTextView)

        spinButton.setOnClickListener {
            spinWheel.spinWheel()
        }
    }

}