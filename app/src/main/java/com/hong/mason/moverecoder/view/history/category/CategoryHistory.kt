package com.hong.mason.moverecoder.view.history.category

import com.hong.mason.moverecoder.room.model.Category

data class CategoryHistory(
        val category: Category?,
        val totalTime: Int,
        val totalCount: Int
)