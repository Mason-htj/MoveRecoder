package com.hong.mason.moverecoder.room.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class CategoryWithRecord(
    @Embedded
    var category: Category? = null,

    @Relation(
            parentColumn = "id",
            entityColumn = "category"
    )
    var record: List<Record>? = null
)