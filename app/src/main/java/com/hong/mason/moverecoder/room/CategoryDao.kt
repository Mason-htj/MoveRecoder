package com.hong.mason.moverecoder.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.hong.mason.moverecoder.room.model.Category
import com.hong.mason.moverecoder.room.model.CategoryWithRecord

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getAll(): List<Category>

    @Query("SELECT * FROM Category INNER JOIN Record ON Category.id = Record.category")
    fun getAllWithRecord(): List<CategoryWithRecord>

    @Insert
    fun insert(category: Category) : Long
}