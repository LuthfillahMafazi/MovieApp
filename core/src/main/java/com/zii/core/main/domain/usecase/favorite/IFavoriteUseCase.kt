package com.zii.core.main.domain.usecase.favorite

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IFavoriteUseCase {
    suspend fun getListFavoriteMovies(): Flow<Resource<List<MovieModel>>>
}