package uz.mdev.mystore.models

import uz.mdev.mystore.db.entities.product.Product

class Data {
    var data_list: List<Product>? = null
    var created_data: Long = 1

    constructor()
    constructor(data_list: List<Product>?, created_data: Long) {
        this.data_list = data_list
        this.created_data = created_data
    }

}