package uz.mdev.mystore.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class Product {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var category: String = "None"
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
    var isSelected: Boolean = false
    var benefit: Float = 0f

    constructor()

    constructor(id: Int, name: String?, category: String, images: String?, description: String?) {
        this.id = id
        this.name = name
        this.category = category
        this.images = images
        this.description = description
    }


}