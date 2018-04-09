package com.hong.mason.moverecoder.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import com.hong.mason.moverecoder.data.Category
import com.hong.mason.moverecoder.data.Record
import com.hong.mason.moverecoder.view.history.category.CategoryHistory


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, CURRENT_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createRecords = "CREATE TABLE ${Records.TABLE_NAME} (" +
                "${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Records.START_TIME} INTEGER NOT NULL," +
                "${Records.ARRIVE_TIME} INTEGER NOT NULL," +
                "${Records.DURATION} INTEGER NOT NULL," +
                "${Records.CATEGORY} INTEGER NOT NULL)"
        val createCategories = "CREATE TABLE ${Categories.TABLE_NAME} (" +
                "${ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Categories.NAME} TEXT NOT NULL)"
        db?.execSQL(createRecords)
        db?.execSQL(createCategories)

        val values = ContentValues()
        values.put(Categories.NAME, "Default")
        db?.insert(Categories.TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun getAllRecords(): List<Record> {
        val cursor = readableDatabase.rawQuery(QUERY_SELECT_ALL_RECORDS, null)
        val result = ArrayList<Record>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
            val startTime = cursor.getLong(cursor.getColumnIndexOrThrow(Records.START_TIME))
            val arriveTime = cursor.getLong(cursor.getColumnIndexOrThrow(Records.ARRIVE_TIME))
            val duration = cursor.getLong(cursor.getColumnIndexOrThrow(Records.DURATION))
            val category = cursor.getInt(cursor.getColumnIndexOrThrow(Records.CATEGORY))
            val categoryName = cursor.getString(cursor.getColumnIndexOrThrow(Categories.NAME))
            result.add(Record(id, startTime, arriveTime, duration, category, categoryName))
        }
        cursor.close()
        return result
    }

    fun insertRecord(startTime: Long, arriveTime: Long, category: Long): Long {
        val duration = arriveTime - startTime

        val values = ContentValues()
        values.put(Records.START_TIME, startTime)
        values.put(Records.ARRIVE_TIME, arriveTime)
        values.put(Records.DURATION, duration)
        values.put(Records.CATEGORY, category)

        return writableDatabase.insert(Records.TABLE_NAME, null, values)
    }

    fun clearReocrds() {
        writableDatabase.execSQL("DELETE FROM ${Records.TABLE_NAME}")
    }

    fun getAllCategories(): List<Category> {
        val projections = arrayOf(ID, Categories.NAME)
        val order = "${ID} DESC"
        val cursor = readableDatabase.query(Categories.TABLE_NAME, projections, null, null, null, null, order)
        val result = ArrayList<Category>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Categories.NAME))
            result.add(Category(id, name))
        }
        cursor.close()
        return result
    }

    fun insertCategory(name: String): Long {
        val values = ContentValues()
        values.put(Categories.NAME, name)

        return writableDatabase.insert(Categories.TABLE_NAME, null, values)
    }

    fun getCategoryHistory(): List<CategoryHistory> {
        val cursor = readableDatabase.rawQuery(QUERY_SELECT_CATEGORY_HISTORY, null)
        val result = ArrayList<CategoryHistory>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
            val count = cursor.getInt(cursor.getColumnIndexOrThrow(COUNT))
            val sum = cursor.getLong(cursor.getColumnIndexOrThrow(SUM))
            val categoryName = cursor.getString(cursor.getColumnIndexOrThrow(Categories.NAME))
            result.add(CategoryHistory(id, categoryName, sum, count, sum.toFloat() / count))
        }
        cursor.close()
        return result
    }

    companion object {
        const val DATABASE_NAME = "MoveRecoder"
        const val CURRENT_VERSION = 1
        const val ID = "id"
        const val COUNT = "count"
        const val SUM = "sum"
        const val QUERY_SELECT_ALL_RECORDS = "SELECT * FROM ${Records.TABLE_NAME} LEFT JOIN ${Categories.TABLE_NAME} ON ${Records.TABLE_NAME}.${Records.CATEGORY} = ${Categories.TABLE_NAME}.$ID"
        const val QUERY_SELECT_CATEGORY_HISTORY = "SELECT ${Categories.TABLE_NAME}.$ID, ${Categories.NAME}, COUNT(${Records.TABLE_NAME}.$ID) as $COUNT, SUM(${Records.DURATION}) as $SUM FROM ${Records.TABLE_NAME} LEFT JOIN ${Categories.TABLE_NAME} ON ${Records.CATEGORY} = ${Categories.TABLE_NAME}.$ID GROUP BY ${Records.CATEGORY}"
    }

    class Records {
        companion object {
            const val TABLE_NAME = "records"
            const val START_TIME = "startTime"
            const val ARRIVE_TIME = "arriveTime"
            const val DURATION = "duration"
            const val CATEGORY = "category"
        }
    }

    class Categories {
        companion object {
            const val TABLE_NAME = "categories"
            const val NAME = "categoryName"
        }
    }
}