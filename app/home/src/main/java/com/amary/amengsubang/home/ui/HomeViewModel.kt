package com.amary.amengsubang.home.ui

import androidx.lifecycle.asLiveData
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import com.amary.amengsubang.presentation.base.BaseViewModel

class HomeViewModel(private val placeUseCase: PlaceUseCase) : BaseViewModel() {

    val getRemotePlace = placeUseCase.getRemoteAllPlace().asLiveData()

    fun getPlaceSearch(search: String) = placeUseCase.getPlaceSearch(search).asLiveData()
}