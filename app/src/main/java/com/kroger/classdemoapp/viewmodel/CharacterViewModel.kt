package com.kroger.classdemoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kroger.classdemoapp.data.model.MoviesResponseReceived
import com.kroger.classdemoapp.data.repository.MoviesRepository
import com.kroger.classdemoapp.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _characters = MutableStateFlow<MovieItemEvent>(MovieItemEvent.Loading)
    val characters: StateFlow<MovieItemEvent> = _characters

    fun fillData() {
        viewModelScope.launch {
            when (val response = moviesRepository.getNewsPopular()) {
                MoviesResponseReceived.Error -> _characters.value = MovieItemEvent.Failure
                is MoviesResponseReceived.Success -> _characters.value = MovieItemEvent.Success(response.characters)
            }
        }
    }
    fun fetchByTitle(title: String): Article? {
        return characters.value.let { movieItemEvent ->
            if (movieItemEvent is MovieItemEvent.Success) {
                movieItemEvent.article.find { it.title == title }
            } else {
                null
            }
        }
    }



    sealed class MovieItemEvent {
        data class Success(val article : List<Article>) : MovieItemEvent()
        object Failure : MovieItemEvent()
        object Loading : MovieItemEvent()
    }
}
