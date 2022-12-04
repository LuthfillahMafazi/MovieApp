package com.zii.movieapp.domain.search

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.domain.repository.IMovieRepository
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(
    private val movieRepository: IMovieRepository
): ISearchUseCase {
    override suspend fun getSearchMovie(query: String): Flow<Resource<List<MovieModel>>> {
        return movieRepository.searchMovie(query)
    }
}