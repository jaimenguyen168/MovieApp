package edu.temple.movieapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.temple.movieapp.movie_list.data.repo.MovieListRepoImplementation
import edu.temple.movieapp.movie_list.domain.repo.MovieListRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieListRepoImplementation: MovieListRepoImplementation
    ): MovieListRepository

}