package com.amary.amengsubang.map.ui

import androidx.lifecycle.asLiveData
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import com.amary.amengsubang.presentation.base.BaseViewModel

class MapViewModel(private val placeUseCase: PlaceUseCase) : BaseViewModel() {

    val getRemotePlace = placeUseCase.getRemoteAllPlace().asLiveData()

    fun getPlaceSearch(search: String) = placeUseCase.getPlaceSearch(search).asLiveData()
}