package uz.mdev.mystore.helpers

import android.R.attr
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.jetbrains.annotations.NotNull
import uz.mdev.mystore.db.entities.product.ProductPriceDetails
import java.lang.reflect.Type


open class ListConverter<T> {
    @TypeConverter
    fun storedStringToMyObjects(data: String): List<T> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType: Type = object : TypeToken<List<T?>?>() {}.type
        val fromJson = gson.fromJson<List<T>>(data, listType)
        if (fromJson == null) {
            return ArrayList()
        }
        return fromJson
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<T?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}