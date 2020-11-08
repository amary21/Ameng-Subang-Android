package com.amary.amengsubang.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amary.amengsubang.domain.model.DetailPlaceDomain

@Entity(tableName = "detail")
data class DetailPlaceEntity(
        @PrimaryKey
        @ColumnInfo(name = "detailId")
        val placeId: String,

        @ColumnInfo(name = "address")
        val address: String,

        @ColumnInfo(name = "description")
        val description: String,

        @ColumnInfo(name = "facility")
        val facility: List<String>,

        @ColumnInfo(name = "images")
        val images: List<String>,

        @ColumnInfo(name = "rate")
        val rate: Float,

        @ColumnInfo(name = "ticketWeekday")
        val ticketWeekday: List<String>,

        @ColumnInfo(name = "ticketWeekend")
        val ticketWeekend: List<String>,

        @ColumnInfo(name = "video")
        val video: String,

        @ColumnInfo(name = "visitingWeekday")
        val visitingWeekday: List<String>,

        @ColumnInfo(name = "visitingWeekend")
        val visitingWeekend: List<String>
)

fun DetailPlaceEntity.mapToDomain() = DetailPlaceDomain(
        placeId,
        address,
        description,
        facility,
        images,
        rate,
        ticketWeekday,
        ticketWeekend,
        video,
        visitingWeekday,
        visitingWeekend
)