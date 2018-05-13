package com.hong.mason.moverecoder.room.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Category(
        val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}