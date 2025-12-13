package uz.codingtech.messengerdashboard.domain.models

class User {

    var userId: String? = null
    var userName: String? = null
    var password: String? = null

    constructor(
        userId: String,
        userName: String,
        password: String
    ) {
        this.userId = userId
        this.userName = userName
        this.password = password
    }

    constructor(
        userName: String,
        password: String
    ) {
        this.userName = userName
        this.password = password
    }

    constructor()

}