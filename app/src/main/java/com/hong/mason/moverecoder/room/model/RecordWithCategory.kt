package com.hong.mason.moverecoder.room.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class RecordWithCategory(
    @Embedded
    var record: Record? = null,

    @Relation(
            parentColumn = "category",
            entityColumn = "id"
    )
    var category: List<Category>? = null
)