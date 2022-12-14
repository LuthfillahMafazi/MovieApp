package com.zii.core.main.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zii.core.BuildConfig
import com.zii.core.main.data.remote.response.MovieResult
import com.zii.core.main.service.MovieService
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(
    private val service: MovieService
) : PagingSource<Int, MovieResult>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        val pageIndex = params.key ?: 1
        return try {
            val response = service.getNowPlayingMovies(
                apiKey = BuildConfig.API_KEY,
                page = pageIndex
            )
            val movies = response.body()?.results
            val nextKey = if (movies?.isEmpty() == true) {
                null
            } else {
                pageIndex + (params.loadSize)
            }
            LoadResult.Page(
                data = movies.orEmpty(),
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}