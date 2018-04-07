package com.hong.mason.moverecoder.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.data.Record
import com.hong.mason.moverecoder.util.TimeFormatUtils
import java.text.DateFormat
import java.util.*

class RecentlyRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textStartTime: TextView = itemView.findViewById(R.id.text_start_time)
    private val textArriveTime: TextView = itemView.findViewById(R.id.text_arrive_time)
    private val textDuration: TextView = itemView.findViewById(R.id.text_duration)
    private val textCategory: TextView = itemView.findViewById(R.id.text_category)
    private val formatter = DateFormat.getInstance()

    fun bind(record: Record) {
        textStartTime.text = formatter.format(Date(record.startTime))
        textArriveTime.text = formatter.format(Date(record.arriveTime))
        textDuration.text = TimeFormatUtils.getDurationString(record.duration)
        textCategory.text = record.categoryName
    }
}