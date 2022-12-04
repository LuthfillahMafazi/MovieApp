package com.zii.core.main.domain.repository

import com.zii.core.main.data.locale.source.LocaleDataSource
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import com.zii.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocaleRepositoryImpl @Inject constructor(
    private val localeDataSource: LocaleDataSource
) : ILocaleRepository {
    override fun addToFavorite(movie: MovieModel) {
        val entity = DataMapper.mapModelToEntity(movie)
        localeDataSource.addToFavorite(entity)
    }

    override fun deleteFromFavorite(movie: MovieModel) {
        val entity = DataMapper.mapModelToEntity(movie)
        localeDataSource.deleteFromFavorite(entity)
    }

    override suspend fun getMovieId(movieId: Int): Flow<Resource<MovieModel?>> {
        return flow {
            emit(Resource.Loading())
            localeDataSource.getMovie(movieId).collect {
                val model = DataMapper.mapEntityToModel(it)
                emit(Resource.Success(model))

            }
        }
    }

    override suspend fun getFavoriteMovie(): Flow<Resource<List<MovieModel>>> {
        return flow {
            emit(Resource.Loading())
            localeDataSource.getFavoriteMovies().collect {
                it.let { data ->
                    val model = data?.map { movie -> DataMapper.mapEntityToModel(movie) }
                    emit(Resource.Success(model?.filterNotNull()))
                }
            }
        }

    }
}