package edu.temple.movieapp.movie_list.presentation

import edu.temple.movieapp.movie_list.domain.model.Movie

data class MovieListState (
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,

    val isPopularScreen: Boolean = true,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
)