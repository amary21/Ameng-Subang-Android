package com.amary.amengsubang

import android.app.Application
import com.amary.amengsubang.BuildConfig.*
import com.amary.amengsubang.core.di.databaseModule
import com.amary.amengsubang.core.di.networkModule
import com.amary.amengsubang.core.di.repositoryModule
import com.amary.amengsubang.di.useCaseModule
import com.amary.amengsubang.presentation.di.glideModule
import com.amary.amengsubang.presentation.di.motionToast
import com.amary.amengsubang.presentation.di.preferenceModule
import com.facebook.stetho.Stetho
import com.google.firebase.FirebaseOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mapbox.mapboxsdk.Mapbox
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val options = FirebaseOptions.Builder()
            .setProjectId(PROJECT_ID)
            .setApplicationId(APLICATION_ID)
            .setApiKey(API_KEY)
            .build()
        Firebase.initialize(this, options, getString(R.string.firebase_app))

        Mapbox.getInstance(applicationContext, MAPBOX_ACCESS_TOKEN)

        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    glideModule,
                    preferenceModule,
                    motionToast
                )
            )
        }
    }
}