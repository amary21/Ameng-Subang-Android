package com.amary.amengsubang.presentation.utils

import android.view.View
import com.amary.amengsubang.presentation.model.Place

interface ListCallback {
    fun onClick(view: View, place: Place)
}