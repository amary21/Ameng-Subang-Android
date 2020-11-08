package com.amary.amengsubang.domain.model

import java.io.Serializable

data class PlaceDetailDomain (
        val placeDomain: PlaceDomain,
        val detailPlaceDomain: DetailPlaceDomain
) : Serializable