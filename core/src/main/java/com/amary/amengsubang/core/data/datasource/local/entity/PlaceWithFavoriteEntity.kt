package com.amary.amengsubang.core.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.amary.amengsubang.domain.model.PlaceFavoriteDomain


data class PlaceWithFavoriteEntity(
        @Embedded
        val placeEntity: PlaceEntity,

        @Relation(parentColumn = "placeId", entityColumn = "placeId")
        val favoriteEntity: FavoriteEntity
)

fun PlaceWithFavoriteEntity.mapToDomain() = PlaceFavoriteDomain(favoriteEntity.mapToDomain(), placeEntity.mapToDomain())

fun List<PlaceWithFavoriteEntity>.mapToDomain() = map { it.mapToDomain() }