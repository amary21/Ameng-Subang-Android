package com.amary.amengsubang.core.data.datasource.local

import com.amary.amengsubang.core.data.datasource.local.entity.FavoriteEntity
import com.amary.amengsubang.core.data.datasource.local.entity.PlaceEntity
import com.amary.amengsubang.core.data.datasource.local.room.PlaceDao

class LocalDataSource(private val placeDao: PlaceDao) {

    fun getCacheAllPlace() = placeDao.getCacheAllPlace()

    fun getPlaceSearch(search: String) = placeDao.getPlaceSearch(search)

    fun getFavoriteAllPlace() = placeDao.getFavoriteAllPlace()

    fun isFavorite(placeId: String) = placeDao.isFavorite(placeId)

    suspend fun insertAllPlace(placeEntity: List<PlaceEntity>) = placeDao.insertAllPlace(placeEntity)

    suspend fun insertFavoritePlace(favoriteEntity: FavoriteEntity) = placeDao.insertFavoritePlace(favoriteEntity)

    suspend fun deleteFavorite(placeId: String) = placeDao.deleteFavorite(placeId)
}