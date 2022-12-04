package com.zii.movieapp.domain.search

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface ISearchUseCase {
    suspend fun getSearchMovie(query: String): Flow<Resource<List<MovieModel>>>
}