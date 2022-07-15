package com.example.storyapp.model

import android.content.Context
import android.content.SharedPreferences
import com.example.storyapp.helper.Constanta

class UserPreference(context: Context) {
    private val PREFS_NAME = "user_preferences"
    private val sharedPreferences: SharedPreferences

    val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(Constanta.TOKEN, null)
    }
}