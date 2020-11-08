package com.amary.amengsubang.core.data.datasource.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PlaceConverters {

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun fromStringMap(value: String?): Map<String?, String?>? {
        val mapType = object : TypeToken<Map<String?, String?>?>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMapString(map: Map<String?, String?>?): String? {
        val gSon = Gson()
        return gSon.toJson(map)
    }
}