package app.dao

import app.model.User
import core.jdbc.ConnectionManager

class UserDao {
    fun insert(user: User) {
        val con = ConnectionManager.getConnection()
        con.use {
            val sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use { ps ->
                ps.setString(1, user.userId)
                ps.setString(2, user.password)
                ps.setString(3, user.name)
                ps.setString(4, user.email)
                ps.executeUpdate()
            }
        }
    }

    fun findByUserId(userId: String): User? {
        val con = ConnectionManager.getConnection()
        return con.use {
            val sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?"
            val prepareStatement = it.prepareStatement(sql)
            val user = prepareStatement.use { ps ->
                ps.setString(1, userId)
                val result = ps.executeQuery()
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

    fun update(user: User): User {
        val con = ConnectionManager.getConnection()
        con.use {
            val sql = "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use {ps ->
                ps.setString(1, user.password)
                ps.setString(2, user.name)
                ps.setString(3, user.email)
                ps.setString(4, user.userId)
                ps.executeUpdate()
            }
        }

        return user
    }

    fun findAll(): List<User> {
        val users: MutableList<User> = mutableListOf()
        val con = ConnectionManager.getConnection()
        con.use {
            val sql = "SELECT userId, password, name, email FROM USERS"
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use { ps ->
                val result = ps.executeQuery()
                while (result.next()) {
                    users.add(User(
                            result.getString("userId"),
                            result.getString("password"),
                            result.getString("name"),
                            result.getString("email")))
                }

            }
        }

        return users
    }
}