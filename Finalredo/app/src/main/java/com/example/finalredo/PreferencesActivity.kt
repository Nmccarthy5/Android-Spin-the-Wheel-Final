package com.example.finalredo

import android.os.Bundle
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceManager

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            val deleteListPreference = findPreference<Preference>("delete_saved_list")
            deleteListPreference?.setOnPreferenceClickListener {
                deleteSavedList()
                true
            }
        }

        private fun deleteSavedList() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context ?: return)
            val allEntries: Map<String, *> = sharedPreferences.all
            val listKeys = allEntries.keys.filter { it.startsWith("list_") }

            with(sharedPreferences.edit()) {
                for (key in listKeys) {
                    remove(key)
                }
                apply()
            }
        }
    } // Closing brace for the SettingsFragment

    // Handle the Up button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}