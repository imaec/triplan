package com.imaec.data.di

import com.imaec.data.api.NaverLocalService
import com.imaec.data.api.NaverService
import com.imaec.data.api.RoadAddressService
import com.imaec.data.db.dao.CategoryDao
import com.imaec.data.db.dao.CityDao
import com.imaec.data.db.dao.PlaceDao
import com.imaec.data.db.dao.PlanDao
import com.imaec.data.repository.CategoryRepositoryImpl
import com.imaec.data.repository.CityRepositoryImpl
import com.imaec.data.repository.NaverLocalRepositoryImpl
import com.imaec.data.repository.NaverRepositoryImpl
import com.imaec.data.repository.PlaceRepositoryImpl
import com.imaec.data.repository.PlanRepositoryImpl
import com.imaec.data.repository.RoadAddressRepositoryImpl
import com.imaec.domain.repository.CategoryRepository
import com.imaec.domain.repository.CityRepository
import com.imaec.domain.repository.NaverLocalRepository
import com.imaec.domain.repository.NaverRepository
import com.imaec.domain.repository.PlaceRepository
import com.imaec.domain.repository.PlanRepository
import com.imaec.domain.repository.RoadAddressRepository
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
    fun provideCityRepository(dao: CityDao): CityRepository =
        CityRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePlaceRepository(dao: PlaceDao): PlaceRepository = PlaceRepositoryImpl(dao)

    @Provides
    @Singleton
    fun providePlanRepository(dao: PlanDao): PlanRepository = PlanRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideNaverRepository(service: NaverService): NaverRepository =
        NaverRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideNaverLocalRepository(service: NaverLocalService): NaverLocalRepository =
        NaverLocalRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideRoadAddressRepository(service: RoadAddressService): RoadAddressRepository =
        RoadAddressRepositoryImpl(service)
}
