package com.hong.mason.moverecoder.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.hong.mason.moverecoder.data.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Insert
    fun insert(category: Category) : Long
}