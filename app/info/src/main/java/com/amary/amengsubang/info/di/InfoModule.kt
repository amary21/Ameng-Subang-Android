package com.amary.amengsubang.info.di

import com.amary.amengsubang.info.ui.InfoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val infoModule = module {
    viewModel { InfoViewModel(get()) }
}