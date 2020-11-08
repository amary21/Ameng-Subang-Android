package com.amary.amengsubang.presentation.model

import com.amary.amengsubang.domain.model.PlaceDomain
import java.io.Serializable

data class Place (
    val id: String,
    val name: String,
    val district: String,
    val image: String,
    val latitude: Number,
    val longitude: Number
) : Serializable

fun PlaceDomain.mapToPresentation() = Place(id, name, district, image, latitude, longitude)

fun List<PlaceDomain>.mapToPresentation(): ArrayList<Place> = map { it.mapToPresentation() } as ArrayList<Place>