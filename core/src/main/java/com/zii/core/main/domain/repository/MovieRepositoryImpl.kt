package com.zii.core.main.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zii.core.main.data.remote.response.MovieResult
import com.zii.core.main.data.remote.source.MovieRemoteDataSource
import com.zii.core.main.data.remote.source.MoviesPagingSource
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.enums.MovieType
import com.zii.core.main.service.MovieService
import com.zii.core.main.vo.Resource
import com.zii.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
): IMovieRepository {
    override suspend fun getMovie(movieType: MovieType): Flow<Resource<List<MovieModel>>> {
        return flow {
            remoteDataSource.getMovies(movieType).collect {
                when (it) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(it.message, it.errorType))
                    is Resource.Success -> {
                        val data = DataMapper.movieResponseToMovieModel(it.data)
                        emit(Resource.Success(data))
                    }
                }
            }
        }
    }

    override suspend fun searchMovie(query: String): Flow<Resource<List<MovieModel>>> {
        return flow {
            remoteDataSource.searchMovie(query).collect {
                when (it) {
                    is Resource.Loading -> emit(Resource.Loading())
                    is Resource.Error -> emit(Resource.Error(it.message, it.errorType))
                    is Resource.Success -> {
                        val data = DataMapper.movieResponseToMovieModel(it.data)
                        emit(Resource.Success(data))
                    }
                }
            }
        }
    }
}