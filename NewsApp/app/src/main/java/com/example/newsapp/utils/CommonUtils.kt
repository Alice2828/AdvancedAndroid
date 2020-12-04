package com.example.newsapp.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object CommonUtils {
    private val NIGHT_MODE = "NIGHT_MODE"
    private val TOOGLE = "TOOGLE"

    fun isNightModeEnabled(context: Context): Boolean {
        val mPrefs: SharedPreferences = context.getSharedPreferences("MY_PREF", MODE_PRIVATE)
        return mPrefs.getBoolean(NIGHT_MODE, false)
    }

    fun setIsNightModeEnabled(context: Context, isNightModeEnabled: Boolean) {
        val mPrefs: SharedPreferences = context.getSharedPreferences("MY_PREF", MODE_PRIVATE)
        val editor = mPrefs.edit()
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled)
        editor.apply()
    }
}