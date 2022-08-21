package uz.mdev.mystore.models

class SharingData {
    var id: Long? = null
    var name: String? = null
    var quantity: Int = 1
    var price: Float? = null
    var isSelected:Boolean=false

    constructor()
    constructor(id: Long?, name: String?, quantity: Int, price: Float?) {
        this.id = id
        this.name = name
        this.quantity = quantity
        this.price = price
    }

}