package com.zii.movieapp.domain.detail

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.domain.repository.ILocaleRepository
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCaseImpl @Inject constructor(
    private val localRepository: ILocaleRepository
): IDetailUseCase {
    override suspend fun getMovie(movieId: Int): Flow<Resource<MovieModel?>> {
        return localRepository.getMovieId(movieId)
    }

    override fun addToFavorite(movie: MovieModel) {
        return localRepository.addToFavorite(movie)
    }

    override fun deleteFromFavorite(movie: MovieModel) {
        return localRepository.deleteFromFavorite(movie)
    }
}