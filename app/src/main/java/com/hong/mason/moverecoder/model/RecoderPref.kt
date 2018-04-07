package com.hong.mason.moverecoder.model

import android.content.Context
import android.content.SharedPreferences

class RecoderPref(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setStartTime(time: Long) {
        val editor = pref.edit()
        editor.putLong(PREF_START_TIME, time)
        editor.apply()
    }

    fun getStartTime(): Long {
        return pref.getLong(PREF_START_TIME, 0)
    }

    companion object {
        private const val PREF_NAME = "recoder"
        private const val PREF_START_TIME = "start_time"
    }
}