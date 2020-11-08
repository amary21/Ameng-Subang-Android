package com.amary.amengsubang.core.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.amary.amengsubang.domain.model.PlaceDetailDomain

data class PlaceWithDetailEntity (
        @Embedded
        val placeEntity: PlaceEntity,

        @Relation(parentColumn = "placeId", entityColumn = "detailId")
        val detailPlaceEntity: DetailPlaceEntity
)

fun PlaceWithDetailEntity.mapToDomain() = PlaceDetailDomain(placeEntity.mapToDomain(), detailPlaceEntity.mapToDomain())