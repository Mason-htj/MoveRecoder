package com.hong.mason.moverecoder.view.history

import android.os.Bundle
import android.view.View
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment

class HistoryTabFragment : BaseTabFragment() {
    override fun getTitle(): String {
        return "History"
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.tab_fragment_history
    }

    override fun initView(view: View) {
    }

    override fun initArguments(args: Bundle) {
    }
}