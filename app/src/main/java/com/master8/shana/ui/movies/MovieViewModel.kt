package com.master8.shana.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.master8.shana.domain.entity.Movie
import com.master8.shana.domain.usecase.AddGoodMovieUseCase
import com.master8.shana.domain.usecase.AddNeedToWatchMovieUseCase
import kotlinx.coroutines.launch

class MovieViewModel(
    private val addGoodMovieUseCase: AddGoodMovieUseCase,
    private val addNeedToWatchMovieUseCase: AddNeedToWatchMovieUseCase
) : ViewModel() {

    fun addGoodMovie(movie: Movie) = viewModelScope.launch {
        addGoodMovieUseCase(movie)
    }

    fun addNeedToWatchMovie(movie: Movie) = viewModelScope.launch {
        addNeedToWatchMovieUseCase(movie)
    }
}