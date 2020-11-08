package com.amary.amengsubang.core.data.datasource.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amary.amengsubang.core.data.datasource.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
    @Query("SELECT * FROM place")
    fun getCacheAllPlace(): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM place WHERE name LIKE'%' || :search || '%'")
    fun getPlaceSearch(search: String): Flow<List<PlaceEntity>>

    @Query("SELECT * FROM place, favorite WHERE place.placeId = favorite.placeId")
    fun getFavoriteAllPlace(): Flow<List<PlaceWithFavoriteEntity>>

    @Query("SELECT * FROM place, detail WHERE place.placeId = :id")
    fun getDetailPlace(id: String): Flow<PlaceWithDetailEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE placeId=:placeId)")
    fun isFavorite(placeId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlace(tourism: List<PlaceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritePlace(favoriteEntity: FavoriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailPlace(detailPlaceEntity: DetailPlaceEntity)

    @Query("DELETE FROM favorite WHERE placeId=:placeId")
    suspend fun deleteFavorite(placeId: String)
}
