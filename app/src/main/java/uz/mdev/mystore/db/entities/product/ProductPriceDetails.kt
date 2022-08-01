package uz.mdev.mystore.db.entities.product

class ProductPriceDetails {
    var isGrouped = false

    //piece details
    var quantity = 0
    var price = 0f

    //group details
    var group_count = 0
    var quantity_in_each_group = 0
    var group_price = 0f

    constructor()
    constructor(isGrouped: Boolean, quantity: Int, price: Float) {
        this.isGrouped = isGrouped
        this.quantity = quantity
        this.price = price
    }

    constructor(
        isGrouped: Boolean,
        group_count: Int,
        quantity_in_each_group: Int,
        group_price: Float
    ) {
        this.isGrouped = isGrouped
        this.group_count = group_count
        this.quantity_in_each_group = quantity_in_each_group
        this.group_price = group_price
    }


}