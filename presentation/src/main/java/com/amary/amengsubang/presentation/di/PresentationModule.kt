package com.amary.amengsubang.presentation.di

import android.app.Application
import com.amary.amengsubang.presentation.R
import com.amary.amengsubang.presentation.utils.Preference
import com.amary.amengsubang.presentation.utils.ToastMotion
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.currentCoroutineContext
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val glideModule = module {
    single { provideRequestOptions() }
    single { provideRequestManager(androidApplication(), get()) }
}

fun provideRequestManager(
    application: Application,
    requestOptions: RequestOptions
): RequestManager {
    return Glide.with(application)
        .setDefaultRequestOptions(requestOptions)
}

fun provideRequestOptions(): RequestOptions {
    return RequestOptions()
        .placeholder(R.drawable.img_place_holer)
        .error(R.drawable.img_place_holer)
}

val preferenceModule = module {
    factory {
        Preference(get())
    }
}

val motionToast = module {
    factory {
        ToastMotion(it[0])
    }
}