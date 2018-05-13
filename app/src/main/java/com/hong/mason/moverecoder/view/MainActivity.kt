package com.hong.mason.moverecoder.view

import android.arch.persistence.room.Room
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.common.ActionCodes
import com.hong.mason.moverecoder.room.model.Category
import com.hong.mason.moverecoder.room.model.Record
import com.hong.mason.moverecoder.model.RecoderPref
import com.hong.mason.moverecoder.room.AppDatabase
import com.hong.mason.moverecoder.room.CategoryDao
import com.hong.mason.moverecoder.room.RecordDao
import com.hong.mason.moverecoder.service.MovingService
import com.hong.mason.moverecoder.view.history.HistoryActivity
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CategorySelectDialog.OnSelectCategoryListener, MovingService.Callback {
    private lateinit var buttonStart: Button
    private lateinit var buttonWayPoint: Button
    private lateinit var buttonArrive: Button
    private lateinit var buttonCancel: Button
    private lateinit var buttonMenu: View
    private lateinit var drawer: DrawerLayout
    private lateinit var textStartTime: TextView
    private lateinit var recyclerView: RecyclerView

    private var mBinder: MovingService.LocalBinder? = null
    private lateinit var recordDao: RecordDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var recoderPref: RecoderPref
    private val formatter = DateFormat.getInstance()

    private var savedStartTime = 0L
    private var savedArriveTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initVariable()
        initView()

        if (intent != null) {
            handleIntent(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        bindService(Intent(this, MovingService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(mConnection)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            handleIntent(intent)
        }

    }

    override fun onSelectCategory(category: Category) {
        if (category.id != null) {
            recordDao.insert(Record(savedStartTime, savedArriveTime, savedArriveTime - savedStartTime, category.id!!))
        } else {
            val id = categoryDao.insert(Category(category.name))

            recordDao.insert(Record(savedStartTime, savedArriveTime, savedArriveTime - savedStartTime, id))
        }
        updateRecordList()
        val intent = Intent(this, MovingService::class.java)
        intent.action = ActionCodes.ACTION_ARRIVE
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStarted(startTime: Long) {
    }

    override fun onArrived(startTime: Long) {
    }

    override fun onCanceled() {
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBinder = service as MovingService.LocalBinder
            mBinder?.registerCallback(this@MainActivity)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBinder?.unRegisterCallback(this@MainActivity)
            mBinder = null
        }
    }

    private fun initVariable() {
        val appDatabase = Room.databaseBuilder(
                this,
                AppDatabase::class.java,
                "AppDatabase")
                .allowMainThreadQueries()
                .build()

        recordDao = appDatabase.recordDao()
        categoryDao = appDatabase.categoryDao()
        recoderPref = RecoderPref(this)

        buttonStart = findViewById(R.id.button_start)
        buttonWayPoint = findViewById(R.id.button_way_point)
        buttonArrive = findViewById(R.id.button_arrive)
        buttonCancel = findViewById(R.id.button_cancel)
        buttonMenu = findViewById(R.id.button_menu)
        drawer = findViewById(R.id.drawer)
        textStartTime = findViewById(R.id.text_start_time)
        recyclerView = findViewById(R.id.recycler_view)
    }

    private fun initView() {
        buttonStart.setOnClickListener { onClickControll(it) }
        buttonWayPoint.setOnClickListener { onClickControll(it) }
        buttonArrive.setOnClickListener { onClickControll(it) }
        buttonCancel.setOnClickListener { onClickControll(it) }
        initMenu()

        val startTime = recoderPref.getStartTime()
        setStartedView(startTime > 0, startTime)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        updateRecordList()
    }

    private fun onClickControll(view: View) {
        when (view.id) {
            R.id.button_start -> {
                startMoving()
            }
            R.id.button_way_point -> {
            }
            R.id.button_arrive -> {
                arriveMoving()
            }
            R.id.button_cancel -> {
                cancelMoving()
            }
        }

    }

    private fun initMenu() {
        buttonMenu.setOnClickListener {
            if (drawer.isDrawerOpen(Gravity.START)) {
                drawer.closeDrawer(Gravity.START)
            } else {
                drawer.openDrawer(Gravity.START)
            }
        }
        findViewById<View>(R.id.menu_records).setOnClickListener {
            startActivity(Intent(baseContext, HistoryActivity::class.java))
            drawer.closeDrawer(Gravity.START)
        }
        findViewById<View>(R.id.menu_settings).setOnClickListener {
            drawer.closeDrawer(Gravity.START)
        }
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
        recyclerView.adapter = RecentlyRecordAdapter(recordDao.getAllWithCategory())
        recyclerView.adapter.notifyDataSetChanged()
    }

    private fun startMoving() {
        val startTime = System.currentTimeMillis()
        recoderPref.setStartTime(startTime)
        setStartedView(true, startTime)
        mBinder?.startMoving()
    }

    private fun arriveMoving() {
        setStartedView(false)
        savedStartTime = recoderPref.getStartTime()
        savedArriveTime = System.currentTimeMillis()
        recoderPref.setStartTime(0)
        CategorySelectDialog.newInstance()
                .show(supportFragmentManager, "")
        mBinder?.arriveMoving()
    }

    private fun cancelMoving() {
        recoderPref.setStartTime(0)
        setStartedView(false)
        mBinder?.cancelMoving()
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            ActionCodes.ACTION_ARRIVE -> {
                arriveMoving()
            }
            ActionCodes.ACTION_CANCEL -> {
                cancelMoving()
            }
        }
    }
}
