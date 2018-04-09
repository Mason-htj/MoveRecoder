package com.hong.mason.moverecoder.view.history.category

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment
import com.hong.mason.moverecoder.model.DatabaseHelper

class CategoryTabFragment : BaseTabFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryHistoryAdapter
    private lateinit var database: DatabaseHelper

    override fun getTitle(): String {
        return "Category"
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.tab_fragment_statistic
    }

    override fun initView(view: View) {
        adapter = CategoryHistoryAdapter(database.getCategoryHistory())
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun initArguments(args: Bundle?) {
        database = DatabaseHelper(context)
    }
}