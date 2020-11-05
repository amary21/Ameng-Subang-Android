package com.amary.amengsubang.domain.repository

import com.amary.amengsubang.domain.model.FavoriteDomain
import com.amary.amengsubang.domain.model.PlaceDomain
import com.amary.amengsubang.domain.model.PlaceFavoriteDomain
import com.amary.amengsubang.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IPlaceRepository {
    fun getRemoteAllPlace(): Flow<Resource<List<PlaceDomain>>>

    fun getPlaceSearch(search: String): Flow<Resource<List<PlaceDomain>>>

    fun getFavoriteAllPlace(): Flow<Resource<List<PlaceFavoriteDomain>>>

    fun isFavorite(placeId: String): Flow<Int>

    suspend fun insertFavoritePlace(favoriteDomain: FavoriteDomain)

    suspend fun deleteFavorite(placeId: String)
}