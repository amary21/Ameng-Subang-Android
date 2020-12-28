package com.amary.amengsubang.presentation.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.amary.amengsubang.presentation.R
import com.bumptech.glide.RequestManager
import com.synnapps.carouselview.CarouselView
import org.koin.core.KoinComponent
import org.koin.core.inject

object BindingInstance : KoinComponent {
    private val glide by inject<RequestManager>()

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.bindImage(imageUrl: String?) {
        imageUrl?.let { url ->
            val parsedImageUrl = url.toUri().buildUpon().scheme("https").build()
            glide.load(parsedImageUrl).into(this)
        }
    }

    @JvmStatic
    @BindingAdapter("carouselImage")
    fun CarouselView.bindImage(imageUrl: List<String>?){
        imageUrl?.let {
            this.setImageListener { position, imageView ->
                imageView.setBackgroundResource(R.drawable.bc_carousel)
                imageView.bindImage(it[position])
            }

            this.pageCount = it.size
        }
    }

    @JvmStatic
    @BindingAdapter("viewFacility")
    fun CardView.bindVisibility(visibility: Boolean){
        this.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("unViewFacility")
    fun CardView.bindUnVisibility(visibility: Boolean){
        this.visibility = if (visibility) View.GONE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("ticketPrice", "format")
    fun TextView.bindTicketPrice(ticket: List<String>?, format: String){
        ticket?.let {
            when {
                it.size > 1 -> {
                    this.text = String.format(format, it[0], it[1])
                }
                it.size == 1 -> {
                    this.text = it[0]
                }
                else -> {
                    this.text = ""
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visitingHours", "format")
    fun TextView.bindVisitingHours(visiting: List<String>?, format: String){
        visiting?.let {
            if (it.size > 1) {
                this.text = String.format(format, it[0], it[1])
            } else {
                this.text = it[0]
            }
        }
    }

}