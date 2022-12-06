package com.zii.core.utils

import com.zii.core.BuildConfig
import com.zii.core.main.data.locale.entity.GenreEntity
import com.zii.core.main.data.locale.entity.MoviesEntity
import com.zii.core.main.data.remote.response.MovieResult
import com.zii.core.main.data.remote.response.MoviesResponse
import com.zii.core.main.domain.model.Genre
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.utils.Genres.getData

object DataMapper {
    fun movieResponseToMovieModel(data: MoviesResponse?): List<MovieModel> {
        return data.let { movie ->
            movie?.results?.map {
                MovieModel(
                    it.id,
                    it.title,
                    it.overview,
                    it.releaseDate,
                    BuildConfig.BASE_IMAGE_URL + it.poster,
                    BuildConfig.BASE_IMAGE_URL + it.backdrop,
                    it.voteAverage,
                    it.voteCount,
                    mapGenresIdsToGenre(it.genreIds)
                )
            } ?: listOf()
        }
    }

    fun mapMovieToDomain(data: MovieResult): MovieModel {
        return data.let {
            MovieModel(
                it.id,
                it.title,
                it.overview,
                it.releaseDate,
                BuildConfig.BASE_IMAGE_URL + it.poster,
                BuildConfig.BASE_IMAGE_URL + it.backdrop,
                it.voteAverage,
                it.voteCount,
                mapGenresIdsToGenre(it.genreIds)
            )
        }
//        return (data.let { movie ->
//            movie?.results?.map {
//                MovieModel(
//                    it.id,
//                    it.title,
//                    it.overview,
//                    it.releaseDate,
//                    BuildConfig.BASE_IMAGE_URL + it.poster,
//                    BuildConfig.BASE_IMAGE_URL + it.backdrop,
//                    it.voteAverage,
//                    it.voteCount,
//                    mapGenresIdsToGenre(it.genreIds)
//                )
//            } ?: listOf()
//        } ?: run { listOf() }) as MovieModel
    }

    private fun mapGenresIdsToGenre(data: List<Int>?): List<Genre?>? {
        val genres = getData()
        return data?.let {
            it.map { genreId ->
                genres.find { gen -> gen.id == genreId }
            }
        } ?: run { null }
    }
    fun mapModelToEntity(data: MovieModel): MoviesEntity {
        return MoviesEntity(
            data.id,
            data.title,
            data.overview,
            data.releaseDate,
            data.posterUri,
            data.backdropUri,
            data.voteAverage,
            data.voteCount,
            data.genre?.map { gen -> GenreEntity(gen?.id, gen?.name) }
        )
    }

    fun mapEntityToModel(data: MoviesEntity?): MovieModel? {
        return data?.let { movie ->
            MovieModel(
                movie.id,
                movie.title,
                movie.overview,
                movie.releaseDate,
                movie.posterUri,
                movie.backdropUri,
                movie.voteAverage,
                movie.voteCount,
                movie.genre?.map { gen -> Genre(gen.id, gen.name) }
            )
        } ?: run { null }
    }
}