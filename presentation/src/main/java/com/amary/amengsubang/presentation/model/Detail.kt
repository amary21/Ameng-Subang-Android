package com.amary.amengsubang.presentation.model

import com.amary.amengsubang.domain.model.DetailPlaceDomain

data class Detail(
        val id: String,
        val address: String,
        val description: String,
        val facility: List<String>,
        val images: List<String>,
        val rate: Float,
        val ticketWeekday: List<String>,
        val ticketWeekend: List<String>,
        val video: String,
        val visitingWeekday: List<String>,
        val visitingWeekend: List<String>
)

fun DetailPlaceDomain.mapToPresentation() = Detail(id, address, description, facility, images, rate, ticketWeekday, ticketWeekend, video, visitingWeekday, visitingWeekend)