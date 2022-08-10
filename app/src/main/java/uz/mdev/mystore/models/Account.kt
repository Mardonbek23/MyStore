package uz.mdev.mystore.models

class Account {
    var name: String? = null
    var email: String? = null
    var number: String? = null
    var password: String? = null
    var image: String? = null
    var role: String = "Manager"
    var created_date: Long = 1

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