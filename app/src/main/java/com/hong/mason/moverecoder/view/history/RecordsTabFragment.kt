package com.hong.mason.moverecoder.view.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment
import com.hong.mason.moverecoder.model.DatabaseHelper
import com.hong.mason.moverecoder.view.RecentlyRecordAdapter

class RecordsTabFragment : BaseTabFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentlyRecordAdapter
    private lateinit var database: DatabaseHelper

    override fun getTitle(): String {
        return "Records"
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.tab_fragment_records
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun initView(view: View) {
        adapter = RecentlyRecordAdapter(database.getAllRecords())
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun initArguments(args: Bundle?) {
        database = DatabaseHelper(context)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ?: -1) {
            R.id.action_clear -> {
                database.clearReocrds()
                adapter.changeItems(emptyList())
                return true
            }
        }
        return false
    }
}