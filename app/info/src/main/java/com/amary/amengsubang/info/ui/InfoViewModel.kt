package com.amary.amengsubang.info.ui

import androidx.lifecycle.asLiveData
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import com.amary.amengsubang.presentation.base.BaseViewModel

class InfoViewModel(private val placeUseCase: PlaceUseCase) : BaseViewModel() {

    fun sendMessage(name: String, message: String) = placeUseCase.sendMessage(name, message).asLiveData()

}