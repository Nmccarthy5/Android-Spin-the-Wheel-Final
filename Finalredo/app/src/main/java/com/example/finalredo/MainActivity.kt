package com.example.finalredo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var namesEditText: EditText
    private lateinit var spinButton: Button
    private lateinit var spinWheel: SpinWheelView
    private lateinit var resultTextView: TextView
    private lateinit var btnPreferences: Button
    private lateinit var btnHelp: Button
    private lateinit var savedListsSpinner: Spinner
    private lateinit var saveListButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        namesEditText = findViewById(R.id.namesEditText)
        spinButton = findViewById(R.id.spinButton)
        spinWheel = findViewById(R.id.spinWheel)
        resultTextView = findViewById(R.id.resultTextView)
        btnPreferences = findViewById(R.id.btnPreferences)
        btnHelp = findViewById(R.id.btnHelp)
        savedListsSpinner = findViewById(R.id.savedListsSpinner)
        saveListButton = findViewById(R.id.saveListButton)

        // Load spinner data
        updateSavedListsToSpinner()

        spinButton.setOnClickListener {
            val names = namesEditText.text.toString().split(",").map { it.trim() }.filter { it.isNotEmpty() }
            spinWheel.updateSegments(names)
            spinWheel.spinWheel()
        }

        spinWheel.setOnSegmentSelectedListener(object : SpinWheelView.OnSegmentSelectedListener {
            override fun onSegmentSelected(segmentIndex: Int) {
                val selectedName = spinWheel.segments[segmentIndex]
                resultTextView.text = selectedName
            }
        })

        btnPreferences.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        btnHelp.setOnClickListener {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
        }

        savedListsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedList = savedListsSpinner.selectedItem.toString()
                namesEditText.setText(selectedList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        saveListButton.setOnClickListener {
            saveCurrentListToPreferences()
            updateSavedListsToSpinner()
        }
    }
    override fun onResume() {
        super.onResume()
        updateSavedListsToSpinner()
    }
    private fun updateSavedListsToSpinner() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val allEntries: Map<String, *> = sharedPreferences.all
        val savedLists = allEntries.keys.filter { it.startsWith("list_") }.map { sharedPreferences.getString(it, "") }.filterNotNull()

        if(savedLists.isEmpty()) {
            namesEditText.setText("")  // Clear the EditText if no saved list is found
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, savedLists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        savedListsSpinner.adapter = adapter
    }

    private fun saveCurrentListToPreferences() {
        val listName = "list_${System.currentTimeMillis()}"
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        with(sharedPreferences.edit()) {
            putString(listName, namesEditText.text.toString())
            apply()
        }
    }
}