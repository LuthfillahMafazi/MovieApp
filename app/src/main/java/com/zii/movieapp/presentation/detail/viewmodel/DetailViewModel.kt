package com.zii.movieapp.presentation.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.zii.core.main.domain.model.MovieModel
import com.zii.movieapp.domain.detail.IDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: IDetailUseCase
): ViewModel() {

    suspend fun getLocaleMovie(movieId: Int) = useCase.getMovie(movieId)

    private fun addToFavorite(movie: MovieModel) = useCase.addToFavorite(movie)

    private fun deleteFromFavorite(movie: MovieModel) = useCase.deleteFromFavorite(movie)

    fun updateFavoriteMovie(isFavorite: Boolean, movie: MovieModel) {
        if (isFavorite) deleteFromFavorite(movie)
        else addToFavorite(movie)
    }

}