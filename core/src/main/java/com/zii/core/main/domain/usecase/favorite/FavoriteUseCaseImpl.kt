package com.zii.core.main.domain.usecase.favorite

import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.domain.repository.ILocaleRepository
import com.zii.core.main.vo.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteUseCaseImpl @Inject constructor(
    private val localeRepository: ILocaleRepository
): IFavoriteUseCase {
    override suspend fun getListFavoriteMovies(): Flow<Resource<List<MovieModel>>> {
        return localeRepository.getFavoriteMovie()
    }
}