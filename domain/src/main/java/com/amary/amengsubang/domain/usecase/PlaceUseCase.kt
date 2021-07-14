package com.amary.amengsubang.domain.usecase

import com.amary.amengsubang.domain.model.FavoriteDomain
import com.amary.amengsubang.domain.model.PlaceDetailDomain
import com.amary.amengsubang.domain.model.PlaceDomain
import com.amary.amengsubang.domain.model.PlaceFavoriteDomain
import com.amary.amengsubang.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface PlaceUseCase {
    fun getRemoteAllPlace(): Flow<Resource<List<PlaceDomain>>>

    fun getPlaceSearch(search: String): Flow<Resource<List<PlaceDomain>>>

    fun getFavoriteAllPlace(): Flow<Resource<List<PlaceFavoriteDomain>>>

    fun isFavorite(placeId: String): Flow<Int>

    fun getDetailPlace(placeId: String): Flow<Resource<PlaceDetailDomain>>

    fun sendMessage(name: String, message: String): Flow<Resource<Unit>>

    suspend fun insertFavoritePlace(favoriteDomain: FavoriteDomain)

    suspend fun deleteFavorite(placeId: String)
}