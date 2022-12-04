package com.zii.movieapp.module

import com.zii.core.main.domain.usecase.favorite.IFavoriteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun favoriteUseCase(): IFavoriteUseCase
}