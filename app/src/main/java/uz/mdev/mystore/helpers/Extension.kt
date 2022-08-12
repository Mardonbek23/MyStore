package uz.mdev.mystore.helpers

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.gson.reflect.TypeToken
import uz.mdev.mystore.R
import uz.mdev.mystore.databinding.DialogProgressBinding
import java.lang.reflect.Type
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

public fun View.show() {
    this.visibility = View.VISIBLE
}

public fun View.hide() {
    this.visibility = View.GONE
}

public fun View.invisible() {
    this.visibility = View.INVISIBLE
}

public fun setFloatToCurrencyFormat(float: Float): String {
    var format = DecimalFormat("#,###,###")
    format.minimumFractionDigits = 0
    val result = format.format(float)
    return result
}

fun deFormatCurrencyWithoutSymbols(string: String): Float {
    var format = DecimalFormat("#,###,###")
    format.minimumFractionDigits = 0
    val result = format.parse(string)
    return result.toFloat()
}

fun setFloatToCurrencyWithSymbols(float: Float): String {
    val format = NumberFormat.getCurrencyInstance(Locale.GERMANY)
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("uzs")
    return format.format(float)
}

public fun setIntToStr(int: Int): String {
    if (int == null) {
        return "0"
    }
    return int.toString()
}

public fun setPercentForm(int: Int): String {
    if (int == null) {
        return "0%"
    }
    return "$int%"
}

fun Context.makeMyToast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun intTo4digits(int: Int): String {
    return String.format("%04d", int)
}

fun Context.progress_dialog(string: String?): AlertDialog {
    val dialog = AlertDialog.Builder(this)
    dialog.setCancelable(false)
    val custom = DialogProgressBinding.inflate(LayoutInflater.from(this))
    dialog.setView(custom.root)
    if (string != null) {
        custom.text.text = string
    }
    return dialog.show()
    val type: Type = object : TypeToken<ArrayList<Int>>() {}.type
}

fun <T> getTypeToken(): Type {
    val type: Type = object : TypeToken<T>() {}.type
    return type
}
