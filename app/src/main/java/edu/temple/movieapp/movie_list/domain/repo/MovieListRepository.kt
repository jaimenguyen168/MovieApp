package edu.temple.movieapp.movie_list.domain.repo

import edu.temple.movieapp.movie_list.domain.model.Movie
import edu.temple.movieapp.movie_list.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}