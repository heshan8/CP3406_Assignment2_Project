package com.example.booktracker.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ThemePreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("dark_mode", false) // Default to light mode
    }

    fun setDarkMode(isDark: Boolean) {
        prefs.edit { putBoolean("dark_mode", isDark) }
    }
}