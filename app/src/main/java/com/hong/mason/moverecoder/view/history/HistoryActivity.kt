package com.hong.mason.moverecoder.view.history

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.base.BaseTabFragment
import com.hong.mason.moverecoder.view.history.category.CategoryTabFragment

class HistoryActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        viewPager = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)

        val tabList: ArrayList<BaseTabFragment> = ArrayList()
        tabList.add(CategoryTabFragment())
        tabList.add(HistoryTabFragment())
        viewPager.offscreenPageLimit = tabList.size
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, tabList)
        tabLayout.setupWithViewPager(viewPager)
    }

    class ViewPagerAdapter(
            fragmentManager: FragmentManager,
            private val tabList: List<BaseTabFragment>
    ) : FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return tabList[position]
        }

        override fun getCount(): Int {
            return tabList.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return tabList[position].getTitle()
        }
    }
}