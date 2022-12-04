package com.zii.core.main.data.locale.source

import com.zii.core.main.data.locale.dao.MoviesDao
import com.zii.core.main.data.locale.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors
import javax.inject.Inject

class LocaleDataSource @Inject constructor(
    private val moviesDao: MoviesDao
) {
    private val executorService = Executors.newSingleThreadExecutor()

    fun getFavoriteMovies(): Flow<List<MoviesEntity>?> = moviesDao.getAllMovies()
    fun getMovie(moviesId: Int): Flow<MoviesEntity?> = moviesDao.getMovie(moviesId)

    fun addToFavorite(movies: MoviesEntity) {
        movies.isFavorite = true
        executorService.execute {
            moviesDao.addToFavorite(movies)
        }
    }

    fun deleteFromFavorite(movies: MoviesEntity) {
        executorService.execute {
            moviesDao.deleteFromFavorite(movies)
        }
    }
}