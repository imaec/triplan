package com.imaec.data.di

import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.repository.CategoryRepositoryImpl
import com.imaec.data.repository.PlaceRepositoryImpl
import com.imaec.domain.repository.CategoryRepository
import com.imaec.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(dao: CategoryDao): CategoryRepository =
        CategoryRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePlaceRepository(dao: PlaceDao): PlaceRepository = PlaceRepositoryImpl(dao)
}
