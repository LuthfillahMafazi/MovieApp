package com.zii.core.main.data.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zii.core.main.data.remote.response.MovieResult
import com.zii.core.main.data.remote.response.MoviesResponse
import com.zii.core.main.enums.MovieType
import com.zii.core.main.service.MovieService
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val service: MovieService
) : BaseRemoteDataSource() {
    suspend fun getMovies(movieType: MovieType): Flow<Resource<MoviesResponse>> {
        return when (movieType) {
            MovieType.NOW_PLAYING -> getNowPlaying()
            MovieType.UPCOMING -> getUpcomingMovies()
            MovieType.TOP_RATED -> getTopRated()
        }
    }

    private fun getUpcomingMovies(): Flow<Resource<MoviesResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.getUpComingMovies(apiKey,1)
                validateResponse(response,
                onSuccess = {
                    emit(Resource.Success(it))
                },
                onError = {
                    emit(Resource.Error(it))
                })
            } catch (e: Exception) {
                validateError(e) { error, errorType ->
                    emit(Resource.Error(error, errorType))
                }
            }
        }
    }

    suspend fun searchMovie(query: String): Flow<Resource<MoviesResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.searchMovies(apiKey, query, 1)
                validateResponse(response,
                onSuccess = {
                    emit(Resource.Success(it))
                },
                onError = {
                    emit(Resource.Error(it))
                })
            } catch (e: Exception) {
                validateError(e) { error, errorType ->
                    emit(Resource.Error(error, errorType))
                }
            }
        }
    }

    private suspend fun getNowPlaying(): Flow<Resource<MoviesResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.getNowPlayingMovies(apiKey, 1)
                validateResponse(response,
                    onSuccess = {
                        emit(Resource.Success(it))
                    },
                    onError = {
                        emit(Resource.Error(it))
                    }
                )
            } catch (e: Exception) {
                validateError(e) { error, errorType ->
                    emit(Resource.Error(error, errorType))
                }
            }
        }
    }
    private suspend fun getTopRated(): Flow<Resource<MoviesResponse>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = service.getTopRatedMovies(apiKey, 1)
                validateResponse(response,
                onSuccess = {
                    emit(Resource.Success(it))
                },
                onError = {
                    emit(Resource.Error(it))
                })
            } catch (e: Exception) {
                validateError(e) { error, errorType ->
                    emit(Resource.Error(error, errorType))
                }
            }
        }
    }
}