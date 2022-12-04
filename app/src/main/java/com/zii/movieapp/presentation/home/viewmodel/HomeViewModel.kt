package com.zii.movieapp.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import com.zii.movieapp.domain.home.IHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: IHomeUseCase
) : ViewModel() {

    private val _nowPlayingMovies = MutableStateFlow<Resource<List<MovieModel>>?>(null)
    private val _nowMorePlayingMovies = MutableStateFlow<PagingData<MovieModel>?>(null)
    private val _topRatedMovies = MutableStateFlow<Resource<List<MovieModel>>?>(null)
    private val _upcomingMovies = MutableStateFlow<Resource<List<MovieModel>>?>(null)

    val nowPlayingMovies get() = _nowPlayingMovies.asStateFlow()
    val topRatedMovies get() = _topRatedMovies.asStateFlow()
    val upcomingMovies get() = _upcomingMovies.asStateFlow()

    init {
        getNowPlayingMovies()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            useCase.getNowPlaying().collect {
                _nowPlayingMovies.emit(it)
            }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            useCase.getTopRated().collect{
                _topRatedMovies.emit(it)
            }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            useCase.getUpcoming().collect{
                _upcomingMovies.emit(it)
            }
        }
    }
}