package app.dao

import app.model.User
import core.jdbc.ConnectionManager

class UserDao {
    public fun insert(user: User) {
        val con = ConnectionManager.connection
        con.use {
            val sql = "INSERT INTO USER VALUES (?, ?, ?, ?)"
            val prepareStatement = con.prepareStatement(sql)
            prepareStatement.use {
                it.setString(1, user.userId)
                it.setString(2, user.password)
                it.setString(3, user.name)
                it.setString(4, user.email)
                it.executeUpdate()
            }
        }
    }

    public fun findByUserId(userId: String): User? {
        val con = ConnectionManager.connection
        return con.use {
            val sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?"
            val prepareStatement = con.prepareStatement(sql)
            val user = prepareStatement.use {
                it.setString(1, userId)
                val result = it.executeQuery()
                var user: User? = null
                if (result.next()) {
                    user = User(
                            result.getString("userId"),
                            result.getString("password"),
                            result.getString("name"),
                            result.getString("email"))
                }

                user
            }
            user
        }
    }
}