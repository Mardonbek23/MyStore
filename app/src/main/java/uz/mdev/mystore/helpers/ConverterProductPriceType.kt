package uz.mdev.mystore.helpers

import android.R.attr
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.mdev.mystore.db.entities.product.ProductPriceDetails
import java.lang.reflect.Type


class ConverterProductPriceType {
    @TypeConverter
    fun storedStringToMyObjects(data: String): List<ProductPriceDetails> {
        val gson = Gson()
        if (data == null) {
            return ArrayList()
        }
        val listType: Type = object : TypeToken<List<ProductPriceDetails?>?>() {}.type
        val fromJson = gson.fromJson<List<ProductPriceDetails>>(data, listType)
        if (fromJson == null) {
            return ArrayList()
        }
        return fromJson
    }

    @TypeConverter
    fun myObjectsToStoredString(myObjects: List<ProductPriceDetails?>?): String? {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}