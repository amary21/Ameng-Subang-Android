package com.amary.amengsubang.domain.model

data class DetailPlaceDomain(
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