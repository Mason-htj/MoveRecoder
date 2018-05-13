package com.hong.mason.moverecoder.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hong.mason.moverecoder.room.model.Category
import com.hong.mason.moverecoder.room.model.Record

@Database(
        entities = [(Record::class), (Category::class)],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDao
    abstract fun categoryDao(): CategoryDao
}