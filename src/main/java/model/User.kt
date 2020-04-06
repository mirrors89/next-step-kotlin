package model

data class User(val userId: String,
                val password: String?,
                val name: String?,
                val email: String?) {

    override fun toString(): String {
        return "User [userId=$userId," +
                " password=$password," +
                " name=$name," +
                " email=$email]"
    }


    fun isLogin(password: String?) : Boolean {
        return this.password.equals(password)
    }

    fun isSameUser(user: User): Boolean {
        return userId == user.userId
    }
}