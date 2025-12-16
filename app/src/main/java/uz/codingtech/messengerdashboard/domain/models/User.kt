package uz.codingtech.messengerdashboard.domain.models

class User {

    var username: String? = null
    var password: String? = null

    constructor(
        userName: String,
        password: String
    ) {
        this.username = userName
        this.password = password
    }

    constructor()

}