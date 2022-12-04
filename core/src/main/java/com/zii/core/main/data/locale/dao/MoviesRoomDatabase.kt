package com.zii.core.main.data.locale.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zii.core.main.data.locale.entity.MoviesEntity

@Database(entities = [MoviesEntity::class], version = 1)
@TypeConverters(ConvertersDao::class)
abstract class MoviesRoomDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}