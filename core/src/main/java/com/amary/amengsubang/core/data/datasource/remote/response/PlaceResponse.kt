package com.amary.amengsubang.core.data.datasource.remote.response

import com.amary.amengsubang.core.data.datasource.local.entity.PlaceEntity
import java.io.Serializable

data class PlaceResponse (
        var id: String? = null,
        var name: String? = null,
        var districts: String? = null,
        var image: String? = null
) : Serializable

fun PlaceResponse.mapToEntity(): PlaceEntity{
        val idEntity = id ?: ""
        val nameEntity = name ?: ""
        val districtEntity = districts ?: ""
        val imageEntity = image ?: ""

        return PlaceEntity(idEntity, nameEntity, districtEntity, imageEntity)
}

fun List<PlaceResponse>.mapToEntity(): List<PlaceEntity> = map { it.mapToEntity() }