package com.hong.mason.moverecoder.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Record(
        val startTime: Long,
        val arriveTime: Long,
        val duration: Long,
        val category: Long,
        val categoryName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}