package com.hong.mason.moverecoder.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.hong.mason.moverecoder.room.model.Record
import com.hong.mason.moverecoder.room.model.RecordWithCategory

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    fun getAll(): List<Record>

    @Query("SELECT Record.*, Category.name as categoryName FROM Record INNER JOIN Category ON Record.category = Category.id")
    fun getAllWithCategory(): List<RecordWithCategory>

    @Insert
    fun insert(vararg records: Record)

    @Query("DELETE FROM Record")
    fun clear()
}