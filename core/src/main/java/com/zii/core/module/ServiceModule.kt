package com.zii.core.module

import com.zii.core.BuildConfig
import com.zii.core.main.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieService(
        client: OkHttpClient
    ) : MovieService {
        return NetworkModule
            .buildRetrofit(BuildConfig.BASE_URL, client)
            .create(MovieService::class.java)
    }
}