package com.zii.featurefavorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zii.core.main.domain.usecase.favorite.IFavoriteUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val favoriteUseCase: IFavoriteUseCase
): ViewModel() {
    suspend fun getFavorite() = favoriteUseCase.getListFavoriteMovies()
}

class FavoriteViewModelFactory @Inject constructor(
    private val getFavoriteUseCase : IFavoriteUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(
            getFavoriteUseCase
        ) as T
    }
}