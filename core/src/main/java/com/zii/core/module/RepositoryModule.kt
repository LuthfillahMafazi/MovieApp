package com.zii.core.module

import com.zii.core.main.data.locale.source.LocaleDataSource
import com.zii.core.main.data.remote.source.MovieRemoteDataSource
import com.zii.core.main.domain.repository.ILocaleRepository
import com.zii.core.main.domain.repository.IMovieRepository
import com.zii.core.main.domain.repository.LocaleRepositoryImpl
import com.zii.core.main.domain.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource
    ): IMovieRepository {
        return MovieRepositoryImpl(remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideLocaleRepository(
        localeDataSource: LocaleDataSource
    ) : ILocaleRepository {
        return LocaleRepositoryImpl(localeDataSource)
    }
}