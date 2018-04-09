package com.hong.mason.moverecoder.view.history.category

data class CategoryHistory(
        val id: Long,
        val name: String,
        val totalTime: Long,
        val totalCount: Int,
        val averageTime: Float
)