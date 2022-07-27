package uz.mdev.mystore.helpers

import android.content.Context
import android.view.View
import android.widget.Toast
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

public fun View.show() {
    this.visibility = View.VISIBLE
}

public fun View.hide() {
    this.visibility = View.GONE
}

public fun View.visible() {
    this.visibility = View.VISIBLE
}

public fun setFloatToCurrencyFormat(float: Float): String {
    var format = DecimalFormat("#,###,###")
    format.minimumFractionDigits = 0
    val result = format.format(float)
    return result
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