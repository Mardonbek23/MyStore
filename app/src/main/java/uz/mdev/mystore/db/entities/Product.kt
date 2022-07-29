package uz.mdev.mystore.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var category: Int = 0
    var images: String? = null
    var description: String? = null
    var total_price: Float = 0f
    var min_price: Float = 0f
    var quantity: Int = 0
    var interest_percent: Int = 0
    var min_percent: Int = 5
    var manufacturer: String? = null
    var gift_quantity: Int = 0
    var gift_percent: Int = 0
    var gift_price: Float = 0f
    var price_bought: Float = 0f
    var tax_price: Float = 0f
    var size: String? = null
    var status: Int = 0
    var old_bought_price: Float = 0f
    var old_tax_price: Float = 0f
    var old_min_price: Float = 0f
    var old_interest_percent: Int = 0
    var old_total_price: Float = 0f
    var old_quantity: Int = 0
    var old_gift_quantity: Int = 0
    var old_gift_price: Float = 0f
    var old_manufacturer: String? = null

    constructor()

    constructor(
        id: Int,
        name: String?,
        category: Int,
        price_bought: Float,
        total_price: Float,
        tax_price: Float,
        min_price: Float,
        interest_percent: Int,
        size: String?,
        quantity: Int,
        status: Int,
        images: String?,
        gift_quantity: Int,
        gift_percent: Int,
        gift_price: Float,
        manufacturer: String?,
        description: String?,
        old_bought_price: Float,
        old_tax_price: Float,
        old_min_price: Float,
        old_interest_percent: Int,
        old_total_price: Float,
        old_quantity: Int,
        old_gift_quantity: Int,
        old_gift_price: Float,
        old_manufacturer: String?,
    ) {
        this.id = id
        this.name = name
        this.category = category
        this.price_bought = price_bought
        this.total_price = total_price
        this.tax_price = tax_price
        this.min_price = min_price
        this.interest_percent = interest_percent
        this.size = size
        this.quantity = quantity
        this.status = status
        this.images = images
        this.gift_quantity = gift_quantity
        this.gift_percent = gift_percent
        this.gift_price = gift_price
        this.manufacturer = manufacturer
        this.description = description
        this.old_bought_price = old_bought_price
        this.old_tax_price = old_tax_price
        this.old_min_price = old_min_price
        this.old_interest_percent = old_interest_percent
        this.old_total_price = old_total_price
        this.old_quantity = old_quantity
        this.old_gift_quantity = old_gift_quantity
        this.old_gift_price = old_gift_price
        this.old_manufacturer = old_manufacturer
    }

    constructor(id: Int, name: String?, category: Int, images: String?, description: String?) {
        this.id = id
        this.name = name
        this.category = category
        this.images = images
        this.description = description
    }


}