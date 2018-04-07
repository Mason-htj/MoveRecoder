package com.hong.mason.moverecoder.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseDialogFragment : DialogFragment() {
    abstract fun initArguments(args: Bundle)
    abstract fun initView(view: View)
    abstract fun getLayoutResourceId(): Int
    abstract fun open()
    abstract fun close()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { initArguments(it) }
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutResourceId(), container, false)
        view?.let { initView(view) }
        open()
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnKeyListener { _, code, _ ->
            if (code == KeyEvent.KEYCODE_BACK) {
                if (isCancelable) close()
                true
            } else false
        }
        return dialog
    }
}