package com.hong.mason.moverecoder.room.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
        foreignKeys = [ForeignKey(
                entity = Category::class,
                parentColumns = ["id"],
                childColumns = ["category"])]
)
data class Record(
        val startTime: Long,
        val arriveTime: Long,
        val duration: Long,
        val category: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}