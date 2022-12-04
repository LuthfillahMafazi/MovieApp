package com.zii.core.main.domain.repository

import androidx.paging.PagingData
import com.zii.core.main.data.remote.response.MovieResult
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.enums.MovieType
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getMovie(movieType: MovieType): Flow<Resource<List<MovieModel>>>
    suspend fun searchMovie(query: String): Flow<Resource<List<MovieModel>>>
}