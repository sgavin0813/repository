package com.kroger.classdemoapp.data.model

import com.kroger.classdemoapp.model.Article


sealed class MoviesResponseReceived {
    data class Success(val characters: List<Article>) : MoviesResponseReceived()
    object Error : MoviesResponseReceived()
}
