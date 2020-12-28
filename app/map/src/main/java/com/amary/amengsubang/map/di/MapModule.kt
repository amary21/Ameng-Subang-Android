package com.amary.amengsubang.map.di

import com.amary.amengsubang.map.ui.MapViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapModule = module {
    viewModel { MapViewModel(get()) }
}