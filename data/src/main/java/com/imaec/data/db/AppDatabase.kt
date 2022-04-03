package com.imaec.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.entity.CategoryEntity
import com.imaec.data.entity.PlaceEntity

@Database(
    entities = [
        CategoryEntity::class,
        PlaceEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val DB_NAME = "tri-plan-database"

        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
