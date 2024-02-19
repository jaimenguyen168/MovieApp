package edu.temple.movieapp.movie_list.data.repo

import edu.temple.movieapp.movie_list.data.local.movie.MovieDatabase
import edu.temple.movieapp.movie_list.data.mapping.toMovie
import edu.temple.movieapp.movie_list.data.mapping.toMovieEntity
import edu.temple.movieapp.movie_list.data.remote.MovieApi
import edu.temple.movieapp.movie_list.domain.model.Movie
import edu.temple.movieapp.movie_list.domain.repo.MovieListRepository
import edu.temple.movieapp.movie_list.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepoImplementation @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
): MovieListRepository {
    override suspend fun getMovieList(
        fetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)
            val shouldLoadLocally = localMovieList.isNotEmpty() && !fetchFromRemote

            if (shouldLoadLocally) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromRemote = try {
                movieApi.getMovieList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Loading"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error Network"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error"))
                return@flow
            }

            val movieEntities = movieListFromRemote.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(
                movieEntities.map {
                    it.toMovie(category)
                }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieEntity = movieDatabase.movieDao.getMovieById(id)

            if (localMovieEntity != null) {
                emit(
                    Resource.Success(
                        data = localMovieEntity.toMovie(localMovieEntity.category)
                    )
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error(message = "No Selected Movie"))
            emit(Resource.Loading(false))
        }
    }
}