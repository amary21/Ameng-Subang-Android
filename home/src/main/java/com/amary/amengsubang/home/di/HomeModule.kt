package com.amary.amengsubang.home.di

import com.amary.amengsubang.home.ui.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}