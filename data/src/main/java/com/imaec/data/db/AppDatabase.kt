package com.imaec.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.db.dao.CityDao
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.db.dao.PlanDao
import com.imaec.data.entity.local.CategoryEntity
import com.imaec.data.entity.local.CityEntity
import com.imaec.data.entity.local.PlaceEntity
import com.imaec.data.entity.local.PlanEntity

@Database(
    entities = [
        CategoryEntity::class,
        CityEntity::class,
        PlaceEntity::class,
        PlanEntity::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun cityDao(): CityDao
    abstract fun placeDao(): PlaceDao
    abstract fun planDao(): PlanDao

    companion object {
        private const val DB_NAME = "tri-plan-database"

        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}
