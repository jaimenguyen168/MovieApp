package edu.temple.movieapp.movie_list.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val category: String): MovieListUiEvent
    data object Navigate: MovieListUiEvent
}