package com.amary.amengsubang.core.di

import androidx.room.Room
import com.amary.amengsubang.core.data.PlaceRepository
import com.amary.amengsubang.core.data.datasource.local.LocalDataSource
import com.amary.amengsubang.core.data.datasource.local.room.PlaceDatabase
import com.amary.amengsubang.core.data.datasource.remote.RemoteDataSource
import com.amary.amengsubang.domain.repository.IPlaceRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    factory { get<PlaceDatabase>().placeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            PlaceDatabase::class.java, "place.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val app = Firebase.app("fireStoreApp")
        Firebase.firestore(app)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource() }
    single<IPlaceRepository> { PlaceRepository(get(), get()) }
}