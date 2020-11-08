package com.amary.amengsubang.core.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amary.amengsubang.core.data.datasource.local.entity.DetailPlaceEntity
import com.amary.amengsubang.core.data.datasource.local.entity.FavoriteEntity
import com.amary.amengsubang.core.data.datasource.local.entity.PlaceEntity

@Database(
        entities = [
            PlaceEntity::class,
            FavoriteEntity::class,
            DetailPlaceEntity::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(PlaceConverters::class)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}