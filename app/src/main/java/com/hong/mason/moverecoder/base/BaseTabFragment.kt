package com.hong.mason.moverecoder.base

import android.support.v4.app.Fragment

abstract class BaseTabFragment : BaseFragment() {
    abstract fun getTitle(): String
}