package uz.mdev.mystore.models

class Message {
    var from: String? = null
    var to: String? = null
    var date: Long? = null
    var isRead: Boolean = false
    var key: String? = null
    var status: Int? = 0

    var text: String? = ""
    var image: String? = null
    var file: String? = null
    var money: Int? = null
    var list: List<SharingData>? = null
    var products_price: Float? = null

    constructor()
    constructor(
        from: String?,
        to: String?,
        date: Long?,
        isRead: Boolean,
        key: String?,
        status: Int?,
        text: String?,
        image: String?,
        file: String?,
        money: Int?,
        list: List<SharingData>?,
        products_price: Float?
    ) {
        this.from = from
        this.to = to
        this.date = date
        this.isRead = isRead
        this.key = key
        this.status = status
        this.text = text
        this.image = image
        this.file = file
        this.money = money
        this.list = list
        this.products_price = products_price
    }


}