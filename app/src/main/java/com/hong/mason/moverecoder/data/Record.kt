package com.hong.mason.moverecoder.data

data class Record(
        val id: Long,
        val startTime: Long,
        val arriveTime: Long,
        val duration: Long,
        val category: Int,
        val categoryName: String
)