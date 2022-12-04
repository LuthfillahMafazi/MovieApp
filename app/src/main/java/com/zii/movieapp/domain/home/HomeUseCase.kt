package com.zii.movieapp.domain.home

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.domain.repository.IMovieRepository
import com.zii.core.main.enums.MovieType
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val movieRepository: IMovieRepository
) : IHomeUseCase {
    override suspend fun getNowPlaying(): Flow<Resource<List<MovieModel>>> {
        return movieRepository.getMovie(MovieType.NOW_PLAYING)
    }

    override suspend fun getTopRated(): Flow<Resource<List<MovieModel>>> {
        return movieRepository.getMovie(MovieType.TOP_RATED)
    }

    override suspend fun getUpcoming(): Flow<Resource<List<MovieModel>>> {
        return movieRepository.getMovie(MovieType.UPCOMING)
    }
}