package com.zii.core.module

import com.zii.core.main.data.remote.source.MovieRemoteDataSource
import com.zii.core.main.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        service: MovieService
    ): MovieRemoteDataSource {
        return MovieRemoteDataSource(service)
    }
}