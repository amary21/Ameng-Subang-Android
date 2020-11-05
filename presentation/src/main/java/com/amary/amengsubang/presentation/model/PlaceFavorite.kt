package com.amary.amengsubang.presentation.model

import com.amary.amengsubang.domain.model.PlaceFavoriteDomain
import java.io.Serializable

data class PlaceFavorite (
        val favorite: Favorite,
        val place: Place
) : Serializable

fun PlaceFavoriteDomain.mapToPresentation() = PlaceFavorite(favoriteDomain.mapToPresentation(), placeDomain.mapToPresentation())

fun List<PlaceFavoriteDomain>.mapToPresentation() = map { it.mapToPresentation() }