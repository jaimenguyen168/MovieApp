package edu.temple.movieapp.movie_list.data.remote.response

data class MovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)