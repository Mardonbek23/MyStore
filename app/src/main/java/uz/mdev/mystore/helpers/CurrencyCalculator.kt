package uz.mdev.mystore.helpers

fun getCurrencyRoundFormat(price: Float): Float {
    val result: Float = 0f

    return result
}

fun getTotalPrice(bought_price: Float, tax_price: Float, percent: Int): Float {
    var result = 0f
    result=bought_price+tax_price+bought_price*percent/100
    return result
}