package uz.mdev.mystore.models

class User {
    var name: String? = null
    var email: String? = null
    var number: String? = null
    var password: String? = null
    var image: String? = null
    var role: String = "Shop_Boy"
    var created_date: Long = 1
    var isActive: Boolean = false
    var plan:String="free"
    var shop:Shop?=null

    constructor()
    constructor(
        name: String?,
        email: String?,
        number: String?,
        password: String?,
        image: String?,
        role: String,
        created_date: Long
    ) {
        this.name = name
        this.email = email
        this.number = number
        this.password = password
        this.image = image
        this.role = role
        this.created_date = created_date
    }


}