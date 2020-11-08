package com.amary.amengsubang.presentation.model

import com.amary.amengsubang.domain.model.PlaceDetailDomain
import java.io.Serializable

data class PlaceDetail (
        val place: Place,
        val detail: Detail
) : Serializable

fun PlaceDetailDomain.mapToPresentation() = PlaceDetail(placeDomain.mapToPresentation(), detailPlaceDomain.mapToPresentation())