package com.zii.movieapp.module

import com.zii.core.main.domain.repository.ILocaleRepository
import com.zii.core.main.domain.repository.IMovieRepository
import com.zii.movieapp.domain.detail.DetailUseCaseImpl
import com.zii.movieapp.domain.detail.IDetailUseCase
import com.zii.movieapp.domain.home.HomeUseCase
import com.zii.movieapp.domain.home.IHomeUseCase
import com.zii.movieapp.domain.search.ISearchUseCase
import com.zii.movieapp.domain.search.SearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideHomeUseCase(movieRepository: IMovieRepository) : IHomeUseCase {
        return HomeUseCase(movieRepository)
    }

    @Provides
    fun provideDetailUseCase(localeRepository: ILocaleRepository) : IDetailUseCase {
        return DetailUseCaseImpl(localeRepository)
    }

    @Provides
    fun provideSearchUseCase(movieRepository: IMovieRepository): ISearchUseCase {
        return SearchUseCaseImpl(movieRepository)
    }

}