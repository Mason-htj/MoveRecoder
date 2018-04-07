package com.hong.mason.moverecoder.view.history

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment

class HistoryActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        val tabList: ArrayList<BaseTabFragment> = ArrayList()
        tabList.add(StatisticTabFragment())
        tabList.add(HistoryTabFragment())
        viewPager.offscreenPageLimit = tabList.size
        viewPager.adapter = ViewPagerAdapter(tabList)
        tabLayout.setupWithViewPager(viewPager)
    }

    class ViewPagerAdapter(
            private val tabList: List<BaseTabFragment>
    ) : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            return tabList[position]
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return tabList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabList[position].getTitle()
        }
    }
}