package com.amary.amengsubang.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amary.amengsubang.domain.model.FavoriteDomain

@Entity(tableName = "favorite")
class FavoriteEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "favoriteId")
        var id: Int,

        @ColumnInfo(name = "placeId")
        var placeId: String
) {
    constructor(inputId: String) : this(0, inputId)
}

fun FavoriteEntity.mapToDomain() = FavoriteDomain(placeId)

fun FavoriteDomain.mapToEntity() = FavoriteEntity(placeId)