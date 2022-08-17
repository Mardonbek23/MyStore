package uz.mdev.mystore.models

class Shop {
    var name: String? = null
    var number: String? = null
    var related_date: Long? = null

    constructor()
    constructor(name: String?, number: String?, related_date: Long?) {
        this.name = name
        this.number = number
        this.related_date = related_date
    }

}