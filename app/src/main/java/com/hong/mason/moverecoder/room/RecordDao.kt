package com.hong.mason.moverecoder.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.hong.mason.moverecoder.data.Record

@Dao
interface RecordDao {
    @Query("SELECT * FROM Record")
    fun getAll(): List<Record>

    @Insert
    fun insert(vararg records: Record)

    @Query("DELETE FROM Record")
    fun clear()
}