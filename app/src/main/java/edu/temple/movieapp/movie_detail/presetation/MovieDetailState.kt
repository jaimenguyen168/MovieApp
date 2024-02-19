package edu.temple.movieapp.movie_detail.presetation

import edu.temple.movieapp.movie_list.domain.model.Movie

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
