package com.zii.core.main.domain.repository

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ILocaleRepository {
    fun addToFavorite(movie: MovieModel)
    fun deleteFromFavorite(movie: MovieModel)
    suspend fun getMovieId(movieId: Int): Flow<Resource<MovieModel?>>
    suspend fun getFavoriteMovie(): Flow<Resource<List<MovieModel>>>
}