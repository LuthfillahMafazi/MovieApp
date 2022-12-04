package com.zii.movieapp.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zii.core.main.domain.model.MovieModel
import com.zii.core.main.vo.Resource
import com.zii.movieapp.domain.search.ISearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: ISearchUseCase
): ViewModel() {

    private val _searchMovies = MutableStateFlow<Resource<List<MovieModel>>?>(null)
    val searchMovies get() = _searchMovies.asStateFlow()

    fun getSearchMovies(query: String) {
        viewModelScope.launch {
            useCase.getSearchMovie(query).collect {
                _searchMovies.emit(it)
            }
        }
    }

}