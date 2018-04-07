package com.hong.mason.moverecoder.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hong.mason.moverecoder.model.DatabaseHelper
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.data.Category
import com.hong.mason.moverecoder.model.RecoderPref
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CategorySelectDialog.OnSelectCategoryListener {
    private lateinit var buttonStart: Button
    private lateinit var buttonWayPoint: Button
    private lateinit var buttonArrive: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonClear: Button
    private lateinit var buttonMenu: View
    private lateinit var drawer: DrawerLayout
    private lateinit var textStartTime: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var database: DatabaseHelper
    private lateinit var recoderPref: RecoderPref
    private val formatter = DateFormat.getInstance()

    private var savedStartTime = 0L
    private var savedArriveTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariable()
        initView()
    }

    override fun onSelectCategory(category: Category) {
        if (category.id != -1L) {
            database.insertRecord(savedStartTime, savedArriveTime, category.id)
        } else {
            val id = database.insertCategory(category.name)
            database.insertRecord(savedStartTime, savedArriveTime, id)
        }
        updateRecordList()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initVariable() {
        database = DatabaseHelper(this)
        recoderPref = RecoderPref(this)

        buttonStart = findViewById(R.id.button_start)
        buttonWayPoint = findViewById(R.id.button_way_point)
        buttonArrive = findViewById(R.id.button_arrive)
        buttonCancel = findViewById(R.id.button_cancel)
        buttonClear = findViewById(R.id.button_clear)
        buttonMenu = findViewById(R.id.button_menu)
        drawer = findViewById(R.id.drawer)
        textStartTime = findViewById(R.id.text_start_time)
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun initView() {
        buttonStart.setOnClickListener {
            val startTime = System.currentTimeMillis()
            recoderPref.setStartTime(startTime)
            setStartedView(true, startTime)
        }
        buttonWayPoint.setOnClickListener { }
        buttonArrive.setOnClickListener {
            setStartedView(false)
            savedStartTime = recoderPref.getStartTime()
            savedArriveTime = System.currentTimeMillis()
            recoderPref.setStartTime(0)
            CategorySelectDialog.newInstance()
                    .show(supportFragmentManager, "")
        }
        buttonCancel.setOnClickListener {
            recoderPref.setStartTime(0)
            setStartedView(false)
        }
        buttonClear.setOnClickListener {
            database.clearReocrds()
            updateRecordList()
        }
        buttonMenu.setOnClickListener {
            if (drawer.isDrawerOpen(Gravity.START)) {
                drawer.closeDrawer(Gravity.START)
            } else {
                drawer.openDrawer(Gravity.START)
            }
        }

        val startTime = recoderPref.getStartTime()
        setStartedView(startTime > 0, startTime)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        updateRecordList()
    }

    private fun setStartedView(started: Boolean, startTime: Long = 0) {
        buttonStart.visibility = if (started) View.GONE else View.VISIBLE
        buttonWayPoint.visibility = if (started) View.VISIBLE else View.GONE
        buttonArrive.visibility = if (started) View.VISIBLE else View.GONE
        buttonCancel.visibility = if (started) View.VISIBLE else View.GONE
        textStartTime.visibility = if (started) View.VISIBLE else View.GONE
        if (startTime > 0) {
            textStartTime.text = "Start : ${formatter.format(Date(startTime))}"
        }
    }

    private fun updateRecordList() {
        recyclerView.adapter = RecordsAdapter(database.getAllRecords())
        recyclerView.adapter.notifyDataSetChanged()
    }
}
