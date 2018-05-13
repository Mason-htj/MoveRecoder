package com.hong.mason.moverecoder.view.history.category

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment
import com.hong.mason.moverecoder.room.AppDatabase

class CategoryTabFragment : BaseTabFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryHistoryAdapter

    override fun getTitle(): String {
        return "Category"
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.tab_fragment_statistic
    }

    override fun initView(view: View) {
//        val database = Room.databaseBuilder(context, AppDatabase::class.java, "AppDatase")
//                .allowMainThreadQueries()
//                .build()
//        recyclerView = view.findViewById(R.id.recycler_view)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter
//        adapter.notifyDataSetChanged()
    }

    override fun initArguments(args: Bundle?) {
    }
}