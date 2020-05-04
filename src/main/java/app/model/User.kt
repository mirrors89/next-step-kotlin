package app.model

data class User(var userId: String,
                var password: String?,
                var name: String?,
                var email: String?) {

    fun update(user: User) {
        this.userId = user.userId
        this.password = user.password
        this.name = user.name
        this.email = user.email
    }

    fun isLogin(password: String?) : Boolean {
        return this.password.equals(password)
    }

    fun isSameUser(user: User): Boolean {
        return this.userId == user.userId
    }

    fun isSameUser(writer: String): Boolean {
        return this.name == writer
    }
}