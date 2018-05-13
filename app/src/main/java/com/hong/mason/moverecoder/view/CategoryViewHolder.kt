package com.hong.mason.moverecoder.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.room.model.Category

class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textName: TextView = itemView.findViewById(R.id.text_name)
    private val buttonCheck: View = itemView.findViewById(R.id.button_check)
    private var category: Category? = null

    fun bind(category: Category, selected: Boolean) {
        this.category = category
        textName.text = category.name
        buttonCheck.isSelected = selected
    }
}