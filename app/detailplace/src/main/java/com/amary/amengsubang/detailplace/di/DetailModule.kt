package com.amary.amengsubang.detailplace.di

import com.amary.amengsubang.detailplace.ui.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel { DetailViewModel(get()) }
}