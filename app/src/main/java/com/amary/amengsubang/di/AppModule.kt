package com.amary.amengsubang.di

import com.amary.amengsubang.domain.usecase.PlaceInteractor
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<PlaceUseCase> {
        PlaceInteractor(
            get()
        )
    }
}