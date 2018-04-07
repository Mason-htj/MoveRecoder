package com.hong.mason.moverecoder.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class AndroidUtils {
    companion object {
        fun showKeyboard(context: Context?, view: View) {
            context?.let {
                val ime: InputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ime.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        fun hideKeyboard(context: Context?, view: View) {
            context?.let {
                val ime: InputMethodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                ime.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            }
        }
    }
}