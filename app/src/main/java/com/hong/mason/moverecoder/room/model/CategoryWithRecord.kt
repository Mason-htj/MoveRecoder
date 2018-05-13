package com.hong.mason.moverecoder.room.model

data class CategoryWithRecord(
        var id: Long? = null,
        var name: String = "",
        var totalTime: Long? = null,
        var totalCount: Int? = null
)