package com.nextgen.beritaku.di

import com.nextgen.beritaku.core.domain.usecase.NewsInteractor
import com.nextgen.beritaku.core.domain.usecase.NewsUseCase
import com.nextgen.beritaku.detail.DetailViewModel
import com.nextgen.beritaku.explore.ExploreViewModel
import com.nextgen.beritaku.explore.search.SearchViewModel
import com.nextgen.beritaku.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<NewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel{HomeViewModel(get())}
    viewModel { ExploreViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}
