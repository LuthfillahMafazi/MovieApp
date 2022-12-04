package com.zii.movieapp.domain.home

import androidx.paging.PagingData
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IHomeUseCase {
    suspend fun getNowPlaying(): Flow<Resource<List<MovieModel>>>
    suspend fun getTopRated(): Flow<Resource<List<MovieModel>>>
    suspend fun getUpcoming(): Flow<Resource<List<MovieModel>>>
}