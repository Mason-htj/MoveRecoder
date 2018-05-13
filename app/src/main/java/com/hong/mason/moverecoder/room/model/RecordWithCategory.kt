package com.hong.mason.moverecoder.room.model

data class RecordWithCategory(
        var id: Long? = null,
        val startTime: Long,
        val arriveTime: Long,
        val duration: Long,
        val category: Long,
        val categoryName: String
)