package com.amary.amengsubang.presentation.model

import com.amary.amengsubang.domain.model.FavoriteDomain
import java.io.Serializable

data class Favorite (
        val placeId: String
) : Serializable

fun Favorite.mapToDomain() = FavoriteDomain(placeId)

fun FavoriteDomain.mapToPresentation() = Favorite(placeId)