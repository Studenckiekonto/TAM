package com.kielczykowski.tam.module

import com.kielczykowski.tam.MainViewModel
import com.kielczykowski.tam.data.citiesRepository
import com.kielczykowski.tam.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cityModule = module {
    single { citiesRepository() }

    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}