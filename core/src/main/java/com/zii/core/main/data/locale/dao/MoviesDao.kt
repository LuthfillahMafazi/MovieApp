package com.zii.core.main.data.locale.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zii.core.main.data.locale.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavorite(movie: MoviesEntity)

    @Delete
    fun deleteFromFavorite(movie: MoviesEntity)

    @Query("SELECT * FROM MoviesEntity ORDER BY title ASC")
    fun getAllMovies(): Flow<List<MoviesEntity>?>

    @Query("SELECT * FROM MoviesEntity WHERE id = :idMovie")
    fun getMovie(idMovie: Int): Flow<MoviesEntity?>

}