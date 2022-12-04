package com.zii.movieapp.domain.detail

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IDetailUseCase {
    suspend fun getMovie(movieId: Int): Flow<Resource<MovieModel?>>
    fun addToFavorite(movie: MovieModel)
    fun deleteFromFavorite(movie: MovieModel)
}