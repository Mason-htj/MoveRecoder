package com.hong.mason.moverecoder.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.data.Record

class RecentlyRecordAdapter(
        private val items: List<Record>) : RecyclerView.Adapter<RecentlyRecordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecentlyRecordViewHolder {
        return RecentlyRecordViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_record, parent, false))
    }

    override fun onBindViewHolder(holder: RecentlyRecordViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}