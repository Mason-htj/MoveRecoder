package com.hong.mason.moverecoder.view.history.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hong.mason.moverecoder.R

class CategoryHistoryAdapter(
        private val items: List<CategoryHistory>) : RecyclerView.Adapter<CategoryHistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CategoryHistoryViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_category_history, parent, false)
        return CategoryHistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryHistoryViewHolder?, position: Int) {
        holder?.bind(items[position])
    }
}