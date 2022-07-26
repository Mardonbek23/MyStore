package uz.mdev.mystore.helpers

import android.view.View

public fun View.show() {
    this.visibility = View.VISIBLE
}

public fun View.hide() {
    this.visibility = View.GONE
}

public fun View.visible() {
    this.visibility = View.VISIBLE
}