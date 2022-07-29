package uz.mdev.mystore.local_data

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(var context: Context) {
    private val APP_SETTINGS = "APP_SETTINGS"

    // properties
    private val SOME_STRING_VALUE = "SOME_STRING_VALUE"
    // other properties...

    private fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE)
    }

    fun getCalculateItems(): String? {
        return getSharedPreferences().getString(SOME_STRING_VALUE, null)
    }

    fun setCalculateItems(newValue: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(SOME_STRING_VALUE, newValue!!)
        editor.commit()
    }
}