package com.amary.amengsubang.core.data.datasource.remote.response

import com.amary.amengsubang.core.data.datasource.local.entity.DetailPlaceEntity

data class DetailPlaceResponse(
    var address: String? = null,
    var description: String? = null,
    var facility: List<String> = listOf(),
    var id: String? = null,
    var images: List<String> = listOf(),
    var rate: Float? = null,
    var ticketWeekday: List<String> = listOf(),
    var ticketWeekend: List<String> = listOf(),
    var video: String? = null,
    var visitingWeekday: List<String> = listOf(),
    var visitingWeekend: List<String> = listOf(),
)

fun DetailPlaceResponse.mapToEntity(): DetailPlaceEntity {
    val addressEntity = address ?: ""
    val descriptionEntity = description ?: ""
    val idEntity = id ?: ""
    val rateEntity = rate ?: 0f
    val videoEntity = video ?: ""

    return DetailPlaceEntity(
        idEntity,
        addressEntity,
        descriptionEntity,
        facility,
        images,
        rateEntity,
        ticketWeekday,
        ticketWeekend,
        videoEntity,
        visitingWeekday,
        visitingWeekend
    )
}