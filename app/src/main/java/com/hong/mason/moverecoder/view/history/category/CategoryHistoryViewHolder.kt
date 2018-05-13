package com.hong.mason.moverecoder.view.history.category

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.room.model.CategoryWithRecord
import com.hong.mason.moverecoder.util.TimeFormatUtils

class CategoryHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textName: TextView = itemView.findViewById(R.id.text_name)
    private val textTotalTime: TextView = itemView.findViewById(R.id.text_total_time)
    private val textTotalCount: TextView = itemView.findViewById(R.id.text_total_count)
    private val textAverageTime: TextView = itemView.findViewById(R.id.text_average_time)

    fun bind(item: CategoryWithRecord) {
        textName.text = item.name
        textTotalTime.text = TimeFormatUtils.getDurationString(item.totalTime ?: 0)
        textTotalCount.text = "${item.totalCount ?: 0} 회"
        textAverageTime.text = TimeFormatUtils.getDurationString((item.totalTime ?: 0L) / (item.totalCount ?: 0))
    }
}