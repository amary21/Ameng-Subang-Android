package com.amary.amengsubang.presentation.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import org.koin.core.KoinComponent
import org.koin.core.inject

object GlideInstance : KoinComponent {
    private val glide by inject<RequestManager>()

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.bindImage(imageUrl: String?) {
        imageUrl?.let { url ->
            val parsedImageUrl = url.toUri().buildUpon().scheme("https").build()
            glide.load(parsedImageUrl).into(this)
        }
    }
}