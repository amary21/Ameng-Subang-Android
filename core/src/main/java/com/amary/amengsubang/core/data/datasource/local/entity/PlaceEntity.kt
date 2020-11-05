package com.amary.amengsubang.core.data.datasource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amary.amengsubang.domain.model.PlaceDomain

@Entity(tableName = "place")
data class PlaceEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "placeId")
        val id: String,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "district")
        val district: String,

        @ColumnInfo(name = "image")
        val image: String
)

fun PlaceEntity.mapToDomain(): PlaceDomain = PlaceDomain(id, name, district, image)

fun List<PlaceEntity>.mapToDomain(): List<PlaceDomain> = map { it.mapToDomain() }

