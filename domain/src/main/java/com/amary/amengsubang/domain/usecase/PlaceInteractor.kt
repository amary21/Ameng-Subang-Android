package com.amary.amengsubang.domain.usecase

import com.amary.amengsubang.domain.model.FavoriteDomain
import com.amary.amengsubang.domain.repository.IPlaceRepository

class PlaceInteractor(private val placeRepository: IPlaceRepository): PlaceUseCase {
    override fun getRemoteAllPlace() = placeRepository.getRemoteAllPlace()

    override fun getPlaceSearch(search: String) = placeRepository.getPlaceSearch(search)

    override fun getFavoriteAllPlace() = placeRepository.getFavoriteAllPlace()

    override fun isFavorite(placeId: String) = placeRepository.isFavorite(placeId)

    override suspend fun insertFavoritePlace(favoriteDomain: FavoriteDomain) = placeRepository.insertFavoritePlace(favoriteDomain)

    override suspend fun deleteFavorite(placeId: String) = placeRepository.deleteFavorite(placeId)
}