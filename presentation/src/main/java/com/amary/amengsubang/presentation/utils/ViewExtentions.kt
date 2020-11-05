package com.amary.amengsubang.presentation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.amary.amengsubang.presentation.BuildConfig.API_KEY_YOUTUBE
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX

inline fun <reified T : ViewDataBinding> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, false)
}

fun YouTubePlayerSupportFragmentX.loadVideo(videoUrl: String) {
    this.initialize(API_KEY_YOUTUBE, object : YouTubePlayer.OnInitializedListener{
        override fun onInitializationSuccess(
            p0: YouTubePlayer.Provider?,
            p1: YouTubePlayer?,
            p2: Boolean
        ) {
            p1?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
            p1?.cueVideo(videoUrl)
        }

        override fun onInitializationFailure(
            p0: YouTubePlayer.Provider?,
            p1: YouTubeInitializationResult?
        ) {}

    })
}
