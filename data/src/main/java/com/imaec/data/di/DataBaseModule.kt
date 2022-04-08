package com.imaec.data.di

import android.content.Context
import com.imaec.data.db.AppDatabase
import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.db.dao.CityDao
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.db.dao.PlanDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideCategoryDao(
        database: AppDatabase
    ): CategoryDao = database.categoryDao()

    @Singleton
    @Provides
    fun provideCityDao(
        database: AppDatabase
    ): CityDao = database.cityDao()

    @Singleton
    @Provides
    fun providePlaceDao(
        database: AppDatabase
    ): PlaceDao = database.placeDao()

    @Singleton
    @Provides
    fun providePlanDao(
        database: AppDatabase
    ): PlanDao = database.planDao()
}
