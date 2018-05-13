package com.hong.mason.moverecoder.view.history

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment
import com.hong.mason.moverecoder.room.AppDatabase
import com.hong.mason.moverecoder.room.RecordDao
import com.hong.mason.moverecoder.view.RecentlyRecordAdapter

class RecordsTabFragment : BaseTabFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecentlyRecordAdapter
    private lateinit var recordDao: RecordDao

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
        adapter = RecentlyRecordAdapter(recordDao.getAllWithCategory())
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun initArguments(args: Bundle?) {
        val appDatabase = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "AppDatabase")
                .allowMainThreadQueries()
                .build()

        recordDao = appDatabase.recordDao()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ?: -1) {
            R.id.action_clear -> {
                recordDao.clear()
                adapter.changeItems(emptyList())
                return true
            }
        }
        return false
    }
}