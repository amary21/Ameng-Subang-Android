package com.amary.amengsubang.detailplace.ui

import androidx.lifecycle.asLiveData
import com.amary.amengsubang.domain.usecase.PlaceUseCase
import com.amary.amengsubang.presentation.base.BaseViewModel
import com.amary.amengsubang.presentation.model.Favorite
import com.amary.amengsubang.presentation.model.mapToDomain
import kotlinx.coroutines.launch

class DetailViewModel(private val placeUseCase: PlaceUseCase): BaseViewModel() {

    fun getDetailPlace(placeId: String) = placeUseCase.getDetailPlace(placeId).asLiveData()

    fun isFavorite(placeId: String) = placeUseCase.isFavorite(placeId).asLiveData()

    fun insertFavoritePlace(favorite: Favorite){
        vmScopes.launch {
            placeUseCase.insertFavoritePlace(favorite.mapToDomain())
        }
    }

    fun deleteFavorite(placeId: String){
        vmScopes.launch {
            placeUseCase.deleteFavorite(placeId)
        }
    }
}