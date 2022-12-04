package com.zii.movieapp.module

import com.zii.core.main.domain.usecase.favorite.FavoriteUseCaseImpl
import com.zii.core.main.domain.usecase.favorite.IFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideFavoriteUseCase(favoriteUseCaseImpl: FavoriteUseCaseImpl): IFavoriteUseCase
}