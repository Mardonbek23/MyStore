package uz.mdev.mystore.helpers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

open class ObjectConverter<T> {
    @TypeConverter
     fun storedStringToMyObjects(data: String): T {
        val gson = Gson()
        val objectType: Type = object : TypeToken<T?>() {}.type
        val fromJson = gson.fromJson<T>(data, objectType)
        return fromJson
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: T?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}