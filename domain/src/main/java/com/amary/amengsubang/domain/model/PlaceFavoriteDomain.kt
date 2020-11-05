package com.amary.amengsubang.domain.model

import java.io.Serializable

data class PlaceFavoriteDomain (
        val favoriteDomain: FavoriteDomain,
        val placeDomain: PlaceDomain
) : Serializable