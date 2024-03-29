package com.amary.amengsubang.domain.model

import java.io.Serializable

data class PlaceDomain(
        val id: String,
        val name: String,
        val district: String,
        val image: String,
        val latitude: Double,
        val longitude: Double
) : Serializable