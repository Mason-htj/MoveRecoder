package com.hong.mason.moverecoder.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {
    abstract fun getLayoutResourceId(): Int
    abstract fun initView(view: View)
    abstract fun initArguments(args: Bundle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            initArguments(arguments)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutResourceId(), container, false)
        if (view != null) {
            initView(view)
        }
        return view
    }
}