package com.amary.amengsubang.favorite.ui

import androidx.lifecycle.asLiveData
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import com.amary.amengsubang.presentation.base.BaseViewModel

class FavoriteViewModel(placeUseCase: PlaceUseCase) : BaseViewModel() {

    val getFavoriteAllPlace = placeUseCase.getFavoriteAllPlace().asLiveData()
}