package com.hong.mason.moverecoder.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hong.mason.moverecoder.R
import com.hong.mason.moverecoder.data.Category

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Category> = emptyList()
    private var selected = 0
    private var otherName: String = ""

    fun getSelectedCategory(): Category {
        return if (selected < items.size) {
            items[selected]
        } else {
            Category(otherName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FOOTER -> CategoryFooterViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_category_footer, parent, false), { str -> otherName = str })
            else -> CategoryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.viewholder_category, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder?.itemView?.setOnClickListener {
            setSelected(position)
        }
        if (holder is CategoryViewHolder) {
            holder.bind(items[position], selected == position)
        } else if (holder is CategoryFooterViewHolder) {
            holder.bind(otherName, selected == position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) TYPE_NORMAL else TYPE_FOOTER
    }

    private fun setSelected(newSelected: Int) {
        val oldSelected = selected
        selected = newSelected
        notifyItemChanged(oldSelected)
        notifyItemChanged(newSelected)
    }

    fun setItems(items: List<Category>, selected: Int) {
        this.items = items
        this.selected = selected
        notifyDataSetChanged()
    }

    companion object {
        const val TYPE_NORMAL = 0x1
        const val TYPE_FOOTER = 0x2
    }
}